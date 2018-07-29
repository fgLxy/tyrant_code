package org.tyrant.core.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class AspectUtils {
	/**
	 * 根据切点获取切面方法
	 * @param point
	 * @return
	 */
	public static Method getMethod(ProceedingJoinPoint point) {
		MethodSignature methodSignature = (MethodSignature) point.getSignature();  
		return methodSignature.getMethod();  
	}
	
	/**
	 * 根据切点获取切面方法上注解
	 * @param annoClazz
	 * @param point
	 * @return
	 */
	public static <T extends Annotation> T getAnnotation(Class<T> annoClazz, ProceedingJoinPoint point) {
		Method method = getMethod(point);
		return method.getAnnotation(annoClazz);
	}
	
}
