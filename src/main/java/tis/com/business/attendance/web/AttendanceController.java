package tis.com.business.attendance.web;

import java.util.Calendar;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.cmt.service.CmtManageVO;
import egovframework.com.uss.cmt.service.EgovCmtManageService;
import egovframework.com.uss.umt.service.UserManageVO;
import egovframework.com.utl.fcc.service.EgovDateUtil;

@Controller
public class AttendanceController {
	
	/** cmtManageService */
	@Resource(name = "cmtManageService")
	private EgovCmtManageService cmtManageService;
	
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
	
	
	
	/*근태리스트*/
	@RequestMapping(value = "/com/business/attendance/attendanceList.do")
	public String attendanceList(@ModelAttribute("cmtManageVO")CmtManageVO cmtManageVO, ModelMap model) throws Exception {
	
    	LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();
    	
    	cmtManageVO.setEmplyrId(user.getUniqId());    	
    	
    	String nowMM = not8formatDate( EgovDateUtil.getToday().substring(0, 6),"/");
    	if(cmtManageVO.getWrktDt() == null || cmtManageVO.getWrktDt().isEmpty()){
    		model.addAttribute("nowMM", nowMM);
    		cmtManageVO.setWrktDt(nowMM.replace("/", ""));    		
    	}else{
    		model.addAttribute("nowMM", not8formatDate( cmtManageVO.getWrktDt(),"/"));
    	}   	
    	
    	model.addAttribute("resultList", cmtManageService.selectUserMonthCmtList(cmtManageVO));    	

		return "tis/com/business/attendance/attendanceList";
	}
}