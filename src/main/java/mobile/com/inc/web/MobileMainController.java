package mobile.com.inc.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.util.EgovUserDetailsHelper;

@Controller
public class MobileMainController {

	@RequestMapping("/mobile/com/main.do")
	public String mobileMain() throws Exception {
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			return "redirect:/mobile/com/inc/login.do";
		}
		return "redirect:/mobile/com/receipt/receiptInsert.do";
	}
}