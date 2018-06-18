package tis.com.epay.web;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.TisUtil;
import tis.com.common.service.TisCodeService;
import tis.com.common.service.TisCodeVO;
import tis.com.epay.service.EpayApprHistVO;
import tis.com.epay.service.EpayApprInfoSearchVO;
import tis.com.epay.service.EpayApprInfoService;
import tis.com.epay.service.EpayApprInfoVO;
import tis.com.epay.service.EpayCnsulVO;
import tis.com.epay.service.EpayDocFormVO;
import tis.com.epay.service.EpayDraftInfoService;
import tis.com.epay.service.EpayDraftInfoVO;
import tis.com.epay.service.EpayExpCorHistVO;
import tis.com.epay.service.EpayExpCorVO;
import tis.com.epay.service.EpayExpHistVO;
import tis.com.epay.service.EpayExpVO;
import tis.com.epay.service.EpayHolidayVO;
import tis.com.financial.purchase.service.PurchaseService;
import tis.com.financial.purchase.service.PurchaseVO;
import tis.com.financial.sales.service.SalesService;
import tis.com.financial.sales.service.SalesVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.vct.service.EgovVcatnManageService;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class EpayApprController {

	@Resource(name = "epayApprInfoService")
	private EpayApprInfoService epayApprInfoService;

	@Resource(name = "epayDraftInfoService")
	private EpayDraftInfoService epayDraftInfoService;

	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;

	@Resource(name = "salesService")
	private SalesService salesService;
	
	@Resource(name = "purchaseService")
	private PurchaseService purchaseService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "egovVcatnManageService")
    private EgovVcatnManageService egovVcatnManageService;
	
	@RequestMapping("/com/epay/appr/epayApprInfoList.do")
	public String epayApprInfoList(
			HttpServletRequest request
			, EpayDocFormVO epayDocFormVO
			, EpayApprInfoVO epayApprInfoVO
			, EpayApprInfoSearchVO epayApprInfoSearchVO
			, ModelMap model
			, String method) 
			throws Exception {
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String resultPage = "/tis/com/epay/appr/epayApprInfoList"; // 기본 페이지 설정
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(epayApprInfoSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(epayApprInfoSearchVO.getRecordCountPerPage());
		paginationInfo.setPageSize(epayApprInfoSearchVO.getPageSize());
		
		epayApprInfoVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		epayApprInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
		epayApprInfoVO.setPageSize(paginationInfo.getPageSize());
		epayApprInfoVO.setSortColumn(epayApprInfoSearchVO.getSortColumn());
		epayApprInfoVO.setSortOrderBy(epayApprInfoSearchVO.getSortOrderBy());
		// 결재상태 조건 검색을 위한 VO 세팅
		epayApprInfoVO.setSearchApprSttusCnd(epayApprInfoSearchVO.getSearchApprSttusCnd());
		// 문서양식 조건 검색을 위한 VO 세팅
		epayApprInfoVO.setSearchDocFormCnd(epayApprInfoSearchVO.getSearchDocFormCnd());
		epayApprInfoVO.setSearchSdate(epayApprInfoSearchVO.getSearchSdate());
		epayApprInfoVO.setSearchEdate(epayApprInfoSearchVO.getSearchEdate());
		
		String[] arrDocForm, arrApprSttus, docFormTot, apprSttusTot = null;
		String searchDocFormCnd = "";
		String searchApprSttusCnd = "";
		
		//docFormChkTot
		docFormTot = request.getParameterValues("docFormChkTot");
		apprSttusTot = request.getParameterValues("apprSttusChkTot");
		arrDocForm = request.getParameterValues("docFormChk");
		arrApprSttus = request.getParameterValues("apprSttusChk");
		
		// 문서양식 검색 조건  쿼리 문자열 생성
		if(arrDocForm != null && arrDocForm.length > 0){
			int i = 0;
			for (String val : arrDocForm) {
				if(i > 0)searchDocFormCnd += " OR";
				searchDocFormCnd += " B.doc_form_id = \'" + val + "\'";
				i++;
			}
		}
		// 결재상태 검색 조건 쿼리 문자열 생성
		if(arrApprSttus != null && arrApprSttus.length > 0){
			int i = 0;
			for (String val : arrApprSttus) {
				if(i > 0)searchApprSttusCnd += " OR";
				//searchApprSttusCnd += " B.APPR_STTUS = \'" + val + "\'";//searchApprSttusCnd += " A.appr_sttus = \'" + val + "\'";
				
				searchApprSttusCnd += " A.APPR_STATE = \'" + val + "\'";
				
				i++;
			}
		}
		
		// 문서양식 체크박스 체크 상태 유지를 위한 문서양식 ID 배열 정보 전달
		model.addAttribute("docFormTot", docFormTot);
		model.addAttribute("arrDocForm", arrDocForm);
		// 결재상태 체크박스 체크 상태 유지를 위한 결재상태 ID 배열 정보 전달
		model.addAttribute("apprSttusTot", apprSttusTot);
		model.addAttribute("arrApprSttus", arrApprSttus);
		
		epayApprInfoVO.setSearchDocFormCnd(searchDocFormCnd);
		epayApprInfoVO.setSearchApprSttusCnd(searchApprSttusCnd);
		
		
		// 문서양식 목록 조회
		List<EpayDocFormVO> docFormList = this.epayDraftInfoService.selectEpayDocFormList(epayDocFormVO);
		
		// subpage 구분 파라미터
		model.addAttribute("method", method);
		model.addAttribute("docFormList", docFormList);
		model.addAttribute("deptList", tisCodeService.selectCodeDetailList("COM101"));
		
		TisCodeVO apprSttusVO = new TisCodeVO();
		apprSttusVO.setCodeId("COM202");
		apprSttusVO.setCode("'0','1','2','3','4'"); // 결재진행중(결재대기), 승인, 반려
		model.addAttribute("apprSttusList", tisCodeService.selectCodeDetailByVOList(apprSttusVO));
		
		//epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 테스트 사용자
		epayApprInfoVO.setEmplNo(user.getEmplyrNo());//"I-1802"); // 결재자 사원번호
		
		// 기안정보 조회
		//List<EpayDraftInfoVO> draftInfoList = epayApprInfoService.selectEpayApprInfoList(epayDraftInfoVO);
		List<EgovMap> draftInfoList = epayApprInfoService.selectEpayApprInfoList(epayApprInfoVO);
		
		int recordCount = this.epayApprInfoService.epayApprInfoListCount(epayApprInfoVO);
		
		paginationInfo.setTotalRecordCount(recordCount);
		
		String cnt = this.epayApprInfoService.epayApprWaitingCount(epayApprInfoVO).toString();
				
		model.addAttribute("epayApprWaitingCount", cnt);
		model.addAttribute("resultList", draftInfoList);
		model.addAttribute("epayApprInfoSearchVO", epayApprInfoSearchVO);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return resultPage;
	}
	
	@RequestMapping(value="/com/epay/appr/apprBoxCnt.do")
	public String apprBoxCnt(@RequestParam("leftmenu") String leftmenu, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		EpayApprInfoVO epayApprInfoVO = new EpayApprInfoVO();
		epayApprInfoVO.setEmplNo(user.getEmplyrNo());
		
		String cnt = this.epayApprInfoService.epayApprWaitingCount(epayApprInfoVO).toString();
		
		model.addAttribute("newCnt", cnt);
		model.addAttribute("leftmenu", leftmenu);		
    	return "tis/com/inc/newEpay";
	}
	
	// [품의서] 결재 화면
	@RequestMapping("/com/epay/appr/epayCnsulAppr.do")
	public String epayCnsulAppr(
			String draftInfoId
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayCnsulVO epayCnsulVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			, String method
			, String apprSttus
			, String apprState) 
			throws Exception{
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		epayApprHistVO.setDraftInfoId(draftInfoId);                  
		
		EgovMap cnsul = this.epayDraftInfoService.selectEpayCnsulWithDInfo(epayCnsulVO);
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO);
		
		model.addAttribute("cnsul", cnsul);
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("apprHistList", epayApprHistList);
		model.addAttribute("apprSttus", apprSttus);  // 기안정보 결재 상태
		model.addAttribute("apprState", apprState);// apprState 결재이력 결재자 결재상태
		//model.addAttribute("loginEmplNo", "I-1802"); // 로그인한 사용자가 기안이력의 결재할 순서의 결재자와 동일한지 비교
		model.addAttribute("loginEmplNo", user.getEmplyrNo());
		
		// 매출내역 리스트
		List<SalesVO> draftSalesList = (List<SalesVO>)salesService.selectDraftSalesList(epayDraftInfoVO.getDraftInfoId());
		model.put("draftSalesList", draftSalesList);
		
		// 매입내역 리스트
		List<PurchaseVO> draftPurchaseList = (List<PurchaseVO>)purchaseService.selectDraftPurchaseList(epayDraftInfoVO.getDraftInfoId());
		model.put("draftPurchaseList", draftPurchaseList);
		
		return "/tis/com/epay/appr/epayCnsulAppr";// 품의서 상세보기
	}
	
	@RequestMapping("/com/epay/appr/epayCnsulApprSalesView.do")
	public String epayCnsulApprSalesView(ModelMap model,
			EpayDraftInfoVO epayDraftInfoVO,
			String salesId,
			String apprSttus,
			String apprState) throws Exception {
		
		EgovMap salesMap = salesService.selectSales(salesId);
		model.addAttribute("salesVO", salesMap);
		
		List<EgovMap> salesDetailMap = (List<EgovMap>)salesService.selectSalesDetailList(salesId);
		model.addAttribute("salesDetailVO", salesDetailMap);
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		model.addAttribute("apprSttus", apprSttus);
		model.addAttribute("apprState", apprState);
		
		return "/tis/com/epay/appr/epayCnsulApprSalesView";
	}
	
	@RequestMapping("/com/epay/appr/epayCnsulApprPurchaseView.do")
	public String epayCnsulApprPurchaseView(ModelMap model,
			EpayDraftInfoVO epayDraftInfoVO,
			String purchaseId,
			String apprSttus,
			String apprState) throws Exception {
		
		EgovMap purchaseMap = purchaseService.selectPurchase(purchaseId);
		model.addAttribute("purchaseVO", purchaseMap);
		
		List<EgovMap> purchaseDetailMap = (List<EgovMap>)purchaseService.selectPurchaseDetailList(purchaseId);
		model.addAttribute("purchaseDetailVO", purchaseDetailMap);
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		model.addAttribute("apprSttus", apprSttus);
		model.addAttribute("apprState", apprState);
		
		return "/tis/com/epay/appr/epayCnsulApprPurchaseView";
	}
	
	// [지출명세서(개인)] 결재 화면
	@RequestMapping("/com/epay/appr/epayExpAppr.do")
	public String epayExpAppr(String draftInfoId
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayExpVO epayExpVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			, String method
			, String apprSttus
			, String apprState) 
			throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		epayApprHistVO.setDraftInfoId(draftInfoId);
		
 		EgovMap exp = this.epayDraftInfoService.selectEpayExpWithDInfo(epayExpVO); // 지출명세서(개인) 정보 조회
 		
 		EpayExpHistVO epayExpHistVO = new EpayExpHistVO();
 		epayExpHistVO.setExpStatId(exp.get("expStatId").toString());
		List<?> epayExpHistList = this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO);
		
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO); // 결재이력라인 정보 조회
		
		model.addAttribute("exp", exp);
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("apprHistList", epayApprHistList);
		model.addAttribute("expHistList", epayExpHistList);
		model.addAttribute("apprSttus", apprSttus);
		model.addAttribute("apprState", apprState);
		//model.addAttribute("apprEmplNo", user.getEmplyrNo()); // 로그인한 사용자가 기안이력의 결재할 순서의 결재자와 동일한지 비교
		model.addAttribute("loginEmplNo", user.getEmplyrNo());//"I-1802"); // 로그인한 사용자가 기안이력의 결재할 순서의 결재자와 동일한지 비교
		//model.addAttribute("apprEmplNo", epayDraftInfoVO.getApprEmplNo());
		
		return "/tis/com/epay/appr/epayExpAppr";// 품의서 상세보기
	}
	
	// [지출명세서(법인)] 결재 화면
	@RequestMapping("/com/epay/appr/epayExpCorAppr.do")
	public String epayExpAppr(String draftInfoId
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayExpCorVO epayExpCorVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			, String method
			, String apprSttus
			, String apprState) 
			throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		epayApprHistVO.setDraftInfoId(draftInfoId);
		
 		EgovMap expcor = this.epayDraftInfoService.selectEpayExpCorWithDInfo(epayExpCorVO); // 지출명세서(법인) 정보 조회
 		
 		EpayExpCorHistVO epayExpCorHistVO = new EpayExpCorHistVO();
 		epayExpCorHistVO.setExpcorStatId(expcor.get("expcorStatId").toString());
		List<?> epayExpCorHistList = this.epayDraftInfoService.selectEpayExpCorHistList(epayExpCorHistVO);
		
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO); // 결재이력라인 정보 조회
		
		model.addAttribute("expcor", expcor);
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("apprHistList", epayApprHistList);
		model.addAttribute("expCorHistList", epayExpCorHistList);
		model.addAttribute("apprSttus", apprSttus);
		model.addAttribute("apprState", apprState);
		//model.addAttribute("apprEmplNo", user.getEmplyrNo()); // 로그인한 사용자가 기안이력의 결재할 순서의 결재자와 동일한지 비교
		model.addAttribute("loginEmplNo", user.getEmplyrNo());//"I-1804"); // 로그인한 사용자가 기안이력의 결재할 순서의 결재자와 동일한지 비교
		//model.addAttribute("apprEmplNo", epayDraftInfoVO.getApprEmplNo());
		
		return "/tis/com/epay/appr/epayExpCorAppr";// 품의서 상세보기
	}
	
	// [휴가원] 결재 화면
	@RequestMapping("/com/epay/appr/epayHolidayAppr.do")
	public String epayHolidayAppr(String draftInfoId
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayHolidayVO epayHolidayVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			, String apprSttus
			, String apprState) 
			throws Exception{
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
				
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
			
		epayApprHistVO.setDraftInfoId(draftInfoId);
			
	 	EgovMap holiday = this.epayDraftInfoService.selectEpayHolidayWithDInfo(epayHolidayVO); // 휴가원 정보 조회
			
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO); // 결재이력라인 정보 조회
			
		/*VcatnManageVO vcatnManageVO1 = egovVcatnManageService.selectIndvdlYrycManage(user.getUniqId());
		double remndrYrycCo = vcatnManageVO1.getRemndrYrycCo();
		double useYrycCo = vcatnManageVO1.getUseYrycCo();
		double possibleYryc =  remndrYrycCo + useYrycCo; 
		model.addAttribute("possibleYryc", possibleYryc);*/
		
		model.addAttribute("holiday", holiday);
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("apprHistList", epayApprHistList);
		model.addAttribute("apprSttus", apprSttus);
		model.addAttribute("apprState", apprState);
		//model.addAttribute("apprEmplNo", user.getEmplyrNo()); // 로그인한 사용자가 기안이력의 결재할 순서의 결재자와 동일한지 비교
		model.addAttribute("loginEmplNo", user.getEmplyrNo());//"I-1804"); // 로그인한 사용자가 기안이력의 결재할 순서의 결재자와 동일한지 비교
		//model.addAttribute("apprEmplNo", epayDraftInfoVO.getApprEmplNo());
			
		return "/tis/com/epay/appr/epayHolidayAppr";// 휴가원 상세보기
	}	
	
	// 결재 문서 승인
	@RequestMapping("/com/epay/appr/epayApprAccept.do")
	public ModelAndView epayApprAccept(ModelMap model, String draftInfoId, String apprCm) 
			throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		EpayDraftInfoVO epayDraftInfoVO = new EpayDraftInfoVO();
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		
		EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(epayDraftInfoVO);
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime()); // 결재 처리 날짜 생성
		
		EpayApprHistVO epayApprHistVO = new EpayApprHistVO();
		epayApprHistVO.setDraftInfoId(draftInfoId);
		epayApprHistVO.setEmplNo(user.getEmplyrNo());
		
		List<EpayApprHistVO> apprHistVOList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO);
		
		EpayApprHistVO vo = null;
		
		if(apprHistVOList.size() == 1){
			vo = apprHistVOList.get(0);
			
			vo.setApprTm(today);  // 결재승인 처리일시
			vo.setApprState("1"); // 결재상태 : 승인(1)
			vo.setComment(apprCm);    // 첨언
			
			this.epayApprInfoService.epayApprHist(vo); // 담당 결재자 결재이력 변경
		}
		
		// 결재 라인 완료 유무 검사(완료가 아닐 경우 다음 결재자의 결재이력 정보를 반환함)
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(vo);
		
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo")); // 기안정보에 저장할 다음 결재자 사원번호 입력 (임시 주석처리, 실 서비스 운영시 주석 해제 필요)
			
			EpayApprHistVO tmpApprHistVO = new EpayApprHistVO();
			tmpApprHistVO.setApprHistId(apprHistVO.get("apprHistId").toString());
			tmpApprHistVO.setDraftInfoId(apprHistVO.get("draftInfoId").toString());
			tmpApprHistVO.setEmplNo(apprHistVO.get("emplNo").toString());
			tmpApprHistVO.setApprState("0"); // 다음 결재자의 결재상태를 결재대기중으로 변경하여, 해당 결재자의 결재함 목록에 표시 되도록 함.
			tmpApprHistVO.setApprOrdr(apprHistVO.get("apprOrdr").toString());
			
			this.epayApprInfoService.updateEpayApprHist(tmpApprHistVO);
			// 다음 결재자 정보 세팅 완료
			
			// 다음 결재자에게 결재 요청 메세지 전송
			TisUtil.postURLforSlack(
					String.format(
							"%s님이 %s을(를) 기안하였습니다."
							, slackDraftInfoVO.get("userNm").toString()
							, slackDraftInfoVO.get("docformNm").toString()
							)
					, (String)apprHistVO.get("emplyrId")
					);
			
		}else{
			// 결재 완료 작업 처리
			// 1. 기안정보 결재 상태 결재 최종 승인(1)으로 변경
			epayDraftInfoVO.setApprEmplNo(""); // 최종 결재 승인이므로 기안정보의 다음 결재자 정보는 공백처리
			epayDraftInfoVO.setApprSttus("1");
			
			// 기안자에게 최종 결재 승인 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 승인 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO);
		
		Map<String,Object> map = new HashMap<String,Object>();	
		map.put("result", "success");

		return new ModelAndView("ajaxMainView", map);
	}
	
	// 결재 문서 반려
	@RequestMapping("/com/epay/appr/epayApprReturn.do")
	public ModelAndView epayApprReturn(ModelMap model, String draftInfoId, String apprReturnCm) 
			throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		EpayDraftInfoVO epayDraftInfoVO = new EpayDraftInfoVO();
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		
		EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(epayDraftInfoVO);
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime()); // 결재 처리 날짜 생성
		
		EpayApprHistVO epayApprHistVO = new EpayApprHistVO();
		epayApprHistVO.setDraftInfoId(draftInfoId);
		//epayApprHistVO.setEmplNo(user.getEmplyrNo()); // 임시 주석 처리 (실서비스 가동시 주석 해제 필요)
		epayApprHistVO.setEmplNo(user.getEmplyrNo());//"I-1802"); // 테스트
		
		List<EpayApprHistVO> apprHistVOList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO);
		
		EpayApprHistVO vo = null;
		
		if(apprHistVOList.size() == 1){
			vo = apprHistVOList.get(0);
			
			vo.setApprTm(today);  // 결재승인 처리일시
			vo.setApprState("2"); // 결재상태 : 반려(2)
			vo.setReturnCm(apprReturnCm); // 반려의견
			
			this.epayApprInfoService.epayApprHist(vo); // 담당 결재자 결재이력 변경
			this.epayApprInfoService.epayApprReturn(vo); // 담당 결재자의 결재 순서 다음 결재자 이력 정보 삭제
		}
		
		epayDraftInfoVO.setApprEmplNo(""); // 반려처리이므로 기안정보의 다음 결재자 정보는 공백처리
		epayDraftInfoVO.setApprSttus("2"); // 기안정보 결재 상태 : 반려(2)
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO);
		
		// 기안자에게 반려 메세지 전송
		TisUtil.postURLforSlack(
				String.format("%s님의 %s이(가) 반려 되었습니다."
						, (String)slackDraftInfoVO.get("userNm")
						, (String)slackDraftInfoVO.get("docformNm"))
			, (String)slackDraftInfoVO.get("emplyrId")
		);
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", "success");

		return new ModelAndView("ajaxMainView", map);
	}
	
	// 결재 문서 전결
	@RequestMapping("/com/epay/appr/epayAppDecide.do")
	public ModelAndView epayAppDecide(ModelMap model, String draftInfoId, String apprCm) 
			throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();	
		//map.put("epayExpHistList", "");
		
		return new ModelAndView("ajaxMainView", map);
	}
}
