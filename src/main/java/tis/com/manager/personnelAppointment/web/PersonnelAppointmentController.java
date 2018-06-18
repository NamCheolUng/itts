package tis.com.manager.personnelAppointment.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import tis.com.manager.personnelAppointment.service.ComthemplyrinfochangedtlsService;
import tis.com.manager.personnelAppointment.service.ComthemplyrinfochangedtlsVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uss.umt.service.EgovUserManageService;
import egovframework.com.uss.umt.service.UserManageVO;

@Controller
public class PersonnelAppointmentController {
	
	@Resource(name = "comthemplyrinfochangedtlsService")
	private ComthemplyrinfochangedtlsService emplyrinfochangeService;
	
	/** cmmUseService */
	@Resource(name = "EgovCmmUseService")
	private EgovCmmUseService cmmUseService;
	
	/** userManageService */
	@Resource(name = "userManageService")
	private EgovUserManageService userManageService;
	
	@RequestMapping(value = "/com/manager/personnelHistory.do")
	public String personnelHistory(@ModelAttribute("emplyrinfochangeVO")ComthemplyrinfochangedtlsVO emplyrinfochangeVO
			, @ModelAttribute("userManageVO") UserManageVO userManageVO, ModelMap model, HttpServletRequest request) throws Exception {

    	ComDefaultCodeVO vo = new ComDefaultCodeVO();

		//부서 조회
		vo.setCodeId("COM101");
		List<?> depart_result = cmmUseService.selectCmmCodeDetail(vo);
		//직위 조회
		vo.setCodeId("COM102");
		List<?> postion_result = cmmUseService.selectCmmCodeDetail(vo);		
		
		userManageVO = userManageService.selectUser(userManageVO.getUniqId());
    	model.addAttribute("userManageVO", userManageVO);
    	
    	model.addAttribute("chang_result", emplyrinfochangeService.selectComthemplyrinfochangedtlsList(emplyrinfochangeVO));
    	model.addAttribute("depart_result", depart_result); //부서구분
		model.addAttribute("postion_result", postion_result); //직위구분
    	
		return "tis/com/manager/personnelAppointment/personnelHistory";
	}
	
	@RequestMapping(value = "/com/manager/appointmentSave.do")
	public String appointmentSave(@ModelAttribute("emplyrinfochangeVO")ComthemplyrinfochangedtlsVO emplyrinfochangeVO
			, @ModelAttribute("userManageVO") UserManageVO userManageVO, ModelMap model, HttpServletRequest request) throws Exception {		

    	emplyrinfochangeService.insertComthemplyrinfochangedtls(emplyrinfochangeVO);
    	
		return "forward:/com/manager/personnelHistory.do";
	}
	
	
	
}