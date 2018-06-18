package tis.com.inc.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Controller
public class MainController {

	@RequestMapping("/com/deviceInfo.do")
	public String deviceInfo(HttpServletRequest request) throws Exception {

		Device device = DeviceUtils.getCurrentDevice(request);

	    String url = "";

	    if (device.isNormal()) {
	        // Desktop
	    	url = "/com/inc/login.do";

	    } else if (device.isMobile()) {
	        // Mobile
	    	url = "/mobile/com/main.do";
	    }
		return "redirect:"+url;
	}
	
	@RequestMapping("/com/main.do")
	public String main(HttpServletRequest request) throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/com/inc/login.do";
		}
		return "redirect:/com/inc/myPage.do";
	}
}