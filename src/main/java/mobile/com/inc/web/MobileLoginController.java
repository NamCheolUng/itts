package mobile.com.inc.web;

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
public class MobileLoginController {
	
	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;

	/** EgovMessageSource */
	@Resource(name = "egovMessageSource")
	EgovMessageSource egovMessageSource;
	
	@RequestMapping(value = "/mobile/com/inc/login.do")
	public String login() throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (isAuthenticated) {
			return "redirect:/mobile/com/main.do";
		}
		
		return "/mobile/com/inc/login";
	}

	@RequestMapping(value = "/mobile/com/inc/actionLogin.do")
	public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, ModelMap model,
							  @RequestParam(value="tokenId", required=false) String tokenId) throws Exception {
		
		// 1. 일반 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
			// 2-1. 로그인 정보를 세션에 저장
			resultVO.setTokenId(tokenId);
			request.getSession().setAttribute("loginVO", resultVO);
			
			if(resultVO.getTokenId().equals("undefined")){
			}else{
			loginService.updtPushToken(resultVO);
			}
			return "redirect:/mobile/com/main.do";
		}else {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login"));
			return "mobile/com/inc/login";
		}
	}
	
	@RequestMapping(value = "/mobile/com/inc/actionLogout.do")
	public String actionLogout(HttpServletRequest request, ModelMap model) throws Exception {

		request.getSession().setAttribute("loginVO", null);

		return "/mobile/com/inc/login";
	}
	
}