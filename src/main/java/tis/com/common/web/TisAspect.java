package tis.com.common.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Method;







import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;

import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.web.bind.annotation.RequestMapping;

import tis.com.common.checker.TisAccessChecker;
import tis.com.common.checker.TisAccessCheckerResult;
import tis.com.common.checker.TisExternalAceessChecker;

@Aspect
public class TisAspect {
    @Pointcut("execution(public * tis.com..*.web.*Controller.*(..))")
    private void checkAccess(){}
    
    @Around(value = "checkAccess()")
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		Method method = ((MethodSignature)joinPoint.getSignature()).getMethod();
		String requestMapping = method.getAnnotation(RequestMapping.class).value()[0];
		
		// 로그인 체크
		List<String> loginCheckExcept = new ArrayList<String>();
		loginCheckExcept.add("/");
		loginCheckExcept.add("/com/inc/login.do");
		loginCheckExcept.add("/com/inc/actionLogin.do");
		loginCheckExcept.add("/com/deviceInfo.do");
		loginCheckExcept.add("/tis/uss/umt/userIdChk.do");
		
		Boolean isAuthenticated = EgovUserDetailsHelper.isAuthenticated();
		if (!isAuthenticated) {
			if(!loginCheckExcept.contains(requestMapping)){
				return "redirect:/com/inc/login.do?loginStatus=1";
			}
		}
		
		// 관리자 체크
		HttpServletRequest request = null;
        for ( Object obj : joinPoint.getArgs() ){
            if ( obj instanceof HttpServletRequest ) {
                request = (HttpServletRequest)obj;
            }
        }

        if(request != null) {
	        LoginVO loginVO = (LoginVO)request.getSession().getAttribute("loginVO");

	        // 관리자 접근 메뉴
			List<String> adminMappingList = new ArrayList<String>();
			adminMappingList.add("/com/financial");
			adminMappingList.add("/com/manager");
			adminMappingList.add("/com/member/EgovUserManage.do");
			adminMappingList.add("/com/member/EgovUserInsertView.do");

			for(String adminMapping : adminMappingList) {

				if(requestMapping.startsWith(adminMapping) && !loginVO.getManageAt().equals("Y")) {
					request.getSession().setAttribute("loginVO", null);
					
					return "redirect:/com/inc/login.do?loginStatus=3";
				}
			}
        }
        
        List<TisAccessChecker> externalAccessCheckerList = new ArrayList<TisAccessChecker>();
        externalAccessCheckerList.add(new TisExternalAceessChecker());
        
        for(TisAccessChecker accessChecker : externalAccessCheckerList) {
	        TisAccessCheckerResult checkResult = accessChecker.check(joinPoint);
	        if(!checkResult.isSuccess()) {
	        	return checkResult.getViewName();
	        }
        }

		Object result = joinPoint.proceed();
		return result;
	}
}