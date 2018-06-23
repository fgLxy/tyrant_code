package org.tyrant.surfboard.aspect;

import java.lang.reflect.Method;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.core.utils.AspectUtils;
import org.tyrant.surfboard.anno.TransCode;
import org.tyrant.surfboard.consts.RetCodeConstants;
import org.tyrant.surfboard.service.AuthService;
import org.tyrant.surfboard.utils.ResponseUtils;

@Aspect
@Component
public class PowerControlAspect {
	Logger log = LoggerFactory.getLogger(ExceptionAspect.class);
	
	@Autowired
	AuthService authService;
	
	@Around("execution(* org.tyrant.surfboard.controller..*(..)) and "
			+ "@annotation(org.tyrant.surfboard.anno.TransCode)")
	public Object requestAroundAspect(ProceedingJoinPoint point) {
		try {
			if (!authService.checkLoginState(getTransCode(point))) {
				return ResponseUtils.response(RetCodeConstants.SESSION_TIMEOUT, null);
			}
			if (!authService.checkUserPower(getTransCode(point))) {
				return ResponseUtils.response(RetCodeConstants.NO_POWER, null);
			}
			authService.refreshValidTime();
			return point.proceed();
		} catch(Throwable e) {
			log.info(ExceptionUtils.getFullStackTrace(e));
		}
		return ResponseUtils.response(GlobalValue.EXCEPTION_STATUS, "接口异常，请联系管理员");
	}

	private String getTransCode(ProceedingJoinPoint point) {
		Method method = AspectUtils.getMethod(point);
		TransCode transCode = method.getAnnotation(TransCode.class);
		return transCode.value();
	}
}
