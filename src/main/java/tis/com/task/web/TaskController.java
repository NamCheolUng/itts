package tis.com.task.web;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.service.TisCodeService;
import tis.com.common.service.TisWorkHoursService;
import tis.com.financial.cost.service.CostSearchVO;
import tis.com.financial.cost.service.CostService;
import tis.com.financial.profitloss.service.ProfitLossSearchVO;
import tis.com.financial.profitloss.service.ProfitLossService;
import tis.com.financial.purchase.service.PurchaseSearchVO;
import tis.com.financial.purchase.service.PurchaseService;
import tis.com.financial.sales.service.SalesSearchVO;
import tis.com.financial.sales.service.SalesService;
import tis.com.task.service.TaskEnvVO;
import tis.com.task.service.TaskOrgztPerVO;
import tis.com.task.service.TaskService;
import tis.com.task.service.TaskVO;
import egovframework.com.cmm.ComDefaultVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.EgovFileMngUtil;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.smt.dsm.service.DiaryManageVO;
import egovframework.com.cop.smt.dsm.service.EgovDiaryManageService;
import egovframework.com.cop.smt.sim.service.EgovIndvdlSchdulManageService;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class TaskController {

	@Resource(name="taskService")
	private TaskService taskService;
	
	@Resource(name = "egovIndvdlSchdulManageService")
	private EgovIndvdlSchdulManageService egovIndvdlSchdulManageService;
	
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@Resource(name="TisCodeService")
	private TisCodeService tisCodeService;
	
	@Resource(name = "EgovFileMngUtil")
	private EgovFileMngUtil fileUtil;
	
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileMngService;
    
	@Resource(name = "egovDiaryManageService")
	private EgovDiaryManageService egovDiaryManageService;
	
    @Resource(name = "EgovFileMngService")
    private EgovFileMngService fileService;
	
    @Resource(name="TisWorkHoursService")
    private TisWorkHoursService tisWorkHoursService;
    
	@Resource(name = "profitLossService")
	private ProfitLossService profitLossService;
	
	@Resource(name = "salesService")
	private SalesService salesService;
		
	@Resource(name = "purchaseService")
	private PurchaseService purchaseService;
	
	@Resource(name = "costService")
	private CostService costService;
	
	@Autowired
	private JavaMailSender EMSMailSender;

    
	@RequestMapping("/com/task/taskList.do")
	public String taskList(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}

		/** paging */
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(taskVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(taskVO.getPageUnit());
		paginationInfo.setPageSize(taskVO.getPageSize());
		
		taskVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		taskVO.setLastIndex(paginationInfo.getLastRecordIndex());
		taskVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());
		String tmp = "";
		if(taskVO.getDeptCode() != null && !taskVO.getDeptCode().isEmpty()){
			String []arr  = taskVO.getDeptCode().split(",");
		    tmp = "";
			if(arr.length > 1){
				for(int i=0;i<arr.length;i++){
					if(arr.length -1 == i){
						tmp = tmp +"'" +arr[i] +"'" ;
					}else{
						tmp = tmp +"'" +arr[i] +"'," ;
					}
					
				}
				taskVO.setDeptCode(tmp);
				
			}else{
				taskVO.setDeptCode("'"+taskVO.getDeptCode()+"'");
			}
		}else{
			if(taskVO.getMyTaskChk()!= null && !taskVO.getMyTaskChk().isEmpty()){
				taskVO.setDeptCode("");
			}else{
				if(taskVO.getEmptyChk() != null && !taskVO.getEmptyChk().isEmpty() && taskVO.getEmptyChk().indexOf("dept") != -1){
					taskVO.setDeptCode("'nodata'");
				}else{
					tmp = "";
					List<EgovMap> mapList = (List<EgovMap>) tisCodeService.selectCodeDetailList("COM101");
					for(int i=0; i<mapList.size();i++){
						EgovMap map = mapList.get(i);
						if(mapList.size()-1 == i){
							tmp = tmp +"'" +map.get("code").toString()+"'" ;	
						}else{
							tmp = tmp +"'" +map.get("code").toString()+"'," ;	
						}
						 
					}	
					taskVO.setDeptCode(tmp);
				}
				
			}
			
		}
		
		if(taskVO.getTaskStatusCd() != null && !taskVO.getTaskStatusCd().isEmpty()){
			String []arr  = taskVO.getTaskStatusCd().split(",");
			tmp = "";
			if(arr.length > 1){
				for(int i=0;i<arr.length;i++){
					if(arr.length -1 == i){
						tmp = tmp +"'" +arr[i] +"'" ;
					}else{
						tmp = tmp +"'" +arr[i] +"'," ;
					}
					
				}
				taskVO.setTaskStatusCd(tmp);
				
			}else{
				taskVO.setTaskStatusCd("'"+taskVO.getTaskStatusCd()+"'");
			}
		}else{
			if(taskVO.getEmptyChk() != null && !taskVO.getEmptyChk().isEmpty() && taskVO.getEmptyChk().indexOf("status") != -1){
				taskVO.setTaskStatusCd("'nodata'");
			}else{
				tmp = "";
				List<EgovMap> mapList = (List<EgovMap>) tisCodeService.selectCodeDetailList("COM302");
				for(int i=0; i<mapList.size();i++){
					EgovMap map = mapList.get(i);
					if(mapList.size()-1 == i){
						tmp = tmp +"'" +map.get("code").toString()+"'" ;	
					}else{
						tmp = tmp +"'" +map.get("code").toString()+"'," ;	
					}
					 
				}
				taskVO.setTaskStatusCd(tmp);
			}
			
		}
		taskVO.setEmplNo(user.getEmplyrNo());
		int totCnt = taskService.selectTaskListCnt(taskVO);
		paginationInfo.setTotalRecordCount(totCnt);
	
		model.addAttribute("resultList", taskService.selectTaskList(taskVO));		
		model.addAttribute("paginationInfo", paginationInfo);
		return "tis/com/task/taskList";
	}
	
	@RequestMapping("/com/task/taskRegist.do")
	public String taskRegist(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		model.addAttribute("deptCodeList", tisCodeService.selectCodeDetailList("COM101"));
		
		return "tis/com/task/taskRegist";
	}
	
	@RequestMapping("/com/task/taskModify.do")
	public String taskModify(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}

		model.addAttribute("result", taskService.selectTaskDetail(taskVO));
		model.addAttribute("relaResult", taskService.selectRelatedTaskList(taskVO));
		model.addAttribute("deptCodeList", tisCodeService.selectCodeDetailList("COM101"));
		return "tis/com/task/taskModify";
	}
	
	@RequestMapping("/com/task/taskInsert.do")
	public String taskInsert(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		taskVO.setEmplNo(user.getEmplyrNo());
		taskVO.setDeptCode(user.getOrgnztId());
		taskService.insertTask(taskVO);
		
		return "redirect:/com/task/taskList.do";
	}
	
	@RequestMapping("/com/task/taskUpdate.do")
	public String taskUpdate(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		taskService.updateTask(taskVO);
		
		return "redirect:/com/task/taskList.do";
	}
	
	@RequestMapping("/com/task/taskNmCompare.do")
	public ModelAndView taskNmCompare(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}		
		Map<String,Object> map = new HashMap<String,Object>();		
		map.put("cnt", taskService.taskNmCompare(taskVO));
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/taskDetail.do")
	public String taskDetail(@ModelAttribute("taskVO") TaskVO taskVO,@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH:mm", Locale.KOREA );
		String nowDate = mSimpleDateFormat.format(new Date());
		int time = 0;
		List<EgovMap> delayList  =(List<EgovMap>) taskService.selectDelaySchdulList(taskVO);
		for(int i=0;i<delayList.size();i++){
			EgovMap map = delayList.get(i);
			IndvdlSchdulManageVO vo = new IndvdlSchdulManageVO();
			vo.setSchdulId(map.get("schdulId").toString());
			if("1".equals(map.get("schdulPrgrsSttus").toString()) || "2".equals(map.get("schdulPrgrsSttus").toString())){
				time = tisWorkHoursService.countWorkHours(map.get("schdulEdt").toString(), nowDate);				
				vo.setSchdulEarlydelayTime(time);
				vo.setSchdulPrgrsSttus("4");
			}
			if("4".equals(map.get("schdulPrgrsSttus").toString())){
				time = tisWorkHoursService.countWorkHours(map.get("schdulEdt").toString(), nowDate);				
				vo.setSchdulEarlydelayTime(time);
			}
			taskService.updateSchdulPrgrSttusAndTime(vo);
		}
		TaskEnvVO taskEnvVO = new TaskEnvVO();
		taskEnvVO.setTaskId(taskVO.getTaskId());
		EgovMap result = taskService.selectTaskDetail(taskVO);
		model.addAttribute("result", result);
		model.addAttribute("relaResult", taskService.selectRelatedTaskList(taskVO));
		model.addAttribute("envM", taskService.selectTaskEnv(taskEnvVO));
		model.addAttribute("envS", taskService.selectTaskSubEnvList(taskEnvVO));
		
		List<EgovMap> mapList  =  (List<EgovMap>) taskService.selectTaskSchduleList(taskVO);
		double sum = 0.0;
		int cnt = 0;
		for(int i=0;i<mapList.size();i++){
			EgovMap tmp = mapList.get(i);
			if("0".equals(tmp.get("ptaskStepId").toString())){
				sum=sum+  Double.parseDouble(tmp.get("percent").toString());
				cnt++;
			}
			
		}
		sum = sum/cnt;
		if(cnt == 0){
			model.addAttribute("totPercent",0 );
		}else{
			model.addAttribute("totPercent",sum );
		}
		
		model.addAttribute("taskStepList",mapList );
		
		model.addAttribute("taskPersonList", taskService.selectTaskPersonList(taskVO));
		TaskOrgztPerVO taskOrgztPerVO = new TaskOrgztPerVO();
		taskOrgztPerVO.setTaskId(taskVO.getTaskId());
		model.addAttribute("opList", taskService.selectTbOrgztPersonList(taskOrgztPerVO));
		model.addAttribute("diary", egovDiaryManageService.selectDiarySection2or3(diaryManageVO));
		model.addAttribute("particiEmplNo",taskService.selectParticiEmplNo(taskVO));
		
		return "tis/com/task/taskDetail";
	}
	
	@RequestMapping("/com/task/taskScheduleRegist.do")
	public String taskScheduleRegist(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		model.addAttribute("codeList", tisCodeService.selectCodeDetailList("COM304"));
		model.addAttribute("taskStep", taskService.selectTaskStep(taskVO));
		return "tis/com/task/taskScheduleRegist";
	}
	
	@RequestMapping("/com/task/taskScheduleInsert.do")
	public String taskScheduleInsert(@ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		indvdlSchdulManageVO.setFrstRegisterId(user.getEmplyrNo());
		int actual = tisWorkHoursService.countWorkHours(indvdlSchdulManageVO.getSchdulSdt(), indvdlSchdulManageVO.getSchdulEdt());
		indvdlSchdulManageVO.setSchdulTime(actual);
		egovIndvdlSchdulManageService.insertIndvdlSchdulManage(indvdlSchdulManageVO);
		if(indvdlSchdulManageVO.getSchdulIpcrCode() != null && "Y".equals(indvdlSchdulManageVO.getSchdulIpcrCode())){
			TaskVO taskVO = new TaskVO();
			taskVO.setTaskId(indvdlSchdulManageVO.getTaskId());
			List<EgovMap> perList =(List<EgovMap>) taskService.selectTaskPersonList(taskVO);
			String userNm = "";
			for(int i=0;i<perList.size();i++){
				EgovMap temp = perList.get(i);
				if(indvdlSchdulManageVO.getFrstRegisterId().equals(temp.get("emplNo").toString())){
					userNm = temp.get("userNm").toString();
				}
			}			
			for(int i=0;i<perList.size();i++){
				EgovMap tmp = perList.get(i);
				String mailAdres = tmp.get("emailAdres").toString();
				try{
					SimpleMailMessage sendemail = new SimpleMailMessage();
				  	//발신계정은 임의로 입력해도 됨. 
					sendemail.setTo(mailAdres);
					sendemail.setFrom("ieetumaster@gmail.com");
					sendemail.setSubject(userNm+" "+indvdlSchdulManageVO.getSchdulNm()+"중요일정 등록");
					sendemail.setText(indvdlSchdulManageVO.getSchdulNm()+" "
								 	+	indvdlSchdulManageVO.getSchdulSdt()+"~"+indvdlSchdulManageVO.getSchdulEdt()
							);
					//test mail
					//sendemail.setFrom("");
					EMSMailSender.send(sendemail);
				}catch(Exception e){
					
				}
			}
		}
		
		
		return "redirect:/com/task/taskDetail.do?taskId="+indvdlSchdulManageVO.getTaskId()/*+"&taskStepId="+indvdlSchdulManageVO.getTaskStepId()*/;
	}
	
	@RequestMapping("/com/task/taskScheduleModify.do")
	public String taskScheduleModify(@ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}			
		model.addAttribute("codeList", tisCodeService.selectCodeDetailList("COM304"));
		TaskVO taskVO =  new TaskVO();
		taskVO.setTaskId(indvdlSchdulManageVO.getTaskId());
		model.addAttribute("taskStep", taskService.selectTaskStep(taskVO));
		model.addAttribute("result", egovIndvdlSchdulManageService.selectIndvdlSchdulManageDetailEgovMap(indvdlSchdulManageVO));
		ComDefaultVO comDefaultVO = new ComDefaultVO();
		comDefaultVO.setSearchKeyword(indvdlSchdulManageVO.getSchdulId());
		comDefaultVO.setRecordCountPerPage(9999999);
		comDefaultVO.setFirstIndex(0);
		model.addAttribute("diary", egovDiaryManageService.selectDiaryManageList(comDefaultVO));
		model.addAttribute("relaCmng", egovIndvdlSchdulManageService.selectRelatedCmng(indvdlSchdulManageVO));
		
		return "tis/com/task/taskScheduleModify";
	}
	
	@RequestMapping("/com/task/taskScheduleUpdate.do")
	public String taskScheduleUpdate(@ModelAttribute("indvdlSchdulManageVO") IndvdlSchdulManageVO indvdlSchdulManageVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		IndvdlSchdulManageVO vo = new IndvdlSchdulManageVO();
		vo.setSchdulId(indvdlSchdulManageVO.getSchdulId());
		SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy/MM/dd HH:mm", Locale.KOREA );
		Date compareDate = mSimpleDateFormat.parse(indvdlSchdulManageVO.getSchdulEdt());
		int compare =0;
		if(indvdlSchdulManageVO.getSchdulFinDt() != null){
			Date compareDate_f = mSimpleDateFormat.parse(indvdlSchdulManageVO.getSchdulFinDt());
			compare = compareDate_f.compareTo(compareDate);
		}	
		int time = 0;
		if("3".equals(indvdlSchdulManageVO.getSchdulPrgrsSttus()) || "4".equals(indvdlSchdulManageVO.getSchdulPrgrsSttus()) || "5".equals(indvdlSchdulManageVO.getSchdulPrgrsSttus())){			
			if(compare > 0 ){
				time = tisWorkHoursService.countWorkHours(indvdlSchdulManageVO.getSchdulEdt(), indvdlSchdulManageVO.getSchdulFinDt());
			}else if(compare < 0){
				time = tisWorkHoursService.countWorkHours(indvdlSchdulManageVO.getSchdulFinDt(), indvdlSchdulManageVO.getSchdulEdt())*-1;
			}else{
				time = 0;
			}
			vo.setSchdulEarlydelayTime(time);
			
		}		
		if("6".equals(indvdlSchdulManageVO.getSchdulPrgrsSttus())){			
			vo.setSchdulEarlydelayTime(time);
		}
		taskService.updateSchdulPrgrSttusAndTime(vo);
		indvdlSchdulManageVO.setLastUpdusrId(user.getEmplyrNo());
		egovIndvdlSchdulManageService.updateIndvdlSchdulManage(indvdlSchdulManageVO);
		return "redirect:/com/task/taskDetail.do?taskId="+indvdlSchdulManageVO.getTaskId();
	}
	
	@RequestMapping("/com/task/taskScheduleDiaryInsert.do")
	public ModelAndView taskScheduleDiaryInsert(@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO,final MultipartHttpServletRequest multiRequest, Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}
		diaryManageVO.setFrstRegisterId(user.getEmplyrNo());
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			List<FileVO> result = fileUtil.parseFileInf(files, "SCHDULDIARY_", 0, "", "Globals.fileStorePathSchdulDiary");
			String atchFileId = fileMngService.insertFileInfs(result);
			diaryManageVO.setAtchFileId(atchFileId);			
		}
		egovDiaryManageService.insertDiaryManage(diaryManageVO);
		Map<String,Object> map  = new  HashMap<String,Object>();
		map.put("success","ok");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/taskScheduleDiaryUpdate.do")
	public ModelAndView taskScheduleDiaryUpdate(@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO,final MultipartHttpServletRequest multiRequest,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}
		String atchFileId = "";
		if(diaryManageVO.getAtchFileId() != null && !diaryManageVO.getAtchFileId().isEmpty()){
			atchFileId = diaryManageVO.getAtchFileId();
		}
		
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		if (!files.isEmpty()) {
			if("".equals(atchFileId)){
				List<FileVO> result = fileUtil.parseFileInf(files, "SCHDULDIARY_", 0, "", "Globals.fileStorePathSchdulDiary");
				atchFileId = fileMngService.insertFileInfs(result);
				diaryManageVO.setAtchFileId(atchFileId);
			}else{
				FileVO fvo = new FileVO();
				fvo.setAtchFileId(atchFileId);
				int cnt = fileMngService.getMaxFileSN(fvo);
				List<FileVO> result = fileUtil.parseFileInf(files, "SCHDULDIARY_", cnt,atchFileId,"Globals.fileStorePathSchdulDiary");
				if(cnt == 0 && result.size()== 0){
					diaryManageVO.setAtchFileId(null);
				}
				fileMngService.updateFileInfs(result);
			}
			
		}
		egovDiaryManageService.updateDiaryManage(diaryManageVO);
		Map<String,Object> map  = new  HashMap<String,Object>();
		map.put("success","ok");
		return new ModelAndView("ajaxMainView",map);
	}
	
	
	@RequestMapping("/com/task/taskScheduleDiaryList.do")
	public ModelAndView taskScheduleDiaryList(@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO, Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}
		ComDefaultVO comDefaultVO = new ComDefaultVO();
		comDefaultVO.setRecordCountPerPage(9999999);
		comDefaultVO.setFirstIndex(0);
		comDefaultVO.setSearchKeyword(diaryManageVO.getSchdulId());
		Map<String,Object> map  = new  HashMap<String,Object>();
		map.put("result", egovDiaryManageService.selectDiaryManageList(comDefaultVO));		
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/taskScheduleDiaryDelete.do")
	public ModelAndView taskScheduleDiaryDelete(@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO, Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}

		Map<String,Object> map  = new  HashMap<String,Object>();
		String [] diaryId = diaryManageVO.getDiaryId().split(",");
		for(int i = 0 ;i<diaryId.length;i++){
			if(diaryId[i].isEmpty()){
				break;
			}
			diaryManageVO.setDiaryId(diaryId[i]);
			egovDiaryManageService.deleteDiaryManage(diaryManageVO);
		}
		
		map.put("success", "ok");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/taskScheduleDiarySelectFile.do")
	public ModelAndView taskScheduleDiarySelectFile(@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO, Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}
		FileVO fileVO = new FileVO();
		fileVO.setAtchFileId(diaryManageVO.getAtchFileId());

		List<FileVO> result = fileService.selectFileInfs(fileVO);
		
		Map<String,Object> map  = new  HashMap<String,Object>();
		map.put("result", result);
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/taskScheduleDiaryDeleteFile.do")
	public ModelAndView taskScheduleDiaryDeleteFile(@ModelAttribute("fileVO") FileVO fileVO, Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}		
		Map<String,Object> map  = new  HashMap<String,Object>();
		 fileService.deleteFileInf(fileVO);
		 map.put("success", "ok");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/taskProfitLoss.do")
	public String taskProfitLoss(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		EgovMap map = taskService.selectTaskDetail(taskVO);
		model.addAttribute("taskDetail", map);

		// 인건비 계산
		ProfitLossSearchVO profitLossVO = new ProfitLossSearchVO();
		profitLossVO.setSearchTaskId(taskVO.getTaskId());
		profitLossVO.setRecordCountPerPage(-1);
		List<EgovMap> taskProfitLossList = (List<EgovMap>)profitLossService.selectProfitLossList(profitLossVO).get("resultList");
		EgovMap taskProfitLossMap = (EgovMap)taskProfitLossList.get(0);
		
		String taskSdate = ((String)map.get("taskSdate"));
		String taskEdate = ((String)map.get("taskEdate"));
		int taskLaborCost = profitLossService.calculateLaborCost(taskVO.getTaskId(), taskSdate, taskEdate);
		taskProfitLossMap.put("laborExecPrice", taskLaborCost);
		
		// 인건비를 반영한 영업이익 다시 계산
		long businessProfit = ((BigDecimal)taskProfitLossMap.get("businessProfit")).longValue();
		taskProfitLossMap.put("businessProfit", businessProfit - taskLaborCost);
		
		model.addAttribute("profitLossList", taskProfitLossList);
		
		// 매출내역
		SalesSearchVO searchVO = new SalesSearchVO();
		searchVO.setSearchTaskId(taskVO.getTaskId());
		model.addAttribute("salesList", salesService.selectSalesManageList(searchVO).get("resultList"));
		
		// 매입내역
		PurchaseSearchVO purchaseVO = new PurchaseSearchVO();
		purchaseVO.setSearchTaskId(taskVO.getTaskId());
		model.addAttribute("purchaseList", purchaseService.selectPurchaseManageList(purchaseVO).get("resultList"));
		
		// 경비내역
		CostSearchVO costVO = new CostSearchVO();
		costVO.setSearchTaskId(taskVO.getTaskId());
		model.addAttribute("costList", costService.selectCostManageList(costVO).get("resultList"));
		return "tis/com/task/taskProfitLoss";
	}
		
	@RequestMapping("/com/task/taskReport.do")
	public String taskReport(@ModelAttribute("taskVO") TaskVO taskVO,Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "tis/com/inc/login";
		}
		
		return "tis/com/task/taskReport";
	}
	
	@RequestMapping("/com/task/taskSchduleDiary.do")
	public ModelAndView taskSchduleDiary(@ModelAttribute("diaryManageVO") DiaryManageVO diaryManageVO, Model model) throws Exception{
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "tis/com/inc/login";
		}		
		Map<String,Object> map  = new  HashMap<String,Object>();
	
		 map.put("result",egovDiaryManageService.selectDiarySection2or3(diaryManageVO));
		return new ModelAndView("ajaxMainView",map);
	}
}
