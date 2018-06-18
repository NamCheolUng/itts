package tis.com.task.web;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import tis.com.common.service.TisCodeService;
import tis.com.task.service.TaskEnvVO;
import tis.com.task.service.TaskOrgztPerVO;
import tis.com.task.service.TaskPersonVO;
import tis.com.task.service.TaskService;
import tis.com.task.service.TaskStepVO;
import tis.com.task.service.TaskVO;
import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.cop.smt.sim.service.IndvdlSchdulManageVO;

@Controller
public class TaskPopupController {
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;

	@Resource(name="taskService")
	private TaskService taskService;
	
	@Resource(name="TisCodeService")
	private TisCodeService tisCodeService;
	
	@RequestMapping("/com/task/popup/companyListPopup.do")
	public String companyListPopup(@ModelAttribute("taskVO") TaskVO taskVO,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		model.addAttribute("resultList", taskService.selectCompanyList(taskVO));
		
		return "tis/com/task/popup/companyListPopup";
	}
	
	@RequestMapping("/com/task/popup/taskListPopup.do")
	public String taskListPopup(@ModelAttribute("taskVO") TaskVO taskVO, Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		taskVO.setFirstIndex(0);
		taskVO.setRecordCountPerPage(9999999);
		model.addAttribute("resultList", taskService.selectTaskList(taskVO));
		
		return "tis/com/task/popup/taskListPopup";
	}
	
	@RequestMapping("/com/task/popup/taskMngerPopup.do")
	public String taskMngerPopup(@ModelAttribute("taskVO") TaskVO taskVO,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		
		model.addAttribute("userList", taskService.selectUserList_task(taskVO));		
		model.addAttribute("taskPersonList", taskService.selectTaskPersonList(taskVO));
		model.addAttribute("deptCodeList", tisCodeService.selectCodeDetailList("COM101"));
		return "tis/com/task/popup/taskMngerPopup";
	}
	
	@RequestMapping("/com/task/popup/taskDevelEnvirPopup.do")
	public String taskDevelEnvirPopup(@ModelAttribute("taskEnvVO") TaskEnvVO taskEnvVO ,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		model.addAttribute("result", taskService.selectTaskEnv(taskEnvVO));
		return "tis/com/task/popup/taskDevelEnvirPopup";
	}
	
	@RequestMapping("/com/task/popup/taskDevelEnvirSubPopup.do")
	public String taskDevelEnvirSubPopup(@ModelAttribute("taskEnvVO") TaskEnvVO taskEnvVO ,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}		
		return "tis/com/task/popup/taskDevelEnvirSubPopup";
	}
	
	@RequestMapping("/com/task/popup/taskDeptMngerPopup.do")
	public String taskDeptMngerPopup(@ModelAttribute("taskOrgztPerVO") TaskOrgztPerVO taskOrgztPerVO ,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}	
		model.addAttribute("cmpnyMngerList", taskService.selectCompanyManagerList(taskOrgztPerVO));
		model.addAttribute("opList", taskService.selectTbOrgztPersonList(taskOrgztPerVO));
		return "tis/com/task/popup/taskDeptMngerPopup";
	}
	
	@RequestMapping("/com/task/popup/taskCoOperMngerPopup.do")
	public String taskCoOperMngerPopup(@ModelAttribute("taskOrgztPerVO") TaskOrgztPerVO taskOrgztPerVO ,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}		
		model.addAttribute("cmpnyMngerList", taskService.selectCompanyManagerList(taskOrgztPerVO));
		model.addAttribute("opList", taskService.selectTbOrgztPersonList(taskOrgztPerVO));
		return "tis/com/task/popup/taskCoOperMngerPopup";
	}
	
	@RequestMapping("/com/task/popup/taskDevelEnvirSave.do")
	public ModelAndView taskDevelEnvirSave(@ModelAttribute("taskEnvVO") TaskEnvVO taskEnvVO, @RequestParam(value="iUGbn",required=false) String iUGbn,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		if("I".equals(iUGbn)){
			taskService.insertTaskEnv(taskEnvVO);
		}else{
			taskService.updateTaskEnv(taskEnvVO);
		}
		
		map.put("result","OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/popup/taskDevelEnvirSubSave.do")
	public ModelAndView taskDevelEnvirSubSave(@ModelAttribute("taskEnvVO") TaskEnvVO taskEnvVO,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		taskService.insertTaskSubEnv(taskEnvVO);		
		map.put("result","OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/popup/taskDevelEnvirSubDelete.do")
	public ModelAndView taskDevelEnvirSubDelete(@ModelAttribute("taskEnvVO") TaskEnvVO taskEnvVO,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		
		Map<String,Object> map = new HashMap<String,Object>();
		taskService.deleteTaskSubEnv(taskEnvVO);		
		map.put("result","OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/popup/taskStepPopup.do")
	public String taskStepPopup(@ModelAttribute("taskVO") TaskVO taskVO,Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		model.addAttribute("taskSetpMaster",taskService.selectTaskSetpMaster());
		if(taskVO.getTaskId() != null  && !taskVO.getTaskId().isEmpty() && taskVO.getTaskStepId() != null  && !taskVO.getTaskStepId().isEmpty() ){
			model.addAttribute("resultList", taskService.selectTaskStep(taskVO));
		}else{
			model.addAttribute("resultList", taskService.selectTaskStepTemplate(taskVO));
		}		
		return "tis/com/task/popup/taskStepPopup";
	}
	
	@RequestMapping("/com/task/popup/taskStepSave.do")
	public ModelAndView taskStepSave(@ModelAttribute("taskVO") TaskStepVO taskStepVO,@RequestParam(value="iUGbn",required=false) String iUGbn, Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		if("I".equals(iUGbn)){
			taskService.insertTaskStep(taskStepVO);
		}else{
			taskService.updateTaskStep(taskStepVO);
		}
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("result", "OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/popup/taskPersonSave.do")
	public ModelAndView taskPersonInsert(@ModelAttribute("taskPersonVO") TaskPersonVO taskPersonVO, Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		taskService.taskPersonSave(taskPersonVO);
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("result", "OK");
		return new ModelAndView("ajaxMainView",map);
	}

	@RequestMapping("/com/task/popup/taskDeptMngerSave.do")
	public ModelAndView taskDeptMngerSave(@ModelAttribute("taskOrgztPerVO") TaskOrgztPerVO taskOrgztPerVO, Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		taskOrgztPerVO.setRegstr(user.getEmplyrNo());
		taskOrgztPerVO.setSept("1");
		taskService.taskDeptMngerSave(taskOrgztPerVO);
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("result", "OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/popup/taskCoOperMngerSave.do")
	public ModelAndView taskCoOperMngerSave(@ModelAttribute("taskOrgztPerVO") TaskOrgztPerVO taskOrgztPerVO, Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}
		taskOrgztPerVO.setRegstr(user.getEmplyrNo());
		taskOrgztPerVO.setSept("2");
		taskService.taskDeptMngerSave(taskOrgztPerVO);
		Map<String,Object> map = new HashMap<String , Object>();
		map.put("result", "OK");
		return new ModelAndView("ajaxMainView",map);
	}
	
	@RequestMapping("/com/task/popup/schdulRelaCmngPopup.do")
	public String schdulRelaCmngPopup(@ModelAttribute("taskOrgztPerVO") TaskOrgztPerVO taskOrgztPerVO, Model model)throws Exception{
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
		
		if (!isAuthenticated) {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			//return "home/EgovLoginUsr";
		}

		model.addAttribute("cmpnyMngerList", taskService.selectCompanyManagerList(taskOrgztPerVO));
		return "tis/com/task/popup/schdulRelaCmngPopup";
	}
}
