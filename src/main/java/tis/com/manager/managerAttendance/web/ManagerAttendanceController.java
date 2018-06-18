package tis.com.manager.managerAttendance.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.cmt.service.CmtManageVO;
import egovframework.com.uss.cmt.service.EgovCmtManageService;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;

@Controller
public class ManagerAttendanceController {
	
	/** cmtManageService */
	@Resource(name = "cmtManageService")
	private EgovCmtManageService cmtManageService;
	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	/** userManageService */
	@Resource(name = "userManageService")
	private EgovUserManageService userManageService;
	
	/*근태리스트*/
	@RequestMapping(value = "/com/manager/managerAttendance/managerAttendanceList.do")
	public String managerAttendanceList(@ModelAttribute("cmtManageVO")CmtManageVO cmtManageVO, ModelMap model, HttpServletRequest request) throws Exception {		
		
    	String nowDate = EgovDateUtil.formatDate( EgovDateUtil.getToday(),"/");
    	if(cmtManageVO.getWrktDt() == null || cmtManageVO.getWrktDt().isEmpty()){
    		model.addAttribute("nowDate", nowDate);
    		cmtManageVO.setWrktDt(nowDate.replace("/", ""));    		
    	}else{
    		model.addAttribute("nowDate", EgovDateUtil.formatDate( cmtManageVO.getWrktDt(),"/"));
    	}
    	
    	if(cmtManageVO.getOrgnztId() != null && !cmtManageVO.getOrgnztId().isEmpty())
			model.addAttribute("depart", cmtManageVO.getOrgnztId());
		else
			model.addAttribute("depart", "");
    	
    	model.addAttribute("resultList", cmtManageService.selectDateCmtList(cmtManageVO));  
    	ComDefaultCodeVO vo = new ComDefaultCodeVO();

		//부서 조회
		vo.setCodeId("COM101");
		model.addAttribute("depart_result", cmmUseService.selectCmmCodeDetail(vo));		
    	
		return "tis/com/manager/managerAttendance/managerAttendanceList";
	}
	
	public String not8formatDate(String sDate, String ch) {	

		String str = sDate.trim();
		String yyyy = "";
		String mm = "";
		String dd = "";

		if (str.length() == 6) {
			yyyy = str.substring(0, 4);
			if (yyyy.equals("0000")) {
				return "";
			}

			mm = str.substring(4, 6);
			if (mm.equals("00")) {
				return yyyy;
			}

			return yyyy + ch + mm;
			
		} else if (str.length() == 4) {
			yyyy = str.substring(0, 4);
			if (yyyy.equals("0000")) {
				return "";
			} else {
				return yyyy;
			}
		} else {
			return "";
		}
	}

	/*근태상세보기*/
	@RequestMapping(value = "/com/manager/managerAttendance/managerAttendanceDetail.do")
	public String managerAttendanceDetail(@ModelAttribute("cmtManageVO")CmtManageVO cmtManageVO, ModelMap model, HttpServletRequest request) throws Exception {	
		
    	String nowMM = not8formatDate( EgovDateUtil.getToday().substring(0, 6),"/");
    	if(cmtManageVO.getWrktDt() == null || cmtManageVO.getWrktDt().isEmpty()){
    		model.addAttribute("nowMM", nowMM);
    		cmtManageVO.setWrktDt(nowMM.replace("/", ""));    		
    	}else if(cmtManageVO.getWrktDt().length() == 10){
    		cmtManageVO.setWrktDt(cmtManageVO.getWrktDt().replace("-", "").substring(0, 6));
    		model.addAttribute("nowMM", not8formatDate( cmtManageVO.getWrktDt(),"/"));
    	}else{
    		model.addAttribute("nowMM", not8formatDate( cmtManageVO.getWrktDt(),"/"));
    	}
    	
    	
    	model.addAttribute("resultList", cmtManageService.selectUserMonthCmtList(cmtManageVO));
    	
    	UserManageVO userManageVO = new UserManageVO();
    	userManageVO = userManageService.selectUser(cmtManageVO.getEmplyrId());
		
		model.addAttribute("userManageVO", userManageVO);
    	
		return "tis/com/manager/managerAttendance/managerAttendanceDetail";
	}
	
	/*근태수정페이지*/
	@RequestMapping(value = "/com/manager/managerAttendance/managerAttendanceModify.do")
	public String managerAttendanceModify(@ModelAttribute("cmtManageVO")CmtManageVO cmtManageVO, ModelMap model, HttpServletRequest request) throws Exception {
		
    	String dateString = cmtManageVO.getWrktDt();
    	cmtManageVO.setWrktDt(cmtManageVO.getWrktDt().replace("-", ""));
    	UserManageVO userManageVO = new UserManageVO();
    	userManageVO = userManageService.selectUser(cmtManageVO.getEmplyrId());
		
    	CmtManageVO tmpVO = cmtManageService.selectUserCmtDetail(cmtManageVO);
    	if(tmpVO != null)
    		model.addAttribute("result", tmpVO);
    	else{
    		cmtManageVO.setWrktDt(dateString);
    		model.addAttribute("result", cmtManageVO);
    	}
    		
		model.addAttribute("userManageVO", userManageVO);
		
		return "tis/com/manager/managerAttendance/managerAttendanceModify";
	}
	
	/*근태수정저장*/
	@RequestMapping(value = "/com/manager/managerAttendance/managerAttendanceSave.do")
	public String managerAttendanceSave(@ModelAttribute("cmtManageVO")CmtManageVO cmtManageVO, ModelMap model, HttpServletRequest request) throws Exception {		
    	UserManageVO userManageVO = new UserManageVO();
    	userManageVO = userManageService.selectUser(cmtManageVO.getEmplyrId());
    	cmtManageVO.setOrgnztId(userManageVO.getOrgnztId());
    	cmtManageVO.setWrktDt(cmtManageVO.getWrktDt().replace("-", ""));

    	
    	if(cmtManageVO.getWrktmId() != null && !cmtManageVO.getWrktmId().isEmpty()){
    		cmtManageService.updateWrkCmtInfoOfManager(cmtManageVO);
    	}
    	else{
    		cmtManageService.insertWrkCmtInfoOfManager(cmtManageVO);
    	}
    	cmtManageVO.setWrktDt(cmtManageVO.getWrktDt().substring(0, 6));
		return "forward:/com/manager/managerAttendance/managerAttendanceDetail.do";
	}
	
}