package org.tyrant.surfboard.aspect;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.surfboard.utils.ResponseUtils;

@Aspect
@Component
public class ExceptionAspect {
	Logger log = LoggerFactory.getLogger(ExceptionAspect.class);
	
	@Around("execution(* org.tyrant.surfboard.controller..*(..)) and "
			+ "@annotation(org.springframework.web.bind.annotation.GetMapping) or @annotation(org.springframework.web.bind.annotation.PostMapping)")
	public Object requestAroundAspect(ProceedingJoinPoint point) {
		try {
			return point.proceed();
		} catch(Throwable e) {
			log.info(ExceptionUtils.getFullStackTrace(e));
		}
		return ResponseUtils.response(GlobalValue.EXCEPTION_STATUS, "接口异常，请联系管理员");
	}
	
}
