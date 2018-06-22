package org.tyrant.core.utils;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

public class AspectUtils {

	public static Method getMethod(ProceedingJoinPoint point) {
		MethodSignature methodSignature = (MethodSignature) point.getSignature();  
		return methodSignature.getMethod();  
	}

}
