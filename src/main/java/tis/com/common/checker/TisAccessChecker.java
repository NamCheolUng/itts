package tis.com.common.checker;

import org.aspectj.lang.ProceedingJoinPoint;

public interface TisAccessChecker {
	public static final int LOGIN_STATUS_EXTERNAL_ACCESS_NOT_ALLOWED = 4;
	public static final int LOGIN_STATUS_ONLY_IPV4_ALLOWED = 5;
	
	public TisAccessCheckerResult check(ProceedingJoinPoint joinPoint);
}
