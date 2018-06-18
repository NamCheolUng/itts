package tis.com.inc.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import egovframework.com.cmm.EgovMessageSource;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uat.uia.service.EgovLoginService;

@Controller
public class LoginController {
	
	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@RequestMapping(value = "/com/inc/login.do")
	public String login(ModelMap model, @RequestParam(value="loginStatus", required=false, defaultValue="0") int loginStatus ) throws Exception {

		model.addAttribute("msg", loginStatus);
		return "tis/com/inc/login";
	}
	
	@RequestMapping(value = "/com/inc/actionLogin.do")
	public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model) throws Exception {
		LoginVO resultVO = loginService.actionLogin(loginVO);
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
			// 2-1 . 퇴사자 로그인 접속 거부
			if(resultVO.getEmplyrEndde() != null ){
				return "forward:/com/inc/login.do?loginStatus=6";	
			}
			// 2-2. 로그인 정보를 세션에 저장
			request.getSession().setAttribute("loginVO", resultVO);
			return "redirect:/com/main.do";
		}else{			
			return "forward:/com/inc/login.do?loginStatus=2";	
		}
		
			

	}
	
	@RequestMapping(value = "/com/inc/actionLogout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {

		request.getSession().setAttribute("loginVO", null);

		return "tis/com/inc/login";
	}
}