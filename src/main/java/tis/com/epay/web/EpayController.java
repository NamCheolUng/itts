package tis.com.epay.web;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.TisUtil;
import tis.com.common.service.TisCodeService;
import tis.com.common.service.TisWorkDaysService;
import tis.com.epay.service.EpayApprHistVO;
import tis.com.epay.service.EpayApprInfoService;
import tis.com.epay.service.EpayApprLineVO;
import tis.com.epay.service.EpayApprLnCfgTmpVO;
import tis.com.epay.service.EpayApprLnCfgVO;
import tis.com.epay.service.EpayCnsulVO;
import tis.com.epay.service.EpayDocFormVO;
import tis.com.epay.service.EpayDraftInfoSearchVO;
import tis.com.epay.service.EpayDraftInfoService;
import tis.com.epay.service.EpayDraftInfoVO;
import tis.com.epay.service.EpayDraftVO;
import tis.com.epay.service.EpayExpCorHistVO;
import tis.com.epay.service.EpayExpCorVO;
import tis.com.epay.service.EpayExpHistVO;
import tis.com.epay.service.EpayExpVO;
import tis.com.epay.service.EpayHolidayVO;
import tis.com.epay.service.EpayTaskPersonVO;
import tis.com.financial.bankBook.service.TbBankcardmanageService;
import tis.com.financial.purchase.service.PurchaseDetailVO;
import tis.com.financial.purchase.service.PurchaseService;
import tis.com.financial.purchase.service.PurchaseVO;
import tis.com.financial.sales.service.SalesDetailVO;
import tis.com.financial.sales.service.SalesService;
import tis.com.financial.sales.service.SalesVO;
import tis.com.task.service.TaskService;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.ion.vct.service.EgovVcatnManageService;
import egovframework.com.uss.ion.vct.service.VcatnManageVO;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class EpayController {

	@Resource(name = "epayDraftInfoService")
	private EpayDraftInfoService epayDraftInfoService;
	
	@Resource(name = "epayApprInfoService")
	private EpayApprInfoService epayApprInfoService;
	
	@Resource(name = "egovEpayDraftIdGnrService")
	private EgovIdGnrService epayDraftIdGnrService;
	
	@Resource(name = "egovEpayDraftInfoIdGnrService")
	private EgovIdGnrService epayDraftInfoIdGnrService;
	
	@Resource(name = "egovEpayApprHistIdGnrService")
	private EgovIdGnrService epayApprHistIdGnrService;
	
	@Resource(name = "egovEpayExpIdGnrService")
	private EgovIdGnrService epayExpIdGnrService;
	
	@Resource(name = "egovEpayExpCorIdGnrService")
	private EgovIdGnrService epayExpCorIdGnrService;
	
	@Resource(name = "egovEpayExpCorHistIdGnrService")
	private EgovIdGnrService epayExpCorHistIdGnrService;
	
	@Resource(name = "egovEpayCnsulIdGnrService")
	private EgovIdGnrService epayCnsulIdGnrService;
	
	@Resource(name = "egovEpayHolidayIdGnrService")
	private EgovIdGnrService epayHolidayIdGnrService;
	
    @Resource(name = "EgovFileMngUtil")
    private EgovFileMngUtil fileUtil;
    
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
	
	@Resource(name = "TisCodeService")
	private TisCodeService tisCodeService;
	
	@Resource(name="taskService")
	private TaskService taskService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name = "salesService")
	private SalesService salesService;
	
	@Resource(name = "purchaseService")
	private PurchaseService purchaseService;
	
	@Resource(name = "tbBankcardmanageService")
	private TbBankcardmanageService tbBankcardmanageService;
	
	@Resource(name = "TisWorkDaysService")
	private TisWorkDaysService tisWorkDaysService;
	
	@Resource(name = "egovVcatnManageService")
    private EgovVcatnManageService egovVcatnManageService;
	
	@RequestMapping("/com/epay/draft/epayDraftInfoList.do")
	public String epayDraftInfoList(HttpServletRequest request, EpayDocFormVO epayDocFormVO, EpayDraftInfoVO epayDraftInfoVO, EpayDraftInfoSearchVO epayDraftInfoSearchVO, ModelMap model, String method) 
			throws Exception {

		//TisUtil.postURLforSlack("안녕하세요.결재바랍니다.", "jmj1116", "");
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String resultPage = "/tis/com/epay/draft/epayDraftInfoList"; // 기본 페이지 설정
		
		PaginationInfo paginationInfo = new PaginationInfo();
		
		paginationInfo.setCurrentPageNo(epayDraftInfoSearchVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(epayDraftInfoSearchVO.getRecordCountPerPage());
		paginationInfo.setPageSize(epayDraftInfoSearchVO.getPageSize());
		
		epayDraftInfoVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		epayDraftInfoVO.setLastIndex(paginationInfo.getLastRecordIndex());
		epayDraftInfoVO.setPageSize(paginationInfo.getPageSize());
		epayDraftInfoVO.setSortColumn(epayDraftInfoSearchVO.getSortColumn());
		epayDraftInfoVO.setSortOrderBy(epayDraftInfoSearchVO.getSortOrderBy());
		// 결재상태 조건 검색을 위한 VO 세팅
		epayDraftInfoVO.setSearchApprSttusCnd(epayDraftInfoSearchVO.getSearchApprSttusCnd());
		// 문서양식 조건 검색을 위한 VO 세팅
		epayDraftInfoVO.setSearchDocFormCnd(epayDraftInfoSearchVO.getSearchDocFormCnd());
		epayDraftInfoVO.setSearchSdate(epayDraftInfoSearchVO.getSearchSdate());
		epayDraftInfoVO.setSearchEdate(epayDraftInfoSearchVO.getSearchEdate());
		
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
				searchDocFormCnd += " A.doc_form_id = \'" + val + "\'";
				i++;
			}
		}
		// 결재상태 검색 조건 쿼리 문자열 생성
		if(arrApprSttus != null && arrApprSttus.length > 0){
			int i = 0;
			for (String val : arrApprSttus) {
				if(i > 0)searchApprSttusCnd += " OR";
				searchApprSttusCnd += " A.appr_sttus = \'" + val + "\'";
				i++;
			}
		}
		
		// 문서양식 체크박스 체크 상태 유지를 위한 문서양식 ID 배열 정보 전달
		model.addAttribute("docFormTot", docFormTot);
		model.addAttribute("arrDocForm", arrDocForm);
		// 결재상태 체크박스 체크 상태 유지를 위한 결재상태 ID 배열 정보 전달
		model.addAttribute("apprSttusTot", apprSttusTot);
		model.addAttribute("arrApprSttus", arrApprSttus);
		
		epayDraftInfoVO.setSearchDocFormCnd(searchDocFormCnd);
		epayDraftInfoVO.setSearchApprSttusCnd(searchApprSttusCnd);
		
		
		// 문서양식 목록 조회
		List<EpayDocFormVO> docFormList = this.epayDraftInfoService.selectEpayDocFormList(epayDocFormVO);
		
		// subpage 구분 파라미터
		model.addAttribute("method", method);
		model.addAttribute("docFormList", docFormList);
		
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// "I-1801"); // 테스트 사용자
		
		// 기안정보 조회
		List<EpayDraftInfoVO> draftInfoList = epayDraftInfoService.selectEpayDraftInfoList(epayDraftInfoVO);
		
		int recordCount = this.epayDraftInfoService.epayDraftInfoListCount(epayDraftInfoVO);
		
		paginationInfo.setTotalRecordCount(recordCount);
		
		model.addAttribute("resultList", draftInfoList);
		model.addAttribute("epayDraftInfoSearchVO", epayDraftInfoSearchVO);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return resultPage;
	}
	
	@RequestMapping(value="/com/epay/draft/draftBoxCnt.do")
	public String draftBoxCnt(@RequestParam("leftmenu") String leftmenu, ModelMap model) throws Exception {
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		EpayDraftInfoVO epayDraftInfoVO = new EpayDraftInfoVO();
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());
		epayDraftInfoVO.setSearchApprSttus("0");
		epayDraftInfoVO.setSearchApprSttusCnd(" A.appr_sttus = 0");
		
		int cnt = this.epayDraftInfoService.epayDraftInfoListCount(epayDraftInfoVO);
		
		model.addAttribute("newCnt", cnt);
		model.addAttribute("leftmenu", leftmenu);		
    	return "tis/com/inc/newEpay";
	}
	
	@RequestMapping("/com/epay/draft/epayDraftView.do")
	public String epayDraftView(String draftInfoId, EpayDraftInfoVO epayDraftInfoVO, EpayDraftVO epayDraftVO, EpayApprHistVO epayApprHistVO, ModelMap model, String method) 
			throws Exception{
		
		String resultPage = "/tis/com/epay/draft/epayDraftView"; // 기본 페이지 설정
		
		epayApprHistVO.setDraftInfoId(draftInfoId);
		
 		EpayDraftVO draft = this.epayDraftInfoService.selectEpayDraft(epayDraftVO);
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO);
		
		model.addAttribute("draft", draft);
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("apprHistList", epayApprHistList);
		
		return "/tis/com/epay/draft/epayDraftView";// 기안서 상세보기
	}
	// 품의서 작성
	@RequestMapping("/com/epay/draft/epayCnsulWrite.do")
	public String epayCnsulWrite(
			EpayCnsulVO epayCnsulVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			, String method
			, String draftInfoId
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String resultPage = "/tis/com/epay/draft/epayCnsulWrite"; // 기안서 작성
		// 오늘 날짜 정보 취득
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();
		String today = sDtFmt.format(cal.getTime());
		
		String year = String.valueOf(cal.get(Calendar.YEAR)) + "년 ";
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1) + "월 ";
		
		String tmpTitle = null;
		
		//String draftInfoId = epayDraftInfoVO.getDraftInfoId();
		
		if(draftInfoId != null && draftInfoId != "") {
			
			// 1.기안정보 조회
			EpayDraftInfoVO epayDraftInfoResult = this.epayDraftInfoService.selectEpayDraftInfo(epayDraftInfoVO);
			// 2.품의서 조회
			epayCnsulVO.setDraftInfoId(draftInfoId);
			EgovMap epayCnsulResult = this.epayDraftInfoService.selectEpayCnsul(epayCnsulVO);
			
			
			EpayApprHistVO tmpEpayApprHistVO = new EpayApprHistVO();
			tmpEpayApprHistVO.setDraftInfoId(draftInfoId);
			
			List<EpayApprHistVO> epayApprHistVOList = this.epayDraftInfoService.selectEpayApprHistList(tmpEpayApprHistVO); 
			
			model.addAttribute("epayDraftInfoVO", epayDraftInfoResult);
			model.addAttribute("epayCnsulVO", epayCnsulResult);
			
			
			/*EpayApprLnCfgTmpVO tmpApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
			tmpApprLnCfgTmpVO.setDraftInfoId(draftInfoId);
			model.addAttribute("epayApprLnCfgVO", this.epayDraftInfoService.selectEpayApprLnCfgTmpList(tmpApprLnCfgTmpVO));*/
			
			model.addAttribute("epayApprLnCfgVO", new EpayApprLnCfgVO());
			model.addAttribute("epayApprHistVOList", epayApprHistVOList); // 2018-03-12 추가 (결재라인 설정 후 임시 저장 하였을 경우 데이터 보존을 위한 변수)
			
			// 매출내역 리스트
			List<SalesVO> draftSalesList = (List<SalesVO>)salesService.selectDraftSalesList(epayDraftInfoVO.getDraftInfoId());
			model.put("draftSalesList", draftSalesList);
			
			// 매입내역 리스트
			List<PurchaseVO> draftPurchaseList = (List<PurchaseVO>)purchaseService.selectDraftPurchaseList(epayDraftInfoVO.getDraftInfoId());
			model.put("draftPurchaseList", draftPurchaseList);
		}
		else{
			tmpTitle = String.format("%s %s %s 품의서", year, month, user.getName());
			epayDraftInfoVO.setTitle(tmpTitle); // 기본제목 생성후 세팅     ex)17년12월 김철수 품의서
			
			epayDraftInfoVO.setRegDt(today); // 기안일자 초기값 세팅 : "2018/01/17"
			epayCnsulVO.setCnsulSdate(today); // 시행일자 초기값 세팅
			
			epayDraftInfoVO.setOrgnztNm(user.getOrgnztNm()); // 기안자 소속부서
			epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 기안자 ID (사원번호)
			epayDraftInfoVO.setUserNm(user.getName()); // 기안자명
		}
		
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(user.getEmplyrNo());
		epayTaskPersonVO.setSttus("1"); // 과제 담당자 진행상태가 참여중(1) 인 상태
		
		model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); // 부서별 결재라인 목록 전달
		
		return resultPage;
	}
	
	@RequestMapping("/com/epay/draft/getDraftApprLnCfgList.do")
	public ModelAndView getDraftApprLnCfgList(String draftInfoId) throws Exception{
	
		Map<String, Object> map = new HashMap<String, Object>();
		
		EpayApprLnCfgTmpVO tmpApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
		tmpApprLnCfgTmpVO.setDraftInfoId(draftInfoId);
		map.put("apprLnList", this.epayDraftInfoService.selectEpayApprLnCfgTmpList(tmpApprLnCfgTmpVO).getApprLnList());
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	// 품의서 : 신규->임시저장
	@RequestMapping(value="/com/epay/draft/epayCnsulWriteTemp.do")
	public String epayCnsulWriteTemp(
			EpayCnsulVO epayCnsulVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String cnsulId = this.epayCnsulIdGnrService.getNextStringId();// 신규 품의서 ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
			
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());

		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000000");// 품의서 폼양식 ID
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("3"); // 결재상태 (3:임시저장)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());//"I-1801");// 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
			
		// Code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		epayDraftInfoVO.setCode(code);
			
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
			
		// 품의서 정보 DB 등록 
		epayCnsulVO.setDraftInfoId(draftInfoId);
		epayCnsulVO.setCnsulId(cnsulId);
			
		this.epayDraftInfoService.insertEpayCnsul(epayCnsulVO);
		this.epayDraftInfoService.updateEpayCnsulSales(epayCnsulVO); 	// draftInfoId 로 검색 후 taskId 업데이트
		this.epayDraftInfoService.updateEpayCnsulPurchase(epayCnsulVO); // draftInfoId 로 검색 후 taskId 업데이트
		
		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.insertEpayApprLnCfgTmp(draftInfoId, epayApprLnCfgVO);
		}
				
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 품의서 : 임시저장->임시저장
	@RequestMapping(value="/com/epay/draft/epayCnsulWriteTempUpdate.do")
	public String epayCnsulWriteTempUpdate(
			EpayCnsulVO epayCnsulVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		// 1.품의서 업데이트
		this.epayDraftInfoService.updateEpayCnsul(epayCnsulVO);
		// 2.기안정보 업데이트
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
		// 3.매출 매입 내용 중 과제 ID 업데이트
		this.epayDraftInfoService.updateEpayCnsulSales(epayCnsulVO); 	// draftInfoId 로 검색 후 taskId 업데이트
		this.epayDraftInfoService.updateEpayCnsulPurchase(epayCnsulVO); // draftInfoId 로 검색 후 taskId 업데이트
		
		// 결재라인이 지정 되어 있을 경우 업데이트
		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.updateEpayApprLnCfgTmp(epayDraftInfoVO.getDraftInfoId(), epayApprLnCfgVO);
		}
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 품의서 : 신규 -> 결재요청 (문서 번호 생성은 결재요청시에만 생성되도록 함)
	@RequestMapping(value="/com/epay/draft/epayCnsulWriteInsert.do")
	public String epayCnsulWriteInsert(
			EpayCnsulVO epayCnsulVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		// 로그인 정보
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String cnsulId = this.epayCnsulIdGnrService.getNextStringId();// 품의서 신규 ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 품의서의 기안정보 ID 발급

		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		// 문서번호 생성용 날짜 포맷
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());
			
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000000");// 품의서 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서:지출, 휴가원:휴가, 품의서:품의
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s품의-%s%s", user.getAffiliationId(), today2, docNoId); // 
		
		epayDraftInfoVO.setDocNo(docNo);    // ex) S"I품의-18012601"
		epayDraftInfoVO.setNoYear(today2);  // ex) 180126
		epayDraftInfoVO.setNoId(docNoId);
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0");  // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
		
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
		
		// 다음 결재자 정보 세팅 시작 --------------------------------------------------------
		EpayApprHistVO vo = new EpayApprHistVO();
		vo.setDraftInfoId(draftInfoId);
		vo.setEmplNo(user.getEmplyrNo());
		
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(vo);
		
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료 --------------------------------------------------------
		
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
		
		// 품의서 정보 DB 등록 
		epayCnsulVO.setDraftInfoId(draftInfoId);
		epayCnsulVO.setCnsulId(cnsulId);
		                       
		this.epayDraftInfoService.insertEpayCnsul(epayCnsulVO);
		this.epayDraftInfoService.updateEpayCnsulSales(epayCnsulVO); // 품의서에 첨부된 매출 항목이 있을 경우,task id 수정 사항 반영
		this.epayDraftInfoService.updateEpayCnsulPurchase(epayCnsulVO); // 품의서에 첨부된 매입 항목이 있을 경우,task id 수정 사항 반영
		
		TisUtil.postURLforSlack(
				String.format("%s님이 품의서을(를) 기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 품의서 : 임시저장 -> 결재요청 (문서 번호 생성은 결재요청시에만 생성되도록 함)
	@RequestMapping("/com/epay/draft/epayCnsulWriteTempInsert.do")
	public String epayCnsulWriteTempInsert(
			EpayCnsulVO epayCnsulVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		// 로그인 정보
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String cnsulId = epayCnsulVO.getCnsulId();// 품의서 ID 조회
		String draftInfoId = epayDraftInfoVO.getDraftInfoId(); // 품의서의 기안정보 ID 조회

		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		// 문서번호 생성용 날짜 포맷
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());
		
		// 결재이력용 날짜 포맷
		SimpleDateFormat sDtApprHistFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Calendar cal3 = Calendar.getInstance();	
		String today3 = sDtApprHistFmt.format(cal3.getTime()); // 결재 처리 날짜 생성
		
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000000");// 품의서 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서:지출, 휴가원:휴가
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s품의-%s%s", user.getAffiliationId(), today2, docNoId); // 
		
		epayDraftInfoVO.setDocNo(docNo); // ex) S"I품의-18012601"
		epayDraftInfoVO.setNoYear(today2);  // ex) 180126
		epayDraftInfoVO.setNoId(docNoId);
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());//"I-1801");// 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
		
		// 기안상태 담당자 승인(0)으로 세팅
		//epayDraftInfoVO.setApprSttus("0");
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		
		//this.epayDraftInfoService.MakingEpayApprLn(this.epayDraftInfoService.selectEpayApprLnListByOrgzntId(code),user.getEmplyrNo(), draftInfoId);
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
		
		// 다음 결재자 정보 세팅 시작
		EpayApprHistVO vo = new EpayApprHistVO();
		vo.setDraftInfoId(draftInfoId);
		vo.setEmplNo(user.getEmplyrNo());
		
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(vo);
		
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 업데이트
		
		// 품의서 정보 DB 등록 
		epayCnsulVO.setDraftInfoId(draftInfoId);
		epayCnsulVO.setCnsulId(cnsulId);
		                       
		this.epayDraftInfoService.updateEpayCnsul(epayCnsulVO); // 품의서 정보 업데이트		
		this.epayDraftInfoService.updateEpayCnsulSales(epayCnsulVO); // 품의서에 첨부된 매출 항목이 있을 경우,task id 수정 사항 반영
		this.epayDraftInfoService.updateEpayCnsulPurchase(epayCnsulVO); // 품의서에 첨부된 매입 항목이 있을 경우,task id 수정 사항 반영
		
		TisUtil.postURLforSlack(
				String.format("%s님이 품의서을(를) 기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	@RequestMapping("/com/epay/draft/epayDraftWrite.do")
	public String epayDraftWrite(EpayDraftVO epayDraftVO, EpayDraftInfoVO epayDraftInfoVO, EpayApprHistVO epayApprHistVO, ModelMap model, String method) throws Exception{
		String resultPage = "/tis/com/epay/draft/epayDraftWrite"; // 기안서 작성
		// 오늘 날짜 정보 취득
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		
		Calendar cal = Calendar.getInstance();
		
		String today = sDtFmt.format(cal.getTime());
		
		System.out.println("- today : " + today);
		//System.out.println(sDtFmt.format(dtToday));
		
		// 과제 VO 조회하여 전달 필요.
		
		//draftInfoVO.setRegDt(sdFmt.format(dtToday)); // 기안일자 초기값 세팅 : "2018/01/17"
		
		epayDraftInfoVO.setRegDt(today); // 기안일자 초기값 세팅 : "2018/01/17"
		epayDraftVO.setDraftSDate("2018/01/18"); // 시행일자 초기값 세팅
		
		// 과제 및 일반과제 선택 여부에 따라서 해당 과제에 등록된 주 부서의 결재라인에 따라서 다르게 부서명이 표시되어야 함.
		
		//model.addAttribute("", attributeValue);
		
		// 과제 리스트 전달 필요
		
		return resultPage;
	}
	
	@RequestMapping(value="/com/epay/draft/epayDraftWriteInsert.do")
	public String epayDraftWriteInsert(final MultipartHttpServletRequest multiRequest, @ModelAttribute EpayDraftVO epayDraftVO, @ModelAttribute EpayDraftInfoVO epayDraftInfoVO, ModelMap model) throws Exception{
		
		String draftId = this.epayDraftIdGnrService.getNextStringId();         // 신규 기안서 ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Calendar cal = Calendar.getInstance(); //yyMMdd
		String today = sDtFmt.format(cal.getTime());
		
		// 선택된 과제에 속한 주 부서코드를 이용함(현재 테스트용 입력값:"DEPT03")
		
		// 기안서 작성시 기안 이력 테이블에 추가할 해당 부서의 결재라인구성 템플릿을 조회함. 
		//this.draftInfoService.selectApprLnCfgList(apprLnCfgVO);
		
		List<EpayApprLnCfgVO> apprLnCfgVOList = this.epayDraftInfoService.selectEpayApprLnListByOrgzntId("DEPT03");
		
		System.out.println("draftInfoVO.title : " + epayDraftInfoVO.getTitle());
		System.out.println("draftVO.draftContents : " +epayDraftVO.getDraftContents());
		System.out.println("draftVO.rm : " +epayDraftVO.getRm());
		System.out.println("draftInfoVO.regDt : " +epayDraftInfoVO.getRegDt());
		System.out.println("draftVO.draftSDate : " +epayDraftVO.getDraftSDate());
		System.out.println("draftVO.getAtchFileId : " + epayDraftVO.getAtchFileId());
		
		// 삽입 유효성 처리
		
		// 추가할 데이터의 키 값 세팅
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000003");// 기안서 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서:지출, 휴가원:휴가
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		epayDraftInfoVO.setDocNo("I기안-18011802"); // ex) S기안-18011801
		epayDraftInfoVO.setNoYear("20180118");
		epayDraftInfoVO.setNoId("02");
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo("I-1801");//.setDrafterId("test1"); // 기안자 ID
		epayDraftVO.setDraftId(draftId);
		epayDraftVO.setDraftInfoId(draftInfoId);
		epayDraftVO.setTaskId("TASK_000000000000001");;
		
				
		List<FileVO> fileList = null;
		String atchFileId = "";
				
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			fileList = fileUtil.parseFileInf(files, "BBS_", 0, "", "");
			atchFileId = fileMngService.insertFileInfs(fileList);
		    }
		
		epayDraftVO.setAtchFileId(atchFileId);

		// 조회된 결재라인 정보를 결재이력 테이블에 삽입
		Iterator<?> itr = apprLnCfgVOList.iterator();
		EpayApprLnCfgVO epayApprLnCfgVO;
		
		while(itr.hasNext()){ 
			epayApprLnCfgVO = (EpayApprLnCfgVO)itr.next();
			
			if(epayApprLnCfgVO != null){
				String apprHistId = this.epayApprHistIdGnrService.getNextStringId(); // 신규 기안서의 기안이력 ID 발급
				EpayApprHistVO epayApprHistVO = new EpayApprHistVO();
				epayApprHistVO.setApprHistId(apprHistId);   // 결재이력 ID
				epayApprHistVO.setDraftInfoId(draftInfoId); // 기안정보 ID
				epayApprHistVO.setApprOrdr(epayApprLnCfgVO.getApprOrdr()); // 결재순서
				epayApprHistVO.setEmplNo(epayApprLnCfgVO.getEmplNo());// .setApproverId(epayApprLnCfgVO.getApproverId()); // 결재자 ID(사원번호로 변경)
				epayApprHistVO.setApprTy(epayApprLnCfgVO.getApprTy()); // 결재유형
				epayApprHistVO.setPostn(epayApprLnCfgVO.getPosition()); // 결재라인구성 구분
				
				if(epayApprLnCfgVO.getApprOrdr().equals("1")){
					epayApprHistVO.setApprState("1"); // 기안자 본인은 결재상태 기본값 승인(1)로 설정
					epayApprHistVO.setApprTm(today);
				}
				
				this.epayDraftInfoService.insertEpayApprHist(epayApprHistVO); // 기안이력 정보 DB 등록
			}
		}
		
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
		this.epayDraftInfoService.insertEpayDraft(epayDraftVO); // 기안서 정보 DB 등록
		
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	@RequestMapping("/com/epay/draft/epayDraftModify.do")
	public String epayDraftModify(ModelMap model, String method) throws Exception{
		String resultPage = "ieetupms/subpage/ecd-pg01-11"; // 기안서 수정
		
		return resultPage;
	}
	
	@RequestMapping("/com/epay/draft/epayDraftDelete.do")
	public String epayDraftDelete(ModelMap model, String method) throws Exception{
		String resultPage = ""; // 기안서 기안취소
		
		return resultPage;
	}
	
	@RequestMapping("/com/epay/draft/epayExpView.do")
	public String epayExpView(String draftInfoId, EpayExpVO epayExpVO, EpayApprHistVO epayApprHistVO, ModelMap model, String method) 
			throws Exception{
		
		epayApprHistVO.setDraftInfoId(draftInfoId);
		
 		EgovMap exp = this.epayDraftInfoService.selectEpayExpWithDInfo(epayExpVO); // 지출명세서(개인) 정보 조회
 		
 		EpayExpHistVO epayExpHistVO = new EpayExpHistVO();
 		epayExpHistVO.setExpStatId(exp.get("expStatId").toString());
		List<?> epayExpHistList = this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO);
		
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO); // 결재이력라인 정보 조회
		
		model.addAttribute("exp", exp);
		model.addAttribute("apprHistList", epayApprHistList);
		model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); // 부서별 결재라인 목록 전달
		model.addAttribute("expHistList", epayExpHistList);
		
		return "/tis/com/epay/draft/epayExpView";// 기안서 상세보기
	}
	
	// 지출명세서(개인)
	@RequestMapping("/com/epay/draft/epayExpWrite.do")
	public String epayExpWrite(
			EpayExpVO epayExpVO
			, EpayExpHistVO epayExpHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprHistVO epayApprHistVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model) 
			throws Exception{
		String resultPage = "/tis/com/epay/draft/epayExpWrite"; // 지출명세서 작성
		
		// 오늘 날짜 정보 취득
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		String year = String.valueOf(cal.get(Calendar.YEAR)) + "년 ";
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1) + "월 "; 
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		
		String draftInfoId = epayDraftInfoVO.getDraftInfoId();
		
		if( draftInfoId != null & draftInfoId != ""){
			// 임시저장상태에서 화면 보여줄때
			//map.put("epayExpHistList", this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO));
			System.out.println(" - getDraftInfoId : " + epayDraftInfoVO.getDraftInfoId());
			
			EpayApprHistVO tmpEpayApprHistVO = new EpayApprHistVO();
			tmpEpayApprHistVO.setDraftInfoId(draftInfoId);
						
			List<EpayApprHistVO> epayApprHistVOList = this.epayDraftInfoService.selectEpayApprHistList(tmpEpayApprHistVO);
			
			// 1.기안정보 조회
			EpayDraftInfoVO epayDraftInfoResult = this.epayDraftInfoService.selectEpayDraftInfo(epayDraftInfoVO);
			// 2.지출명세서(개인) 조회 
			epayExpVO.setDraftInfoId(draftInfoId);
			EpayExpVO epayExpResult = this.epayDraftInfoService.selectEpayExp(epayExpVO);
			// 3.지출명세서(개인)에 속한 지출내역 조회
			epayExpHistVO.setExpStatId(epayExpResult.getExpStatId());
			List<?> epayExpHistListResult = this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO);
			
			model.addAttribute("epayDraftInfoVO", epayDraftInfoResult);
			model.addAttribute("epayExpVO", epayExpResult);
			model.addAttribute("epayExpHistVO", epayExpHistListResult);
			model.addAttribute("epayApprLnCfgVO", new EpayApprLnCfgVO());
			model.addAttribute("epayApprHistVOList", epayApprHistVOList); // 2018-03-12 추가 (결재라인 설정 후 임시 저장 하였을 경우 데이터 보존을 위한 변수)
		}
		else{
			epayExpVO.setExpSdate(today); // 지출기간 검색 시작일
			epayExpVO.setExpEdate(today); // 지출기간 검색 종료일			
		}
		
		// 기초데이터 생성
		epayDraftInfoVO.setOrgnztNm(user.getOrgnztNm()); // 기안자 소속부서
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 기안자 ID (사원번호)
		epayDraftInfoVO.setUserNm(user.getName()); // 기안자명
		
		String tmpTitle = String.format("%s %s %s 지출명세서", year, month, epayDraftInfoVO.getUserNm());
				
		epayDraftInfoVO.setTitle(tmpTitle); // 기본제목 생성후 세팅     ex)17년12월 김철수 지출명세서
		
		model.addAttribute("expSubjList", this.tisCodeService.selectCodeDetailList("COM501")); // 계정과목 코드 조회
		
		epayDraftInfoVO.setRegDt(today);     // 기안일자 초기값 세팅
		//epayExpHistVO.setSearchSdate(today); // 지출기간 검색 시작일
		//epayExpHistVO.setSearchEdate(today); // 지출기간 검색 종료일
		
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(user.getEmplyrNo());// "I-1801");
		epayTaskPersonVO.setSttus("1"); // 과제 담당자 진행상태가 참여중(1) 인 상태
		
		model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); // 부서별 결재라인 목록 전달
		// 기초데이터 생성 종료	
		
		return resultPage;
	}

	// 지출명세서(개인) - 모바일내역 불러오기
	@RequestMapping("/com/epay/draft/epayExpWriteLoadData.do")
	public ModelAndView epayExpWriteLoadData(EpayDraftInfoVO epayDraftInfoVO, EpayExpVO epayExpVO, EpayExpHistVO epayExpHistVO, ModelMap model) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();		
		System.out.println(epayDraftInfoVO.getSearchSdate());
		
		epayExpHistVO.setSearchSdate(epayExpVO.getExpSdate());
		epayExpHistVO.setSearchEdate(epayExpVO.getExpEdate());
		
		map.put("epayExpHistList", this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO));
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	// 지출명세서(개인) - 결재요청
	@RequestMapping(value="/com/epay/draft/epayExpWriteInsert.do")
	public String epayExpWriteInsert(
			EpayExpVO epayExpVO
			, EpayExpHistVO epayExpHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		/*String expTotPrice = epayExpVO.getExpTotPrice();
		
		if(!expTotPrice.equals("") && !expTotPrice.equals("0")){
			expTotPrice = expTotPrice.replaceAll(",","");
			epayExpVO.setExpTotPrice(expTotPrice);
		}*/
		
		String expStatId = this.epayExpIdGnrService.getNextStringId();// 신규 지출명세서(개인) ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());
		
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000001");// 지출명세서(개인) 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서:지출, 휴가원:휴가
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s지출-%s%s", user.getAffiliationId(), today2, docNoId); 
		
		epayDraftInfoVO.setDocNo(docNo); // ex) "I지출-18012601"
		epayDraftInfoVO.setNoYear(today2);
		epayDraftInfoVO.setNoId(docNoId);
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// "I-1801");// 기안자 ID
		epayDraftInfoVO.setRegDt(today);
		
		// 기안상태 담당자 승인(0)으로 세팅
		epayDraftInfoVO.setApprSttus("0");
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		
		//this.epayDraftInfoService.MakingEpayApprLn(this.epayDraftInfoService.selectEpayApprLnListByOrgzntId(code), user.getEmplyrNo(), draftInfoId);
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
		
		// 다음 결재자 정보 세팅 시작 (2018.02.20 추가)
		EpayApprHistVO tmpApprHistVO = new EpayApprHistVO();
		tmpApprHistVO.setDraftInfoId(draftInfoId);
		tmpApprHistVO.setEmplNo(user.getEmplyrNo());
		
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(tmpApprHistVO);
		
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료
		
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
		
		// 지출명세서(개인)정보 DB 등록 
		epayExpVO.setDraftInfoId(draftInfoId);
		epayExpVO.setExpStatId(expStatId);
		this.epayDraftInfoService.insertEpayExp(epayExpVO);
		
		// 지출내역 항목에 지출명세서 ID 삽입 
		if(epayExpHistVO.getList() != null){
			for (EpayExpHistVO vo: epayExpHistVO.getList()){
				vo.setExpStatId(expStatId); // 지출명세서 ID 등록
				this.epayDraftInfoService.updateEpayExpHist(vo);
				
				System.out.println(vo.getEmplNo());
				System.out.println(vo.getExpPlace());
			}
		}
		
		TisUtil.postURLforSlack(
				String.format("%s님이 개인 지출명세서을(를) 기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}

	// 지출명세서(개인) - 신규 -> 임시저장
	@RequestMapping(value="/com/epay/draft/epayExpWriteTemp.do")
	public String epayExpWriteTemp(
			EpayExpVO epayExpVO
			, EpayExpHistVO epayExpHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String expStatId = this.epayExpIdGnrService.getNextStringId();// 신규 지출명세서(개인) ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
				
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000001");// 지출명세서(개인) 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서:지출, 휴가원:휴가
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		//epayDraftInfoVO.setDocNo("I지출-18012601"); // ex) S기안-18011801
		//epayDraftInfoVO.setNoYear("20180126");
		//epayDraftInfoVO.setNoId("01");
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("3"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
		
		// 기안상태 임시저장(3)으로 세팅
		epayDraftInfoVO.setApprSttus("3");
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		
		epayDraftInfoVO.setCode(code);
		
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
		
		// 지출명세서(개인)정보 DB 등록 
		epayExpVO.setDraftInfoId(draftInfoId);
		epayExpVO.setExpStatId(expStatId);
		this.epayDraftInfoService.insertEpayExp(epayExpVO);
		
		// 지출내역 항목 업데이트
		if(epayExpHistVO.getList() != null){
			for (EpayExpHistVO vo: epayExpHistVO.getList()){
				//vo.setExpStatId(expStatId); // 지출명세서 ID 등록  [임시저장할 경우 세팅할 필요없음:결재요청시 저장필요]
				this.epayDraftInfoService.updateEpayExpHist(vo);
				
				System.out.println(vo.getEmplNo());
				System.out.println(vo.getExpPlace());
			}
		}

		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.insertEpayApprLnCfgTmp(draftInfoId, epayApprLnCfgVO);
		}
			
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 지출명세서(개인) - 임시저장 -> 결재요청 (결재이력 및 문서 정보 생성은 결재 요청시에만 처리)
	@RequestMapping(value="/com/epay/draft/epayExpWriteTempInsert.do")
	public String epayExpWriteTempInsert(
			EpayExpVO epayExpVO
			, EpayExpHistVO epayExpHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String expStatId = epayExpVO.getExpStatId(); // 지출명세서(개인) ID
		String draftInfoId = epayDraftInfoVO.getDraftInfoId(); // 지출명세서(개인)의 기안정보 ID
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());
		
		// 결재이력용 날짜 포맷
		/*SimpleDateFormat sDtApprHistFmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.KOREA);
		Calendar cal3 = Calendar.getInstance();	
		String today3 = sDtApprHistFmt.format(cal3.getTime());*/ // 결재 처리 날짜 생성

		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000001");// 지출명세서(개인) 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서:지출, 휴가원:휴가
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s지출-%s%s", user.getAffiliationId(), today2, docNoId);
		
		epayDraftInfoVO.setDocNo(docNo); // ex) "I지출-18012601"
		epayDraftInfoVO.setNoYear(today2);
		epayDraftInfoVO.setNoId(docNoId);
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
		
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		epayDraftInfoVO.setCode(code);
		
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
		
		// 다음 결재자 정보 세팅 시작
		EpayApprHistVO tmpApprHistVO = new EpayApprHistVO();
		tmpApprHistVO.setDraftInfoId(draftInfoId);
		tmpApprHistVO.setEmplNo(user.getEmplyrNo());
		
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(tmpApprHistVO);
		
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 업데이트
		
		// 지출명세서(개인)정보 DB 업데이트 
		epayExpVO.setDraftInfoId(draftInfoId);
		epayExpVO.setExpStatId(expStatId);
		this.epayDraftInfoService.updateEpayExp(epayExpVO);
		
		// 지출내역 항목에 지출명세서 ID 삽입 
		if(epayExpHistVO.getList() != null){
			for (EpayExpHistVO vo: epayExpHistVO.getList()){
				vo.setExpStatId(expStatId); // 지출명세서 ID 등록
				this.epayDraftInfoService.updateEpayExpHist(vo);
				
				System.out.println(vo.getEmplNo());
				System.out.println(vo.getExpPlace());
			}
		}
		
		TisUtil.postURLforSlack(
				String.format("%s님이 개인 지출명세서을(를) 기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 지출명세서(개인) - 임시저장 -> 임시저장
	@RequestMapping(value="/com/epay/draft/epayExpWriteTempUpdate.do")
	public String epayExpWriteTempUpdate(
			EpayExpVO epayExpVO
			, EpayExpHistVO epayExpHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String expStatId = epayExpVO.getExpStatId(); // 지출명세서(개인) ID
		String draftInfoId = epayDraftInfoVO.getDraftInfoId(); // 지출명세서(개인)의 기안정보 ID
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		

		epayDraftInfoVO.setDraftInfoId(draftInfoId);
				
		
		//epayDraftInfoVO.setDocFormId("DOCFORM_000000000001");// 지출명세서(개인) 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서:지출, 휴가원:휴가
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		//epayDraftInfoVO.setDocNo("I지출-18012601"); // ex) S기안-18011801
		//epayDraftInfoVO.setNoYear("20180126");
		//epayDraftInfoVO.setNoId("01");
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("3"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());//"I-1801");// 기안자 ID
		epayDraftInfoVO.setRegDt(today);
		
		// 기안상태 임시저장(3)으로 세팅
		epayDraftInfoVO.setApprSttus("3");
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		
		epayDraftInfoVO.setCode(code);
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 업데이트
		
		// 지출명세서(개인)정보 DB 업데이트
		epayExpVO.setDraftInfoId(draftInfoId);
		epayExpVO.setExpStatId(expStatId);
		this.epayDraftInfoService.updateEpayExp(epayExpVO);
		
		// 지출내역 항목에 지출명세서 ID 삽입 
		if(epayExpHistVO.getList() != null){
			for (EpayExpHistVO vo: epayExpHistVO.getList()){
				//vo.setExpStatId(expStatId); // 지출명세서 ID 등록
				this.epayDraftInfoService.updateEpayExpHist(vo);
				
				System.out.println(vo.getEmplNo());
				System.out.println(vo.getExpPlace());
			}
		}

		// 결재라인이 지정 되어 있을 경우 업데이트
		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.updateEpayApprLnCfgTmp(epayDraftInfoVO.getDraftInfoId(), epayApprLnCfgVO);
		}
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	// 매출 및 매입 등록 버튼 클릭 후 
	@RequestMapping("/com/epay/draft/epayCnsulTemp.do")
	public ModelAndView epayCnsulTemp(EpayDraftInfoVO epayDraftInfoVO, EpayCnsulVO epayCnsulVO, EpayApprLineVO epayApprLineVO, ModelMap model) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		Map<String,Object> map = new HashMap<String,Object>();	
		
		String cnsulId = this.epayCnsulIdGnrService.getNextStringId();// 신규 품의서 ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
			
			SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
			Calendar cal = Calendar.getInstance();	
			String today = sDtFmt.format(cal.getTime());

			epayDraftInfoVO.setDraftInfoId(draftInfoId);
			epayDraftInfoVO.setDocFormId("DOCFORM_000000000000");// 품의서 폼양식 ID
			epayDraftInfoVO.setDelAt("N");
			epayDraftInfoVO.setApprSttus("3"); // 결재상태 (3:임시저장)
			epayDraftInfoVO.setEmplNo(user.getEmplyrNo());//"I-1801");// 기안자 ID
			//epayDraftInfoVO.setRegDt(today);
			
			// 기안상태 임시저장(3)으로 세팅
			epayDraftInfoVO.setApprSttus("3");
			// Code
			String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
			
			epayDraftInfoVO.setCode(code);
			
			this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
			
			// 품의서 정보 DB 등록 
			epayCnsulVO.setDraftInfoId(draftInfoId);
			epayCnsulVO.setCnsulId(cnsulId);
			
			this.epayDraftInfoService.insertEpayCnsul(epayCnsulVO);
			
			map.put("draftInfoId", draftInfoId);
		
		//map.put("epayExpHistList", this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO));
		
		return new ModelAndView("ajaxMainView", map);
	}

	// 품의서 상세보기
	@RequestMapping("/com/epay/draft/epayCnsulView.do")
	public String epayCnsulView(String draftInfoId, EpayDraftInfoVO epayDraftInfoVO, EpayCnsulVO epayCnsulVO, EpayApprHistVO epayApprHistVO, ModelMap model, String method) 
			throws Exception{
		
		epayApprHistVO.setDraftInfoId(draftInfoId);                  
		
		EgovMap cnsul = this.epayDraftInfoService.selectEpayCnsulWithDInfo(epayCnsulVO);
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO);
		
		model.addAttribute("cnsul", cnsul);
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("apprHistList", epayApprHistList);
		
		// 매출내역 리스트
		List<SalesVO> draftSalesList = (List<SalesVO>)salesService.selectDraftSalesList(epayDraftInfoVO.getDraftInfoId());
		model.put("draftSalesList", draftSalesList);
		
		// 매입내역 리스트
		List<PurchaseVO> draftPurchaseList = (List<PurchaseVO>)purchaseService.selectDraftPurchaseList(epayDraftInfoVO.getDraftInfoId());
		model.put("draftPurchaseList", draftPurchaseList);
		
		return "/tis/com/epay/draft/epayCnsulView";// 품의서 상세보기
	}
	
	@RequestMapping(value="/com/epay/draft/epayCnsulWriteCancel.do")
	public String epayCnsulWriteCancel(
			EpayCnsulVO epayCnsulVO, 
			EpayDraftInfoVO epayDraftInfoVO, 
			ModelMap model, 
			String method) throws Exception{
		
		String draftInfoId = epayDraftInfoVO.getDraftInfoId();
		
		if(draftInfoId != null && draftInfoId != ""){
			this.epayDraftInfoService.deleteEpayCnsul(epayCnsulVO); // 품의서 삭제
			this.epayDraftInfoService.deleteEpayDraftInfo(epayDraftInfoVO); // 기안정보 삭제
			
			// 품의서에 소속된 매출 매입 정보 삭제
			this.salesService.deleteDraftSales((List<EgovMap>)this.salesService.selectDraftSalesList(draftInfoId));
			this.purchaseService.deleteDraftPurchase((List<EgovMap>)this.purchaseService.selectDraftPurchaseList(draftInfoId));
			
			// 품의서에 소속된 결재라인 임시 저장 데이터 삭제(존재 할 경우)
			EpayApprLnCfgTmpVO epayApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
			epayApprLnCfgTmpVO.setDraftInfoId(draftInfoId);
			this.epayDraftInfoService.deleteEpayApprLnCfgTmp(epayApprLnCfgTmpVO);
		}
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	@RequestMapping(value="/com/epay/draft/epayExpWriteCancel.do")
	public String epayExpWriteCancel(EpayExpVO epayExpVO, EpayDraftInfoVO epayDraftInfoVO, ModelMap model, String method) throws Exception{
		
		String epayDraftInfoId = epayDraftInfoVO.getDraftInfoId();
		
		if(epayDraftInfoId != null && epayDraftInfoId != ""){
			this.epayDraftInfoService.deleteEpayExp(epayExpVO);
			this.epayDraftInfoService.deleteEpayDraftInfo(epayDraftInfoVO);
			
			// 품의서에 소속된 결재라인 임시 저장 데이터 삭제(존재 할 경우)
			EpayApprLnCfgTmpVO epayApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
			epayApprLnCfgTmpVO.setDraftInfoId(epayDraftInfoId);
			this.epayDraftInfoService.deleteEpayApprLnCfgTmp(epayApprLnCfgTmpVO);
		}
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	// 휴가원 작성 및 수정 화면
	@RequestMapping("/com/epay/draft/epayHolidayWrite.do")
	public String epayHolidayWrite(
			EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			, String draftInfoId
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		// 오늘 날짜 정보 취득
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();
		String today = sDtFmt.format(cal.getTime());
				
		String year = String.valueOf(cal.get(Calendar.YEAR)) + "년 ";
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1) + "월 ";
		
		String tmpTitle = null;
		
		double remndrYrycCo, useYrycCo, possibleYryc = 0.0;
		
		VcatnManageVO vcatnManageVO1 = egovVcatnManageService.selectIndvdlYrycManage(user.getUniqId());
		
		if(vcatnManageVO1 != null){
			remndrYrycCo = vcatnManageVO1.getRemndrYrycCo();
			useYrycCo = vcatnManageVO1.getUseYrycCo();
			possibleYryc =  remndrYrycCo + useYrycCo; 
			model.addAttribute("vcatnManageVO",  vcatnManageVO1);
			//model.addAttribute("possibleYryc", possibleYryc);
		}
		
		if(draftInfoId != null && draftInfoId != "") {
			
			// 1.기안정보 조회
			EpayDraftInfoVO epayDraftInfoResult = this.epayDraftInfoService.selectEpayDraftInfo(epayDraftInfoVO);
			// 2.휴가원 조회
			epayHolidayVO.setDraftInfoId(draftInfoId);
			EgovMap epayHolidayResult = this.epayDraftInfoService.selectEpayHoliday(epayHolidayVO);
						
						
			EpayApprHistVO tmpEpayApprHistVO = new EpayApprHistVO();
			tmpEpayApprHistVO.setDraftInfoId(draftInfoId);
						
			List<EpayApprHistVO> epayApprHistVOList = this.epayDraftInfoService.selectEpayApprHistList(tmpEpayApprHistVO); 
						
			model.addAttribute("epayDraftInfoVO", epayDraftInfoResult);
			model.addAttribute("epayHolidayVO", epayHolidayResult);
						
			model.addAttribute("epayApprLnCfgVO", new EpayApprLnCfgVO());
			model.addAttribute("epayApprHistVOList", epayApprHistVOList); // 2018-03-12 추가 (결재라인 설정 후 임시 저장 하였을 경우 데이터 보존을 위한 변수)					
		}else{
			tmpTitle = String.format("%s %s %s 휴가원", year, month, user.getName());
			epayDraftInfoVO.setTitle(tmpTitle); // 기본제목 생성후 세팅    
			
			epayDraftInfoVO.setRegDt(today); // 기안일자 초기값 세팅 : "2018/01/17"
			
			epayDraftInfoVO.setOrgnztNm(user.getOrgnztNm()); // 기안자 소속부서
			epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 기안자 ID (사원번호)
			epayDraftInfoVO.setUserNm(user.getName()); // 기안자명
			
			epayHolidayVO.setHolDay("0");
			
			model.addAttribute("epayHolidayVO", epayHolidayVO);
		}
		
		return "/tis/com/epay/draft/epayHolidayWrite"; // 휴가원 작성
	}
	
	// 휴가원 - 결재요청
	@RequestMapping("/com/epay/draft/epayHolidayWriteInsert.do")
	public String epayHolidayWriteInsert(
			final MultipartHttpServletRequest multiRequest
			, EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprHistVO epayApprHistVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String holidayId = this.epayHolidayIdGnrService.getNextStringId();   // 신규 휴가원 ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
			
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());
			
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000004");// 휴가원 폼양식 ID
		
		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s휴가-%s%s", user.getAffiliationId(), today2, docNoId); 
		
		epayDraftInfoVO.setDocNo(docNo); // ex) "I휴가-18012601"
		epayDraftInfoVO.setNoYear(today2);
		epayDraftInfoVO.setNoId(docNoId);
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// 기안자 ID
		epayDraftInfoVO.setRegDt(today); 
		
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
			
		// 다음 결재자 정보 세팅 시작
		EpayApprHistVO tmpApprHistVO = new EpayApprHistVO();
		tmpApprHistVO.setDraftInfoId(draftInfoId);
		tmpApprHistVO.setEmplNo(user.getEmplyrNo());
			
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(tmpApprHistVO);
			
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료
			
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
			
		// 휴가원 정보 DB 등록 
		epayHolidayVO.setDraftInfoId(draftInfoId);
		epayHolidayVO.setHolidayId(holidayId);
		
		this.epayDraftInfoService.insertEpayHoliday(epayHolidayVO, multiRequest);
		
		TisUtil.postURLforSlack(
				String.format("%s님이 휴가원을(를) 기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 휴가원 - 신규 -> 임시저장
	@RequestMapping(value="/com/epay/draft/epayHolidayWriteTemp.do")
	public String epayHolidayWriteTemp(
			final MultipartHttpServletRequest multiRequest
			, EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			) throws Exception{
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
			
		String holidayId = this.epayHolidayIdGnrService.getNextStringId();// 신규 휴가원 ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
			
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
			
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000004");// 휴가원 폼양식 ID
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("3"); // 결재상태 (3:임시저장)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// 기안자 ID

		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
			
		// 휴가원 정보 DB 등록 
		epayHolidayVO.setDraftInfoId(draftInfoId);
		epayHolidayVO.setHolidayId(holidayId);
		this.epayDraftInfoService.insertEpayHoliday(epayHolidayVO, multiRequest);
			
		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.insertEpayApprLnCfgTmp(draftInfoId, epayApprLnCfgVO);
		}
				
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 휴가원 - 임시저장 -> 임시저장
	@RequestMapping(value="/com/epay/draft/epayHolidayWriteTempUpdate.do")
	public String epayHolidayWriteTempUpdate(
			final MultipartHttpServletRequest multiRequest
			, EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			) throws Exception{
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
			
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
			
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 업데이트
			
		// 휴가원 정보 DB 업데이트
		epayHolidayVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		this.epayDraftInfoService.updateEpayHoliday(epayHolidayVO, multiRequest);
			
		// 결재라인이 지정 되어 있을 경우 업데이트
		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.updateEpayApprLnCfgTmp(epayDraftInfoVO.getDraftInfoId(), epayApprLnCfgVO);
		}
			
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 휴가원 : 임시저장 -> 결재요청 (문서 번호 생성은 결재요청시에만 생성되도록 함)
	@RequestMapping("/com/epay/draft/epayHolidayWriteTempInsert.do")
	public String epayHolidayWriteTempInsert(
			final MultipartHttpServletRequest multiRequest
			, EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			) throws Exception{
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String draftInfoId = epayDraftInfoVO.getDraftInfoId(); // 휴가원의 기안정보 ID 조회
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());

		epayDraftInfoVO.setDocFormId("DOCFORM_000000000004");// 휴가원 폼양식 ID

		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s휴가-%s%s", user.getAffiliationId(), today2, docNoId);
		
		epayDraftInfoVO.setDocNo(docNo); // ex) "I휴가-18012601"
		epayDraftInfoVO.setNoYear(today2);
		epayDraftInfoVO.setNoId(docNoId);
		//epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
		//String code = epayApprLineVO.getCode();
		//epayDraftInfoVO.setCode(code); // 선택된 결재라인의 부서 코드(CODE)
		
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
		
		// 다음 결재자 정보 세팅 시작
		EpayApprHistVO tmpApprHistVO = new EpayApprHistVO();
		tmpApprHistVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		tmpApprHistVO.setEmplNo(user.getEmplyrNo());
		
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(tmpApprHistVO);
		
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 업데이트
		
		// 휴가원 정보 DB 업데이트 
		epayHolidayVO.setDraftInfoId(draftInfoId);
		this.epayDraftInfoService.updateEpayHoliday(epayHolidayVO, multiRequest);
		
		TisUtil.postURLforSlack(
				String.format("%s님이 휴가원을(를) 기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
		}
	
	// 휴가원 작성 취소 또는 문서 삭제
	@RequestMapping(value="/com/epay/draft/epayHolidayWriteCancel.do")
	public String epayHolidayWriteCancel(
			EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, ModelMap model
			) throws Exception{
			
		String epayDraftInfoId = epayDraftInfoVO.getDraftInfoId();
			
		if(epayDraftInfoId != null && epayDraftInfoId != ""){
			this.epayDraftInfoService.deleteEpayHoliday(epayHolidayVO);
			this.epayDraftInfoService.deleteEpayDraftInfo(epayDraftInfoVO);
				
			// 품의서에 소속된 결재라인 임시 저장 데이터 삭제(존재 할 경우)
			EpayApprLnCfgTmpVO epayApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
			epayApprLnCfgTmpVO.setDraftInfoId(epayDraftInfoId);
			this.epayDraftInfoService.deleteEpayApprLnCfgTmp(epayApprLnCfgTmpVO);
		}
			
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	@RequestMapping("/com/epay/draft/epayHolidayView.do")
	public String epayHolidayView(
			String draftInfoId
			, EpayHolidayVO epayHolidayVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			) throws Exception{
		
		epayApprHistVO.setDraftInfoId(draftInfoId);
		
		EgovMap holiday = this.epayDraftInfoService.selectEpayHolidayWithDInfo(epayHolidayVO); // 휴가원 정보 조회
 		
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO); // 결재이력라인 정보 조회
		
		model.addAttribute("holiday", holiday);
		model.addAttribute("apprHistList", epayApprHistList);
		//model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); // 부서별 결재라인 목록 전달
		
		return "/tis/com/epay/draft/epayHolidayView";// 휴가원 상세보기
	}
	
	@RequestMapping("/com/epay/draft/epaySalesUpdate.do")
	public String epaySalesUpdate(EpayDraftInfoVO epayDraftInfoVO, EpayCnsulVO epayCnsulVO, ModelMap model, String method, String salesId) throws Exception {
		
		EgovMap salesMap = salesService.selectSales(salesId);
		model.addAttribute("salesVO", salesMap);
		
		List<EgovMap> salesDetailMap = (List<EgovMap>)salesService.selectSalesDetailList(salesId);
		model.addAttribute("salesDetailVO", salesDetailMap);
		
		model.addAttribute("tradeSeptList", tisCodeService.selectCodeDetailList("COM502"));
		model.addAttribute("payPointList", tisCodeService.selectCodeDetailList("COM505"));
		model.addAttribute("payWayList", tisCodeService.selectCodeDetailList("COM506"));
		
		model.addAttribute("draftInfoId", (String)salesMap.get("draftInfoId")); // test
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		model.addAttribute("apprSttus", epayDraftInfoVO.getApprSttus());
		
		return "/tis/com/epay/draft/epaySalesWrite";
	}
	
	@RequestMapping("/com/epay/draft/epaySalesWrite.do")
	public String epaySalesWrite(EpayDraftInfoVO epayDraftInfoVO, 
			EpayCnsulVO epayCnsulVO, 
			EpayApprLineVO epayApprLineVO, 
			ModelMap model, 
			String method) throws Exception {
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String draftInfoId, cnsulId, taskId = null;
		
		draftInfoId = epayDraftInfoVO.getDraftInfoId();
		taskId = epayCnsulVO.getTaskId();
		
		if(draftInfoId == null || draftInfoId == ""){
			
			cnsulId = this.epayCnsulIdGnrService.getNextStringId();// 신규 품의서 ID 발급
			draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
				
				SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
				Calendar cal = Calendar.getInstance();	
				String today = sDtFmt.format(cal.getTime());

				epayDraftInfoVO.setDraftInfoId(draftInfoId);
				epayDraftInfoVO.setDocFormId("DOCFORM_000000000000");// 품의서 폼양식 ID
				epayDraftInfoVO.setDelAt("N");
				epayDraftInfoVO.setApprSttus("3"); // 결재상태 (3:임시저장)
				epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// 기안자 ID
				//epayDraftInfoVO.setRegDt(today);
				
				// 기안상태 임시저장(3)으로 세팅
				epayDraftInfoVO.setApprSttus("3");
				// Code
				String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
				
				epayDraftInfoVO.setCode(code);
				
				this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
				
				// 품의서 정보 DB 등록 
				epayCnsulVO.setDraftInfoId(draftInfoId);
				epayCnsulVO.setCnsulId(cnsulId);
				
				this.epayDraftInfoService.insertEpayCnsul(epayCnsulVO);
		}else{ // 신규 문서가 아니더라도 매번 기안정보 및 품의서 정보 업데이트 작업 수행
			this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO);
			this.epayDraftInfoService.updateEpayCnsul(epayCnsulVO);
		}
		
		model.addAttribute("tradeSeptList", tisCodeService.selectCodeDetailList("COM502"));
		model.addAttribute("payPointList", tisCodeService.selectCodeDetailList("COM505"));
		model.addAttribute("payWayList", tisCodeService.selectCodeDetailList("COM506"));
		
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("apprSttus", epayDraftInfoVO.getApprSttus());
		
		return "/tis/com/epay/draft/epaySalesWrite";
	}
	
	@RequestMapping("/com/epay/draft/epaySalesWriteInsert.do")
	public String epaySalesWriteInsert(ModelMap model,
			final MultipartHttpServletRequest multiRequest,
			String method,
			SalesVO salesVO,
			SalesDetailVO salesDetailVO) throws Exception {
		
		salesService.insertSales(salesVO, salesDetailVO.getList(), multiRequest);
		
		model.addAttribute("draftInfoId", salesVO.getDraftInfoId());
		
		return "redirect:/com/epay/draft/epayCnsulWrite.do";
	}
	
	@RequestMapping("/com/epay/draft/epaySalesWriteUpdate.do")
	public String epaySalesWriteUpdate(EpayDraftInfoVO epayDraftInfoVO, 
			EpayCnsulVO epayCnsulVO,
			ModelMap model,
			final MultipartHttpServletRequest multiRequest,
			String method,
			SalesVO salesVO,
			SalesDetailVO salesDetailVO) throws Exception {
		
		salesService.updateDraftSales(salesVO, salesDetailVO.getList(), multiRequest);
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		
		return "redirect:/com/epay/draft/epayCnsulWrite.do";
	}
	
	@RequestMapping("/com/epay/draft/epaySalesView.do")
	public String epaySalesView(ModelMap model, EpayDraftInfoVO epayDraftInfoVO, String salesId) throws Exception {
		
		EgovMap salesMap = salesService.selectSales(salesId);
		model.addAttribute("salesVO", salesMap);
		
		List<EgovMap> salesDetailMap = (List<EgovMap>)salesService.selectSalesDetailList(salesId);
		model.addAttribute("salesDetailVO", salesDetailMap);
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		
		return "/tis/com/epay/draft/epaySalesView";
	}
	
	@RequestMapping("/com/epay/draft/epayPurchaseUpdate.do")
	public String epayPurchaseUpdate(EpayDraftInfoVO epayDraftInfoVO, EpayCnsulVO epayCnsulVO, ModelMap model, String method, String purchaseId) throws Exception {
		
		EgovMap purchaseMap = purchaseService.selectPurchase(purchaseId);
		model.addAttribute("purchaseVO", purchaseMap);
		
		List<EgovMap> purchaseDetailMap = (List<EgovMap>)purchaseService.selectPurchaseDetailList(purchaseId);
		model.addAttribute("purchaseDetailVO", purchaseDetailMap);
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		model.addAttribute("apprSttus", epayDraftInfoVO.getApprSttus());
		
		String resultPage = "/tis/com/epay/draft/epayPurchaseWrite";
		
		return resultPage;
	}
	
	@RequestMapping("/com/epay/draft/epayPurchaseWrite.do")
	public String epayPurchaseWrite(EpayDraftInfoVO epayDraftInfoVO, EpayCnsulVO epayCnsulVO, EpayApprLineVO epayApprLineVO, ModelMap model, String method) throws Exception {
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String draftInfoId, cnsulId, taskId = null;
		
		draftInfoId = epayDraftInfoVO.getDraftInfoId();
		taskId = epayCnsulVO.getTaskId();
		
		if(draftInfoId == null || draftInfoId == ""){
			
			cnsulId = this.epayCnsulIdGnrService.getNextStringId();// 신규 품의서 ID 발급
			draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
				
			SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
			Calendar cal = Calendar.getInstance();	
			String today = sDtFmt.format(cal.getTime());

			epayDraftInfoVO.setDraftInfoId(draftInfoId);
			epayDraftInfoVO.setDocFormId("DOCFORM_000000000000");// 품의서 폼양식 ID
			epayDraftInfoVO.setDelAt("N");
			epayDraftInfoVO.setApprSttus("3"); // 결재상태 (3:임시저장)
			epayDraftInfoVO.setEmplNo(user.getEmplyrNo());//"I-1801");// 기안자 ID
			//epayDraftInfoVO.setRegDt(today);
				
			// 기안상태 임시저장(3)으로 세팅
			epayDraftInfoVO.setApprSttus("3");
			// Code
			String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
				
			epayDraftInfoVO.setCode(code);
				
			this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
				
			// 품의서 정보 DB 등록 
			epayCnsulVO.setDraftInfoId(draftInfoId);
			epayCnsulVO.setCnsulId(cnsulId);
				
			this.epayDraftInfoService.insertEpayCnsul(epayCnsulVO);
		}else{ // 신규 문서가 아니더라도 매번 기안정보 및 품의서 정보 업데이트 작업 수행
			this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO);
			this.epayDraftInfoService.updateEpayCnsul(epayCnsulVO);
		}
		
		model.addAttribute("tradeSeptList", tisCodeService.selectCodeDetailList("COM502"));
		model.addAttribute("payPointList", tisCodeService.selectCodeDetailList("COM505"));
		model.addAttribute("payWayList", tisCodeService.selectCodeDetailList("COM506"));
		
		model.addAttribute("draftInfoId", draftInfoId);
		model.addAttribute("taskId", taskId);
		model.addAttribute("apprSttus", epayDraftInfoVO.getApprSttus());
		
		return "/tis/com/epay/draft/epayPurchaseWrite";
	}
	
	@RequestMapping("/com/epay/draft/epayPurchaseWriteInsert.do")
	public String epayPurchaseWriteInsert(ModelMap model,
			final MultipartHttpServletRequest multiRequest,
			String method,
			PurchaseVO purchaseVO,
			PurchaseDetailVO purchaseDetailVO) throws Exception {
		
		purchaseService.insertPurchase(purchaseVO, purchaseDetailVO.getList(), multiRequest);
		
		model.addAttribute("draftInfoId", purchaseVO.getDraftInfoId());
		
		return "redirect:/com/epay/draft/epayCnsulWrite.do";
	}
	
	@RequestMapping("/com/epay/draft/epayPurchaseWriteUpdate.do")
	public String epayPurchaseWriteUpdate(EpayDraftInfoVO epayDraftInfoVO, 
			EpayCnsulVO epayCnsulVO, 
			ModelMap model,
			final MultipartHttpServletRequest multiRequest,
			String method,
			PurchaseVO purchaseVO,
			PurchaseDetailVO purchaseDetailVO) throws Exception {
		
		String draftInfoId = null;
		draftInfoId = epayDraftInfoVO.getDraftInfoId();
		
		purchaseService.updateDraftPurchase(purchaseVO, purchaseDetailVO.getList(), multiRequest);
		
		model.put("draftInfoId", draftInfoId);
		
		return "redirect:/com/epay/draft/epayCnsulWrite.do";
	}
	
	@RequestMapping("/com/epay/draft/epayPurchaseView.do")
	public String epayPurchaseView(ModelMap model, EpayDraftInfoVO epayDraftInfoVO, String purchaseId) throws Exception {
		
		EgovMap purchaseMap = purchaseService.selectPurchase(purchaseId);
		model.addAttribute("purchaseVO", purchaseMap);
		
		List<EgovMap> purchaseDetailMap = (List<EgovMap>)purchaseService.selectPurchaseDetailList(purchaseId);
		model.addAttribute("purchaseDetailVO", purchaseDetailMap);
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		
		return "/tis/com/epay/draft/epayPurchaseView";
	}
	
	// 지출명세서(법인)
	@RequestMapping("/com/epay/draft/epayExpCorWrite.do")
	public String epayExpCorWrite(
			EpayExpCorVO epayExpCorVO
			, EpayExpCorHistVO epayExpCorHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprHistVO epayApprHistVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method) 
			throws Exception{
			
		// 오늘 날짜 정보 취득
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
			
		String year = String.valueOf(cal.get(Calendar.YEAR)) + "년 ";
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1) + "월 "; 
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "home/EgovLoginUsr";
		}
			
		String draftInfoId = epayDraftInfoVO.getDraftInfoId();
			
		if( draftInfoId != null & draftInfoId != ""){
			// 임시저장상태에서 화면 보여줄때
			//map.put("epayExpHistList", this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO));
			System.out.println(" - getDraftInfoId : " + epayDraftInfoVO.getDraftInfoId());
			
			EpayApprHistVO tmpEpayApprHistVO = new EpayApprHistVO();
			tmpEpayApprHistVO.setDraftInfoId(draftInfoId);
						
			List<EpayApprHistVO> epayApprHistVOList = this.epayDraftInfoService.selectEpayApprHistList(tmpEpayApprHistVO); 
			
			// 1.기안정보 조회
			EpayDraftInfoVO epayDraftInfoResult = this.epayDraftInfoService.selectEpayDraftInfo(epayDraftInfoVO);
			// 2.지출명세서(법인) 조회 
			epayExpCorVO.setDraftInfoId(draftInfoId);
			EpayExpCorVO epayExpCorResult = this.epayDraftInfoService.selectEpayExpCor(epayExpCorVO);
			// 3.지출명세서(법인)에 속한 지출내역 조회
			epayExpCorHistVO.setExpcorStatId(epayExpCorResult.getExpcorStatId());
			List<?> epayExpHistListResult = this.epayDraftInfoService.selectEpayExpCorHistList(epayExpCorHistVO);
				
			model.addAttribute("epayDraftInfoVO", epayDraftInfoResult);
			model.addAttribute("epayExpCorVO", epayExpCorResult);
			model.addAttribute("epayExpCorHistList", epayExpHistListResult);
			model.addAttribute("epayApprLnCfgVO", new EpayApprLnCfgVO());
			model.addAttribute("epayApprHistVOList", epayApprHistVOList); // 2018-03-12 추가 (결재라인 설정 후 임시 저장 하였을 경우 데이터 보존을 위한 변수)
		}
		else{
			epayDraftInfoVO.setCode(user.getOrgnztId()); // 결재라인 부서 코드
			
			// 기초데이터 생성
			epayDraftInfoVO.setOrgnztNm(user.getOrgnztNm()); // 기안자 소속부서
			epayDraftInfoVO.setEmplNo(user.getEmplyrNo());   // 기안자 ID (사원번호)
			epayDraftInfoVO.setUserNm(user.getName());       // 기안자명
			
			String tmpTitle = String.format("%s %s %s 법인카드 지출명세서", year, month, epayDraftInfoVO.getUserNm());
			
			epayDraftInfoVO.setTitle(tmpTitle); // 기본제목 생성후 세팅     ex)17년12월 김철수 법인카드 지출명세서
			epayDraftInfoVO.setRegDt(today);     // 기안일자 초기값 세팅
			
			model.addAttribute("epayDraftInfoVO", epayDraftInfoVO);
		}
		
		
		// 계정과목 코드  목록 조회
		model.addAttribute("expcorSubjList", this.tisCodeService.selectCodeDetailList("COM501")); 	
			
		// 카드 목록 조회
		model.addAttribute("tbBankcardmanageVOList", this.tbBankcardmanageService.selectTbBankcardmanageList(null));
		model.addAttribute("emplyrList", this.epayDraftInfoService.selectEmplyrList());
				
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(user.getEmplyrNo());// "I-1801");
		epayTaskPersonVO.setSttus("1"); // 과제 담당자 진행상태가 참여중(1) 인 상태
			
		
		model.addAttribute("taskList", this.epayDraftInfoService.selectEpayTaskAllList()); // 모든 과제 목록 조회 및 전달
		//model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); // 부서별 결재라인 목록 전달
		
		// 기초데이터 생성 종료	
			
		return "/tis/com/epay/draft/epayExpCorWrite"; // 지출명세서(법인) 작성;
	}
	
	// 지출명세서(법인) - 결재요청
	@RequestMapping(value="/com/epay/draft/epayExpCorWriteInsert.do")
	public String epayExpCorWriteInsert(
			final MultipartHttpServletRequest multiRequest
			, EpayExpCorVO epayExpCorVO
			, EpayExpCorHistVO epayExpCorHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
			
		String expcorStatId = this.epayExpCorIdGnrService.getNextStringId();   // 신규 지출명세서(법인) ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
			
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
			
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());
			
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000002");// 지출명세서(법인) 폼양식 ID
		//**********************************/
		// [문서번호 생성 참고] 
		// I:IEETU, S:S-MAKER
		// 기안서:기안, 지출명세서(개인):지출, 휴가원:휴가, 지출명세서(법인):지법 
		// 구분자:-
		// 작성일:YYMMDD(6자리)
		// 일련번호:DD(2자리)
		//**********************************/
		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s지법-%s%s", user.getAffiliationId(), today2, docNoId); 
			
		epayDraftInfoVO.setDocNo(docNo); // ex) "I지법-18012601"
		epayDraftInfoVO.setNoYear(today2);
		epayDraftInfoVO.setNoId(docNoId);
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// 기안자 ID
		epayDraftInfoVO.setRegDt(today);
			
		// 기안상태 담당자 승인(0)으로 세팅
		//epayDraftInfoVO.setApprSttus("0");
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
			
		//this.epayDraftInfoService.MakingEpayApprLn(this.epayDraftInfoService.selectEpayApprLnListByOrgzntId(code), user.getEmplyrNo(), draftInfoId);
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
			
		// 다음 결재자 정보 세팅 시작 (2018.02.20 추가)
		EpayApprHistVO tmpApprHistVO = new EpayApprHistVO();
		tmpApprHistVO.setDraftInfoId(draftInfoId);
		tmpApprHistVO.setEmplNo(user.getEmplyrNo());
			
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(tmpApprHistVO);
			
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료
			
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
			
		// 지출명세서(개인)정보 DB 등록 
		epayExpCorVO.setDraftInfoId(draftInfoId);
		epayExpCorVO.setExpcorStatId(expcorStatId);
		
		this.epayDraftInfoService.insertEpayExpCor(epayExpCorVO, multiRequest);
			
		// 지출내역 항목에 지출명세서 ID 삽입 
		if(epayExpCorHistVO.getList() != null){
			for (EpayExpCorHistVO vo: epayExpCorHistVO.getList()){
				if(vo.getTaskId() != null){
					//vo.setExpcorHistId(this.epayExpCorHistIdGnrService.getNextStringId()); // 지출내역 ID 등록
					vo.setExpcorStatId(expcorStatId); // 지출명세서 ID 등록
					this.epayDraftInfoService.insertEpayExpCorHist(vo);
				}
			}
		}			
		
		TisUtil.postURLforSlack(
				String.format("%s님이  법인 지출명세서을(를)  기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
			
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}

	// 지출명세서(법인) - 임시저장 -> 결재요청 (결재이력 및 문서 정보 생성은 결재 요청시에만 처리)
	@RequestMapping(value="/com/epay/draft/epayExpCorWriteTempInsert.do")
	public String epayExpCorWriteTempInsert(
			final MultipartHttpServletRequest multiRequest
			, EpayExpCorVO epayExpCorVO
			, EpayExpCorHistVO epayExpCorHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String draftInfoId = epayDraftInfoVO.getDraftInfoId(); // 품의서의 기안정보 ID 조회
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		SimpleDateFormat sDtDocNoFmt = new SimpleDateFormat("yyMMdd", Locale.KOREA);
		Calendar cal2 = Calendar.getInstance();	
		String today2 = sDtDocNoFmt.format(cal2.getTime());

		epayDraftInfoVO.setDocFormId("DOCFORM_000000000002");// 지출명세서(개인) 폼양식 ID

		String docNoId = this.epayDraftInfoService.selectEpayMaxDocNo(epayDraftInfoVO);
		String docNo = String.format("%s지법-%s%s", user.getAffiliationId(), today2, docNoId);
		
		epayDraftInfoVO.setDocNo(docNo); // ex) "I지법-18012601"
		epayDraftInfoVO.setNoYear(today2);
		epayDraftInfoVO.setNoId(docNoId);
		//epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("0"); // 결재상태 (0:결재진행중)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
		String code = epayApprLineVO.getCode();
		epayDraftInfoVO.setCode(code); // 선택된 결재라인의 부서 코드(CODE)
		
		this.epayDraftInfoService.MakingEpayApprLn(epayApprLnCfgVO.getApprLnList(), user.getEmplyrNo(), draftInfoId);
		
		// 다음 결재자 정보 세팅 시작
		EpayApprHistVO tmpApprHistVO = new EpayApprHistVO();
		tmpApprHistVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		tmpApprHistVO.setEmplNo(user.getEmplyrNo());
		
		EgovMap apprHistVO = this.epayApprInfoService.epayApprHistNextEmplNo(tmpApprHistVO);
		
		if(apprHistVO != null){ // null이 아닐 경우, 다음 결재자 존재함
			epayDraftInfoVO.setApprEmplNo((String)apprHistVO.get("emplNo"));
		}
		// 다음 결재자 정보 세팅 완료
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 업데이트
		
		// 지출명세서(법인)정보 DB 업데이트 
		epayExpCorVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		this.epayDraftInfoService.updateEpayExpCor(epayExpCorVO, multiRequest);
		
		// 지출내역 항목에 지출명세서 ID 삽입 
		if(epayExpCorHistVO.getList() != null){
			
			EpayExpCorHistVO tmpExpCorHistVO = new EpayExpCorHistVO();
			tmpExpCorHistVO.setExpcorStatId(epayExpCorVO.getExpcorStatId());
			
			this.epayDraftInfoService.deleteEpayExpCorHist(epayExpCorHistVO);
			
			for (EpayExpCorHistVO vo: epayExpCorHistVO.getList()){
				
				if(vo.getTaskId() == null) continue;
				
				vo.setExpcorStatId(epayExpCorVO.getExpcorStatId());
				this.epayDraftInfoService.insertEpayExpCorHist(vo);
			}
		}	
		
		TisUtil.postURLforSlack(
				String.format("%s님이 법인 지출명세서을(를) 기안하였습니다.", user.getName())
				, (String)apprHistVO.get("emplyrId")
				);
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	// 지출명세서(법인) - 신규 -> 임시저장
	@RequestMapping(value="/com/epay/draft/epayExpCorWriteTemp.do")
	public String epayExpCorWriteTemp(
			final MultipartHttpServletRequest multiRequest
			, EpayExpCorVO epayExpCorVO
			, EpayExpCorHistVO epayExpCorHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		String expcorStatId = this.epayExpCorIdGnrService.getNextStringId();// 신규 지출명세서(법인) ID 발급
		String draftInfoId = this.epayDraftInfoIdGnrService.getNextStringId(); // 신규 기안서의 기안정보 ID 발급
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		epayDraftInfoVO.setDraftInfoId(draftInfoId);
		epayDraftInfoVO.setDocFormId("DOCFORM_000000000002");// 지출명세서(법인) 폼양식 ID
		
		epayDraftInfoVO.setDelAt("N");
		epayDraftInfoVO.setApprSttus("3"); // 결재상태 (3:임시저장)
		epayDraftInfoVO.setEmplNo(user.getEmplyrNo());// 기안자 ID
		//epayDraftInfoVO.setRegDt(today);
	
		// code
		String code = epayApprLineVO.getCode(); // 선택된 결재라인의 부서 코드(CODE) 
		epayDraftInfoVO.setCode(code);
		
		this.epayDraftInfoService.insertEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 등록
		
		// 지출명세서(법인)정보 DB 등록 
		epayExpCorVO.setDraftInfoId(draftInfoId);
		epayExpCorVO.setExpcorStatId(expcorStatId);
		this.epayDraftInfoService.insertEpayExpCor(epayExpCorVO, multiRequest);
		
		// 지출내역 항목 업데이트
		if(epayExpCorHistVO.getList() != null){
			
			EpayExpCorHistVO tmpExpCorHistVO = new EpayExpCorHistVO();
			tmpExpCorHistVO.setExpcorStatId(epayExpCorVO.getExpcorStatId());
			
			this.epayDraftInfoService.deleteEpayExpCorHist(epayExpCorHistVO);
			
			for (EpayExpCorHistVO vo: epayExpCorHistVO.getList()){
				
				if(vo.getTaskId() == null) continue;
				
				vo.setExpcorStatId(expcorStatId); // 지출명세서 ID 등록
				this.epayDraftInfoService.insertEpayExpCorHist(vo);
			}
		}
		
		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.insertEpayApprLnCfgTmp(draftInfoId, epayApprLnCfgVO);
		}
			
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}

	// 지출명세서(법인) - 임시저장 -> 임시저장
	@RequestMapping(value="/com/epay/draft/epayExpCorWriteTempUpdate.do")
	public String epayExpCorWriteTempUpdate(
			final MultipartHttpServletRequest multiRequest
			, EpayExpCorVO epayExpCorVO
			, EpayExpCorHistVO epayExpCorHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, EpayApprLineVO epayApprLineVO
			, EpayApprLnCfgVO epayApprLnCfgVO
			, ModelMap model
			, String method
			) throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
		}
		
		SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		Calendar cal = Calendar.getInstance();	
		String today = sDtFmt.format(cal.getTime());
		
		epayDraftInfoVO.setCode(epayApprLineVO.getCode()); // 선택된 결재라인의 부서 코드(CODE)
		
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO); // 기안정보 DB 업데이트
		
		// 지출명세서(법인)정보 DB 업데이트
		epayExpCorVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		this.epayDraftInfoService.updateEpayExpCor(epayExpCorVO, multiRequest);
		
		// 지출내역 항목에 지출명세서 ID 삽입 
		if(epayExpCorHistVO.getList() != null){
			
			EpayExpCorHistVO tmpExpCorHistVO = new EpayExpCorHistVO();
			tmpExpCorHistVO.setExpcorStatId(epayExpCorVO.getExpcorStatId());
			
			this.epayDraftInfoService.deleteEpayExpCorHist(epayExpCorHistVO);
			
			for (EpayExpCorHistVO vo: epayExpCorHistVO.getList()){
				
				if(vo.getTaskId() == null) continue;
				
				vo.setExpcorStatId(epayExpCorVO.getExpcorStatId());
				this.epayDraftInfoService.insertEpayExpCorHist(vo);
			}
		}
		
		// 결재라인이 지정 되어 있을 경우 업데이트
		if(epayApprLnCfgVO != null && epayApprLnCfgVO.getApprLnList() != null && epayApprLnCfgVO.getApprLnList().size() > 0){
			this.epayDraftInfoService.updateEpayApprLnCfgTmp(epayDraftInfoVO.getDraftInfoId(), epayApprLnCfgVO);
		}
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	@RequestMapping("/com/epay/draft/epayExpCorView.do")
	public String epayExpCorView(
			String draftInfoId
			, EpayExpCorVO epayExpCorVO
			, EpayApprHistVO epayApprHistVO
			, ModelMap model
			, String method
			) throws Exception{
		
		epayApprHistVO.setDraftInfoId(draftInfoId);
		
 		EgovMap expcor = this.epayDraftInfoService.selectEpayExpCorWithDInfo(epayExpCorVO); // 지출명세서(법인) 정보 조회
 		
 		EpayExpCorHistVO epayExpCorHistVO = new EpayExpCorHistVO();
 		epayExpCorHistVO.setExpcorStatId(expcor.get("expcorStatId").toString());
		List<?> epayExpCorHistList = this.epayDraftInfoService.selectEpayExpCorHistList(epayExpCorHistVO);
		
		List<EpayApprHistVO> epayApprHistList = this.epayDraftInfoService.selectEpayApprHistList(epayApprHistVO); // 결재이력라인 정보 조회
		
		model.addAttribute("expcor", expcor);
		model.addAttribute("apprHistList", epayApprHistList);
		model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); // 부서별 결재라인 목록 전달
		model.addAttribute("expCorHistList", epayExpCorHistList);
		
		return "/tis/com/epay/draft/epayExpCorView";// 기안서 상세보기
	}
	
	// 지출명세서(법인) 작성 취소 또는 문서 삭제
	@RequestMapping(value="/com/epay/draft/epayExpCorWriteCancel.do")
	public String epayExpCorWriteCancel(EpayExpCorVO epayExpCorVO, EpayDraftInfoVO epayDraftInfoVO, ModelMap model, String method) throws Exception{
		
		String epayDraftInfoId = epayDraftInfoVO.getDraftInfoId();
		
		if(epayDraftInfoId != null && epayDraftInfoId != ""){
			this.epayDraftInfoService.deleteEpayExpCor(epayExpCorVO);
			this.epayDraftInfoService.deleteEpayDraftInfo(epayDraftInfoVO);
			
			// 품의서에 소속된 결재라인 임시 저장 데이터 삭제(존재 할 경우)
			EpayApprLnCfgTmpVO epayApprLnCfgTmpVO = new EpayApprLnCfgTmpVO();
			epayApprLnCfgTmpVO.setDraftInfoId(epayDraftInfoId);
			this.epayDraftInfoService.deleteEpayApprLnCfgTmp(epayApprLnCfgTmpVO);
		}
		
		return "redirect:/com/epay/draft/epayDraftInfoList.do";
	}
	
	@RequestMapping("/com/epay/draft/epayExpCorForTaskPerson.do")
	public ModelAndView epayExpCorForTaskPerson(String emplNo) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		map.put("taskInfoForPerson", this.epayDraftInfoService.selectEpayTaskForPerson(emplNo));
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/draft/epayExpCorHistData.do")
	public ModelAndView epayExpCorHistData(String expcorStatId) throws Exception{
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(expcorStatId != null && expcorStatId != ""){
			EpayExpCorHistVO epayExpCorHistVO = new EpayExpCorHistVO();
			epayExpCorHistVO.setExpcorStatId(expcorStatId);
			map.put("epayExpCorHistList", this.epayDraftInfoService.selectEpayExpCorHistList(epayExpCorHistVO));
		}
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/draft/epayExpCorHistListExcelDown.do")
	public ModelAndView epayExpCorHistListExcelDown(/*List<EpayExpCorHistVO> lists, */ModelMap model) throws Exception{
	
		//List<EpayExpCorHistVO> lists = new ArrayList<EpayExpCorHistVO>();
		
		Map<String, Object> map = new HashMap<String, Object>();
		//map.put("category", lists);
		
		return new ModelAndView("epayExpCorHistView", "accessInfoList", map);
	}
	
	@RequestMapping("/com/epay/draft/epayExpCorImportExcel.do")
	public ModelAndView epayExpCorImportExcel(
			EpayExpCorHistVO epayExpCorHistVO
			, Model model
			, MultipartHttpServletRequest request
			) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		MultipartFile excelFile = request.getFile("excelUpload");
		
		String pathString = EgovProperties.getProperty("Globals.fileStorePath") + "excel/";
        File path = new File(pathString);
        
        if (!path.exists()) {
        	path.mkdir();
        }
        
        String pathname = pathString + "EpayExpCorHistExcelUpload.xls";
        File destFile = new File(pathname);
        
        excelFile.transferTo(destFile);
        
        String result = "failure";
        try {
        	List<EpayExpCorHistVO> epayExpCorHistVOList = this.epayDraftInfoService.uploadEpayExpCorHistExcel(destFile);
        	
        	map.put("epayExpCorHistList", epayExpCorHistVOList);
        	
        	result = "success";
        } catch(Exception e) {
        	
        }
        
        destFile.delete();
        
        model.addAttribute("result", result);
		
		return new ModelAndView("ajaxMainView", map);
	}
	
	@RequestMapping("/com/epay/draft/getWorkDayForepayHoliday.do")
	public ModelAndView getWorkDayForepayHoliday(
			EpayHolidayVO epayHolidayVO
			, Model model
			) throws Exception{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String fromDate = epayHolidayVO.getHolSdate();
		String toDate = epayHolidayVO.getHolEdate();
		
		if(fromDate.equals("") || toDate.equals("")) {
			map.put("message", "fail");
			return new ModelAndView("ajaxMainView", map);
		}
		
		List<String> holidayList = this.tisWorkDaysService.getHolidayList(fromDate, toDate);
		int workDay = this.tisWorkDaysService.countWorkDays(fromDate, toDate, holidayList);
		
		map.put("message", "success");
		map.put("workDay", workDay);
		
		return new ModelAndView("ajaxMainView", map);
	}
}
