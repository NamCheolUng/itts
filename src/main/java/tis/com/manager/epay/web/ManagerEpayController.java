package tis.com.manager.epay.web;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.TisUtil;
import tis.com.common.service.TisCodeService;
import tis.com.common.service.TisCodeVO;
import tis.com.epay.service.EpayApprHistVO;
import tis.com.epay.service.EpayApprLineVO;
import tis.com.epay.service.EpayCnsulVO;
import tis.com.epay.service.EpayDocFormVO;
import tis.com.epay.service.EpayDraftInfoSearchVO;
import tis.com.epay.service.EpayDraftInfoService;
import tis.com.epay.service.EpayDraftInfoVO;
import tis.com.epay.service.EpayExpCorHistVO;
import tis.com.epay.service.EpayExpCorVO;
import tis.com.epay.service.EpayExpHistVO;
import tis.com.epay.service.EpayExpVO;
import tis.com.epay.service.EpayHolidayVO;
import tis.com.epay.service.EpayTaskPersonVO;
import tis.com.financial.bankBook.service.TbBankbookmanageVO;
import tis.com.financial.bankBook.service.TbBankcardmanageService;
import tis.com.financial.bankBook.service.TbBankcardmanageVO;
import tis.com.financial.cost.service.CostService;
import tis.com.financial.cost.service.CostVO;
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
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class ManagerEpayController {

	@Resource(name = "epayDraftInfoService")
	private EpayDraftInfoService epayDraftInfoService;
	
	@Resource(name = "egovEpayDraftIdGnrService")
	private EgovIdGnrService epayDraftIdGnrService;
	
	@Resource(name = "egovEpayDraftInfoIdGnrService")
	private EgovIdGnrService epayDraftInfoIdGnrService;
	
	@Resource(name = "egovEpayApprHistIdGnrService")
	private EgovIdGnrService epayApprHistIdGnrService;
	
	@Resource(name = "egovEpayExpIdGnrService")
	private EgovIdGnrService epayExpIdGnrService;
	
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
	
	@Resource(name = "costService")
	private CostService costService;
	
	@Resource(name = "tbBankcardmanageService")
	private TbBankcardmanageService tbBankcardmanageService;	
	
	@RequestMapping("/com/manager/epay/managerEpayDraftInfoList.do")
	public String epayDraftInfoList(HttpServletRequest request,
			EpayDocFormVO epayDocFormVO,
			EpayDraftInfoVO epayDraftInfoVO,
			EpayDraftInfoSearchVO epayDraftInfoSearchVO,
			ModelMap model
			) throws Exception {
		
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
		
		model.addAttribute("docFormList", epayDraftInfoService.selectEpayDocFormList(epayDocFormVO));
		
		model.addAttribute("deptList", tisCodeService.selectCodeDetailList("COM101"));
		
		TisCodeVO apprSttusVO = new TisCodeVO();
		apprSttusVO.setCodeId("COM202");
		apprSttusVO.setCode("'0','1','2','4','5'"); // 승인(신규문서), 문서등록, 문서검토
		model.addAttribute("apprSttusList", tisCodeService.selectCodeDetailByVOList(apprSttusVO));
		
		String searchApprSttus = (epayDraftInfoVO.getSearchApprSttus() == null) ? "'0','1','2','4','5'" : epayDraftInfoVO.getSearchApprSttus();
		epayDraftInfoVO.setSearchApprSttus(searchApprSttus);
		
		// 기안정보 조회
		Map<String, Object> draftInfoList = (Map<String, Object>)epayDraftInfoService.selectManageEpayDraftInfoList(epayDraftInfoVO);
		int recordCount = (Integer)draftInfoList.get("resultCount");
		
		paginationInfo.setTotalRecordCount(recordCount);
		
		model.addAttribute("resultList", draftInfoList.get("resultList"));
		model.addAttribute("epayDraftInfoSearchVO", epayDraftInfoSearchVO);
		model.addAttribute("paginationInfo", paginationInfo);
		
		return "/tis/com/manager/epay/managerEpayDraftInfoList";
	}
	
	@RequestMapping("/com/manager/epay/managerEpayCnsulView.do")
	public String epayCnsulView(HttpServletRequest request,
			String draftInfoId,
			EpayDraftInfoVO epayDraftInfoVO,
			EpayCnsulVO epayCnsulVO,
			EpayApprHistVO epayApprHistVO,
			ModelMap model
			) throws Exception {
		
		// 문서상태를 '문서검토' 상태로 변경함.
		if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("1")){
			epayDraftInfoVO.setApprSttus("4");
			this.epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);
			
			EpayDraftInfoVO tmpDraftInfoVo = new EpayDraftInfoVO();
			tmpDraftInfoVo.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
			EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVo);
			
			// 기안자에게  문서 검토 상태 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 검토 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
		
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
		
		return "/tis/com/manager/epay/managerEpayCnsulView";// 품의서 상세보기
	}
	
	@RequestMapping("/com/manager/epay/managerFinancialRegister.do")
	public ModelAndView managerFinancialRegister(HttpServletRequest request, EpayDraftInfoVO epayDraftInfoVO, ModelMap model) throws Exception{
		
		
		model.addAttribute("result", "success");
		
		// 회계등록 완료 후 문서 상태를 '문서등록' 상태로 변경함.
		if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("4")){
			/*epayDraftInfoVO.setApprSttus("5");
			this.epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);*/
			
			epayDraftInfoService.registerToFinancial(epayDraftInfoVO);
			
			EpayDraftInfoVO tmpDraftInfoVo = new EpayDraftInfoVO();
			tmpDraftInfoVo.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
			EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVo);
			
			// 기안자에게  문서 등록 상태 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 등록 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
		
		return new ModelAndView("ajaxMainView");
	}

	
	// 지출명세서(개인)
	@RequestMapping("/com/manager/epay/managerEpayExpWrite.do")
	public String epayExpWrite(HttpServletRequest request,
			EpayExpVO epayExpVO,
			EpayExpHistVO epayExpHistVO,
			EpayDraftInfoVO epayDraftInfoVO,
			EpayApprHistVO epayApprHistVO,
			ModelMap model
			) throws Exception{
		String resultPage = "/tis/com/manager/epay/managerEpayExpWrite"; // 지출명세서 작성
			
		if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("1")){
			epayDraftInfoVO.setApprSttus("4");
			epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);
				
			EpayDraftInfoVO tmpDraftInfoVo = new EpayDraftInfoVO();
			tmpDraftInfoVo.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
			EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVo);
				
			// 기안자에게  결재 검토 상태 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 검토 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
			
		// 오늘 날짜 정보 취득
		//SimpleDateFormat sDtFmt = new SimpleDateFormat("yyyy/MM/dd", Locale.KOREA);
		//Calendar cal = Calendar.getInstance();	
		//String today = sDtFmt.format(cal.getTime());
		//	
		//String year = String.valueOf(cal.get(Calendar.YEAR)) + "년 ";
		//String month = String.valueOf(cal.get(Calendar.MONTH) + 1) + "월 "; 
			
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		// 임시저장상태에서 화면 보여줄때
		//map.put("epayExpHistList", this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO));
		//System.out.println(" - getDraftInfoId : " + epayDraftInfoVO.getDraftInfoId());
			
		// 1.기안정보 조회
		EpayDraftInfoVO epayDraftInfoResult = this.epayDraftInfoService.selectEpayDraftInfo(epayDraftInfoVO);
		// 2.지출명세서(개인) 조회 
		epayExpVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
		EpayExpVO epayExpResult = this.epayDraftInfoService.selectEpayExp(epayExpVO);
		// 3.지출명세서(개인)에 속한 지출내역 조회
		epayExpHistVO.setExpStatId(epayExpResult.getExpStatId());
		List<?> epayExpHistListResult = this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO);
			
		model.addAttribute("epayDraftInfoVO", epayDraftInfoResult);
		model.addAttribute("epayExpVO", epayExpResult);
		//model.addAttribute("epayExpHistVO", epayExpHistListResult);
		//model.addAttribute("epayExpHistVO", epayExpHistListResult);
		//String draftInfoId = epayDraftInfoVO.getDraftInfoId();		

		// 기초데이터 생성
		//epayDraftInfoVO.setOrgnztNm(user.getOrgnztNm()); // 기안자 소속부서
		//epayDraftInfoVO.setEmplNo(user.getEmplyrNo()); // 기안자 ID (사원번호)
		//epayDraftInfoVO.setUserNm(user.getName()); // 기안자명
			
		//String tmpTitle = String.format("%s %s %s 지출명세서", year, month, epayDraftInfoVO.getUserNm());
					
		//epayDraftInfoVO.setTitle(tmpTitle); // 기본제목 생성후 세팅     ex)17년12월 김철수 지출명세서
			
		model.addAttribute("expSubjList", this.tisCodeService.selectCodeDetailList("COM501")); // 계정과목 코드 조회
			
		//epayDraftInfoVO.setRegDt(today);     // 기안일자 초기값 세팅
		//epayExpHistVO.setSearchSdate(today); // 지출기간 검색 시작일
		//epayExpHistVO.setSearchEdate(today); // 지출기간 검색 종료일
			
		EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
		epayTaskPersonVO.setEmplNo(epayDraftInfoResult.getEmplNo());// 기안자가 속한 과제 목록 조회
		epayTaskPersonVO.setSttus("1"); // 과제 담당자 진행상태가 참여중(1) 인 상태
			
		model.addAttribute("taskList", this.epayDraftInfoService.selectTaskList(epayTaskPersonVO)); // 기안자가 과제 담당자로 속한 과제 목록 전달
		model.addAttribute("apprLineList", this.epayDraftInfoService.selectEpayApprLineList()); // 부서별 결재라인 목록 전달
		// 기초데이터 생성 종료	
			
		return resultPage;
	}
	
	// 지출명세서(개인) - 모바일내역 불러오기
	@RequestMapping("/com/manager/epay/managerEpayExpWriteLoadData.do")
	public ModelAndView managerEpayExpWriteLoadData(String expStatId) throws Exception{//EpayExpHistVO epayExpHistVO) throws Exception{
			
		EpayExpHistVO epayExpHistVO = new EpayExpHistVO();
		epayExpHistVO.setExpStatId(expStatId);
		
		Map<String,Object> map = new HashMap<String,Object>();		
			
		map.put("epayExpHistList", this.epayDraftInfoService.selectEpayExpHistList(epayExpHistVO));
			
		return new ModelAndView("ajaxMainView", map);
	}
		
	// 지출명세서 수정
	@RequestMapping(value="/com/manager/epay/managerEpayExpWriteTempUpdate.do")
	public String epayExpWriteTempUpdate(HttpServletRequest request,
			EpayExpVO epayExpVO,
			EpayExpHistVO epayExpHistVO,
			EpayDraftInfoVO epayDraftInfoVO,
			EpayApprLineVO epayApprLineVO,
			ModelMap model
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
		epayDraftInfoVO.setRegDt(today);
			
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
		
		return "redirect:/com/manager/epay/managerEpayDraftInfoList.do";
	}
		
		// 지출명세서(개인) - 회계등록
		@RequestMapping(value="/com/manager/epay/managerEpayAccountRegist.do")
		public String managerAccountRegist(HttpServletRequest request,
				@ModelAttribute("epayExpVO") EpayExpVO epayExpVO,
				EpayExpHistVO epayExpHistVO,
				EpayDraftInfoVO epayDraftInfoVO,
				ModelMap model
				) throws Exception{
			Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
			LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
			
			if (!isAuthenticated) {
				model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			}
			epayExpVO.setAcuntRegistYn("Y");
			epayDraftInfoService.updateManageExpAcuntRegistYn(epayExpVO);
			
			EpayTaskPersonVO epayTaskPersonVO = new EpayTaskPersonVO();
			epayTaskPersonVO.setEmplNo(epayExpVO.getEmplNo());// "I-1801");
			epayTaskPersonVO.setSttus("1"); // 과제 담당자 진행상태가 참여중(1) 인 상태
			
			List<EgovMap> list = (List<EgovMap>) epayDraftInfoService.selectTaskList(epayTaskPersonVO);
			
			List<CostVO> costVOList =  new ArrayList<CostVO>();
			for(int i=0;i<list.size();i++){
				CostVO costVO  =  new CostVO();
				EgovMap tmp = list.get(i);
				costVO.setTaskId(tmp.get("taskId").toString());
				costVOList.add(costVO);
			}
			boolean prdtNmFlag =  false;
			for(int i=0;i<costVOList.size();i++){
				BigDecimal sum = BigDecimal.ZERO;
				CostVO costVO = costVOList.get(i);
				prdtNmFlag =false;
				for(int j = 0;j<epayExpHistVO.getList().size();j++){
					
					EpayExpHistVO tmp  =  epayExpHistVO.getList().get(j);
					if(tmp != null){
						if(costVO.getTaskId().equals(tmp.getTaskId())){
							if(costVOList.get(i).getExpDate() == null){
								costVOList.get(i).setPayAt("N");
								costVOList.get(i).setExpDate(epayExpVO.getRegDt());
								costVOList.get(i).setDeptCode(epayDraftInfoVO.getCode());
								costVOList.get(i).setEmplNo(epayExpHistVO.getEmplNo());
								costVOList.get(i).setAffiliationId(epayExpHistVO.getEmplNo().substring(0, 1));
							}
							if(costVOList.get(i).getPrdtNm() == null){
								if(tmp.getExpSubj().equals("1")){
									costVOList.get(i).setPrdtNm("식대비");
								}else if(tmp.getExpSubj().equals("2")){
									costVOList.get(i).setPrdtNm("유류비");
								}else if(tmp.getExpSubj().equals("3")){
									costVOList.get(i).setPrdtNm("유류비(법인차)");
								}else if(tmp.getExpSubj().equals("4")){
									costVOList.get(i).setPrdtNm("통행비");
								}else if(tmp.getExpSubj().equals("5")){
									costVOList.get(i).setPrdtNm("주차비");
								}else if(tmp.getExpSubj().equals("6")){
									costVOList.get(i).setPrdtNm("배송비");
								}else if(tmp.getExpSubj().equals("7")){
									costVOList.get(i).setPrdtNm("접대비");
								}else if(tmp.getExpSubj().equals("8")){
									costVOList.get(i).setPrdtNm("기자재");
								}else{
									costVOList.get(i).setPrdtNm("");
								}
								
							}else{
								prdtNmFlag = true;
							}
							if(costVOList.get(i).getPrcsPlace() == null){
								costVOList.get(i).setPrcsPlace(tmp.getExpPlace());
							}
							sum = sum.add( new BigDecimal(tmp.getPrice()));
							
						}
					}
				}
				costVOList.get(i).setExpTotPrice(sum);
				if(prdtNmFlag){
					costVOList.get(i).setPrdtNm(costVOList.get(i).getPrdtNm()+" 외");
				}
				
			}
			costService.insertCost(costVOList);
			
			// 회계등록 완료 후 문서 상태를 '문서등록' 상태로 변경함.
			if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("4")){
				epayDraftInfoVO.setApprSttus("5");
				this.epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);
					
				EpayDraftInfoVO tmpDraftInfoVO = new EpayDraftInfoVO();
				tmpDraftInfoVO.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
				EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVO);
				
				// 기안자에게  결재 검토 상태 메세지 전송
				TisUtil.postURLforSlack(
						String.format("%s님의 %s이(가) 등록 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
						, (String)slackDraftInfoVO.get("emplyrId")
						);
			}
			
			return "redirect:/com/manager/epay/managerEpayExpWrite.do?draftInfoId="+epayExpVO.getDraftInfoId();
		}
					

	
	@RequestMapping("/com/manager/epay/managerEpaySalesUpdate.do")
	public String managerEpaySalesUpdate(ModelMap model,
			HttpServletRequest request, 
			EpayDraftInfoVO epayDraftInfoVO,
			EpayCnsulVO epayCnsulVO,
			String salesId,
			String apprSttus,
			String apprState) throws Exception {
		
		EgovMap salesMap = salesService.selectSales(salesId);
		model.addAttribute("salesVO", salesMap);
		
		List<EgovMap> salesDetailMap = (List<EgovMap>)salesService.selectSalesDetailList(salesId);
		model.addAttribute("salesDetailVO", salesDetailMap);
		
		model.addAttribute("tradeSeptList", tisCodeService.selectCodeDetailList("COM502"));
		model.addAttribute("payPointList", tisCodeService.selectCodeDetailList("COM505"));
		model.addAttribute("payWayList", tisCodeService.selectCodeDetailList("COM506"));
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		model.addAttribute("apprSttus", apprSttus);
		model.addAttribute("apprState", apprState);
		
		return "/tis/com/manager/epay/managerEpaySalesWrite";
	}
	
	@RequestMapping("/com/manager/epay/managerEpaySalesWriteUpdate.do")
	public String managerEpaySalesWriteUpdate(ModelMap model,
			HttpServletRequest request, 
			final MultipartHttpServletRequest multiRequest,
			String draftInfoId,
			SalesVO salesVO,
			SalesDetailVO salesDetailVO) throws Exception {
		
		salesService.updateDraftSales(salesVO, salesDetailVO.getList(), multiRequest);
		
		model.addAttribute("draftInfoId", draftInfoId);
		
		return "redirect:/com/manager/epay/managerEpayCnsulView.do";
	}
	
	@RequestMapping("/com/manager/epay/managerEpayPurchaseUpdate.do")
	public String managerEpayPurchaseUpdate(ModelMap model,
			HttpServletRequest request, 
			EpayDraftInfoVO epayDraftInfoVO,
			EpayCnsulVO epayCnsulVO,
			String purchaseId,
			String apprSttus,
			String apprState) throws Exception {
		
		EgovMap purchaseMap = purchaseService.selectPurchase(purchaseId);
		model.addAttribute("purchaseVO", purchaseMap);
		
		List<EgovMap> purchaseDetailMap = (List<EgovMap>)purchaseService.selectPurchaseDetailList(purchaseId);
		model.addAttribute("purchaseDetailVO", purchaseDetailMap);
		
		model.addAttribute("tradeSeptList", tisCodeService.selectCodeDetailList("COM502"));
		model.addAttribute("payPointList", tisCodeService.selectCodeDetailList("COM505"));
		model.addAttribute("payWayList", tisCodeService.selectCodeDetailList("COM506"));
		
		model.addAttribute("draftInfoId", epayDraftInfoVO.getDraftInfoId());
		
		return "/tis/com/manager/epay/managerEpayPurchaseWrite";
	}
	
	@RequestMapping("/com/manager/epay/managerEpayPurchaseWriteUpdate.do")
	public String managerEpayPurchaseWriteUpdate(ModelMap model,
			HttpServletRequest request, 
			final MultipartHttpServletRequest multiRequest,
			String draftInfoId,
			PurchaseVO purchaseVO,
			PurchaseDetailVO purchaseDetailVO) throws Exception {
		
		purchaseService.updateDraftPurchase(purchaseVO, purchaseDetailVO.getList(), multiRequest);
		
		model.addAttribute("draftInfoId", draftInfoId);
		
		return "redirect:/com/manager/epay/managerEpayCnsulView.do";
	}
	
	@RequestMapping("/com/manager/epay/managerEpayReceiptPrint.do")
	public String managerReceiptPrint(HttpServletRequest request
			                         ,@ModelAttribute("epayExpHistVO")EpayExpHistVO epayExpHistVO
			                         ,@RequestParam(value="printGbn",required=false) String printGbn
			                         ,@RequestParam(value="atchFileIdList",required=false) String atchFileIdList
			                         ,ModelMap model) throws Exception {
			
		model.addAttribute("atchFileIdList", atchFileIdList.split(","));
		model.addAttribute("printGbn", printGbn);
		return "tis/com/manager/epay/managerEpayReceiptPrint";
	}

	// 지출명세서(법인)
	@RequestMapping("/com/manager/epay/managerEpayExpCorWrite.do")
	public String managerEpayExpCorWrite(
			EpayExpCorVO epayExpCorVO
			, EpayExpCorHistVO epayExpCorHistVO
			, EpayDraftInfoVO epayDraftInfoVO
			, ModelMap model
			, HttpServletRequest request) 
			throws Exception{
		
		String resultPage = "/tis/com/manager/epay/managerEpayExpCorWrite"; // 지출명세서(법인) 작성
			
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
		
		if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("1")){
			epayDraftInfoVO.setApprSttus("4");
			this.epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);
			
			EpayDraftInfoVO tmpDraftInfoVo = new EpayDraftInfoVO();
			tmpDraftInfoVo.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
			EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVo);
			
			// 기안자에게  결재 검토 상태 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 검토 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
			
		String draftInfoId = epayDraftInfoVO.getDraftInfoId();
			
		if( draftInfoId != null & draftInfoId != ""){
			
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
		}                       
		
		// 계정과목 코드  목록 조회
		model.addAttribute("expcorSubjList", this.tisCodeService.selectCodeDetailList("COM501")); 
			
		// 카드 목록 조회
		TbBankcardmanageVO tbBankcardmanageVO = new TbBankcardmanageVO();
		tbBankcardmanageVO.setCpCode(null);
		
		model.addAttribute("tbBankcardmanageVOList", this.tbBankcardmanageService.selectTbBankcardmanageList(tbBankcardmanageVO));
		model.addAttribute("emplyrList", this.epayDraftInfoService.selectEmplyrList());
				
		model.addAttribute("taskList", this.epayDraftInfoService.selectEpayTaskAllList()); // 모든 과제 목록 조회 및 전달
		
		// 기초데이터 생성 종료	
			
		return resultPage;
	}
	
	// 지출명세서(법인) 회계등록
	@RequestMapping(value="/com/manager/epay/managerEpayExpCorAccountRegist.do")
	public ModelAndView managerEpayExpCorAccountRegist(
			EpayExpCorVO epayExpCorVO
			, EpayExpCorHistVO epayExpCorHistVO
			,EpayDraftInfoVO epayDraftInfoVO
			, ModelMap model
			, HttpServletRequest request) throws Exception{
		
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
							
		Map<String, CostVO> costVOList = new HashMap<String, CostVO>();   // 등록할 회계 목록 저장용 맵(키 값:과제ID)
		
		if(epayExpCorHistVO.getList() != null){
			for (EpayExpCorHistVO vo: epayExpCorHistVO.getList()){
				if(vo.getTaskId() != null){
					
					if(vo.getAcuntRegistType().equals("1")){ // 경비 일 경우
						
						if(costVOList.containsKey(vo.getTaskId())){ // 중복 과제 합산 처리
							
							BigDecimal prevExpTotPrice = costVOList.get(vo.getTaskId()).getExpTotPrice();
							BigDecimal currExpTotPrice = new BigDecimal(vo.getPrice());
							
							costVOList.get(vo.getTaskId()).setExpTotPrice(prevExpTotPrice.add(currExpTotPrice)); // 지출금액 합산
							
							String prevPrdtNm = costVOList.get(vo.getTaskId()).getPrdtNm();
							String currPrdtNm = vo.getSubjId();
							
							costVOList.get(vo.getTaskId()).setPrdtNm(prevPrdtNm + "," +currPrdtNm); // 상품/서비스명
							
						}else{
							
							CostVO costVO = new CostVO();
							costVO.setTaskId(vo.getTaskId()); // 과제 ID
							costVO.setDeptCode(epayDraftInfoVO.getOrgnztId()); // 주매입부 (기안자의 소속 부서 코드)
							costVO.setEmplNo(epayDraftInfoVO.getEmplNo()); // 담당자
							
							String prdtNm = this.tisCodeService.selectCodeNameOfCodeDetail("COM501", vo.getSubjId());
							
							costVO.setPrdtNm(prdtNm); // 상품/서비스명
							costVO.setPrcsPlace(vo.getExpPlace()); // 지출처
							costVO.setExpTotPrice(new BigDecimal(vo.getPrice())); // 지출금액(Total)
							costVO.setPayAt("1"); // 결재여부(1:미수)
							costVO.setExpDate(vo.getAccptDt()); // 사용일(지출일자)
							costVO.setAffiliationId(epayDraftInfoVO.getAffiliationId()); // 소속코드(I or S)
							
							costVOList.put(vo.getTaskId(), costVO);
						}
						
					}else if(vo.getAcuntRegistType().equals("2")){ // 매입 일 경우
						// 매입 등록
						PurchaseVO purchaseVO = new PurchaseVO();
						purchaseVO.setTaskId(vo.getTaskId());       // 과제 ID
						purchaseVO.setOrderDt(vo.getAccptDt());     // 발주일
						purchaseVO.setPrcsPlace(vo.getExpPlace());  // 매출처(직접입력)
						purchaseVO.setPayPlanDate(vo.getAccptDt()); // 결제예정일
						purchaseVO.setPrdtNm(this.tisCodeService.selectCodeNameOfCodeDetail("COM501", vo.getSubjId())); // 상품 및 서비스명						
						
						purchaseVO.setAffiliationId(epayDraftInfoVO.getAffiliationId()); // 소속코드(I or S)
						purchaseVO.setPayWay("2"); // 1:현금, 2:신용카드
						
						BigDecimal prcsTotPrice = new BigDecimal(vo.getPrice().replaceAll(",",""));
						BigDecimal prcsPrice = prcsTotPrice.divide(new BigDecimal("1.1"), BigDecimal.ROUND_UP);
						BigDecimal prcsSurtax = prcsTotPrice.subtract(prcsPrice);
						
						// 카드 대금은 계획금액과 집행금액 항목이 동일함.
						purchaseVO.setPrcsPlanSurtax(prcsSurtax.toString());     // 계획금액-부가세
						purchaseVO.setPrcsPlanPrice(prcsPrice.toString());       // 계획금액-매출금액
						purchaseVO.setPrcsPlanTotPrice(prcsTotPrice.toString()); // 계획금액-전체
						
						purchaseVO.setPrcsExecSurtax(prcsSurtax.toString());     // 집행금액-부가세 
						purchaseVO.setPrcsExecPrice(prcsPrice.toString());       // 집행금액-매출금액
						purchaseVO.setPrcsExecTotPrice(prcsTotPrice.toString()); // 집행금액-전체
						purchaseVO.setPayApprAt("Y"); // 회계등록 여부
						
						this.purchaseService.insertPurchase(purchaseVO, null, null);
					}// end else					
				}
			}// end for
		}
		
		// 경비 등록
		if(costVOList.size() > 0){
			
			List<CostVO> tmpCostVOList = new ArrayList<CostVO>(costVOList.values());
			
			for (CostVO vo: tmpCostVOList){
				String[] prdtNmArray = vo.getPrdtNm().split(",");
				if(prdtNmArray.length > 1){
					costVOList.get(vo.getTaskId()).setPrdtNm(prdtNmArray[0] + " 외");
				}
			}
			
			this.costService.insertCost(new ArrayList<CostVO>(costVOList.values()));
		}	
		
		// 회계 등록 완료 시 기안 정보 및 지출내역 변경 내용 업데이트
		this.epayDraftInfoService.updateEpayDraftInfo(epayDraftInfoVO);
		
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
		 
		// 회계등록 완료 후 문서 상태를 '문서등록' 상태로 변경함.
		if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("4")){
			epayDraftInfoVO.setApprSttus("5");
			this.epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);
			
			EpayDraftInfoVO tmpDraftInfoVo = new EpayDraftInfoVO();
			tmpDraftInfoVo.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
			EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVo);
			
			// 기안자에게  문서 등록 상태 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 등록 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
		
		epayExpCorVO.setAcuntRegistYn("Y"); // 회계등록 여부
		this.epayDraftInfoService.updateManageExpCorAcuntRegistYn(epayExpCorVO); // 회계등록 여부 업데이트
		
		model.addAttribute("result", "success");
		
		return new ModelAndView("ajaxMainView");
	}
	
	// 휴가원                   
	@RequestMapping("/com/manager/epay/managerEpayHolidayView.do")
	public String managerEpayHolidayView(
			EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, ModelMap model
			, HttpServletRequest request) 
			throws Exception{			
		
		EgovMap holiday = this.epayDraftInfoService.selectEpayHolidayWithDInfo(epayHolidayVO); // 휴가원 정보 조회
		
		if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("1")){
			epayDraftInfoVO.setApprSttus("4");
			this.epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);
			
			EpayDraftInfoVO tmpDraftInfoVo = new EpayDraftInfoVO();
			tmpDraftInfoVo.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
			EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVo);
			
			// 기안자에게  문서 검토 상태 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 검토 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
		
		model.addAttribute("holiday", holiday);
				
		return "/tis/com/manager/epay/managerEpayHolidayView";
	}
	
	// 휴가원 연차등록
	@RequestMapping(value="/com/manager/epay/managerEpayHolidayRegist.do")
	public ModelAndView managerEpayHolidayRegist(
			EpayHolidayVO epayHolidayVO
			, EpayDraftInfoVO epayDraftInfoVO
			, ModelMap model
			) throws Exception{
			
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
								
		this.epayDraftInfoService.insertEpayManagerHoliday(epayHolidayVO);
		
		// 연차등록 완료 후 문서 상태를 '문서등록' 상태로 변경함.
		if(epayDraftInfoVO.getApprSttus() != null && epayDraftInfoVO.getApprSttus().equals("4")){
			epayDraftInfoVO.setApprSttus("5");
			this.epayDraftInfoService.updateManageEpayApprSttus(epayDraftInfoVO);
			
			EpayDraftInfoVO tmpDraftInfoVo = new EpayDraftInfoVO();
			tmpDraftInfoVo.setDraftInfoId(epayDraftInfoVO.getDraftInfoId());
			EgovMap slackDraftInfoVO = this.epayDraftInfoService.selectEpayDraftInfoForSlack(tmpDraftInfoVo);
			
			// 기안자에게  문서 등록 상태 메세지 전송
			TisUtil.postURLforSlack(
					String.format("%s님의 %s이(가) 등록 되었습니다.", (String)slackDraftInfoVO.get("userNm"), (String)slackDraftInfoVO.get("docformNm"))
					, (String)slackDraftInfoVO.get("emplyrId")
					);
		}
			
		model.addAttribute("result", "success");
			
		return new ModelAndView("ajaxMainView");
	}
}
