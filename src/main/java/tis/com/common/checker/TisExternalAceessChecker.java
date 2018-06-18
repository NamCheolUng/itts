package tis.com.common.checker;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestMapping;

import egovframework.com.cmm.LoginVO;

public class TisExternalAceessChecker implements TisAccessChecker {

	@Override
	public TisAccessCheckerResult check(ProceedingJoinPoint joinPoint) {
		Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
		String requestMapping = method.getAnnotation(RequestMapping.class).value()[0];
		
		TisAccessCheckerResult result = new TisAccessCheckerResult();

		List<String> unconstrainedMapper = new ArrayList<String>();
		unconstrainedMapper.add("/");
		unconstrainedMapper.add("/com/inc/login.do");
		unconstrainedMapper.add("/com/inc/actionLogin.do");
		unconstrainedMapper.add("/com/inc/actionLogout.do");
		unconstrainedMapper.add("/com/deviceInfo.do");
		unconstrainedMapper.add("/tis/uss/umt/userIdChk.do");

		if(unconstrainedMapper.contains(requestMapping)) {
			result.setSuccess(true);
			result.setViewName("");
			return result;
		}

		HttpServletRequest request = null;
        for ( Object obj : joinPoint.getArgs() ){
            if ( obj instanceof HttpServletRequest ) {
                request = (HttpServletRequest)obj;
            }
        }
		
        // ip를 받아오기 위해서는 request != null 이어야 되는데, 이를 위해서 Controller 메소드에서 파라미터로 "HttpServletRequest request" 값을 받아야 함
        String ip = getRemoteAddress(request);
        
        if(!ip.isEmpty() && isValidIPv4(ip)) {
			request.getSession().setAttribute("loginVO", null);
			
			result.setSuccess(false);
			result.setViewName("redirect:/com/inc/login.do?loginStatus=" + LOGIN_STATUS_ONLY_IPV4_ALLOWED);
			return result;
        }
        
        boolean allowedIp = false;
        
		if(ip.isEmpty() || ip.startsWith("172.16.1.") || ip.startsWith("127.0.0.1")) {
			allowedIp = true;
		}
		
		if(!allowedIp && !isExternalAccessableUser(request)) {
			request.getSession().setAttribute("loginVO", null);
			
			result.setSuccess(false);
			result.setViewName("redirect:/com/inc/login.do?loginStatus=" + LOGIN_STATUS_EXTERNAL_ACCESS_NOT_ALLOWED);
		} else {
	        result.setSuccess(true);
			result.setViewName("");
		}
		
		return result;
	}

	private String getRemoteAddress(HttpServletRequest request) {
        String ip = "";
        if(request != null) {
        	ip = request.getRemoteAddr();
        }
        
        return ip;
	}
	
	private boolean isExternalAccessableUser(HttpServletRequest request) {
		if(request != null) {
			LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");
			return (loginVO.getExternalAccess() != null && loginVO.getExternalAccess().equals("Y")) ? true : false;
		} else {
			return false;
		}
	}
	
	private boolean isValidIPv4(String ip) {
		String validIp = "^([1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])(\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])){3}$";
		return !Pattern.matches(validIp, ip);
	}
}
