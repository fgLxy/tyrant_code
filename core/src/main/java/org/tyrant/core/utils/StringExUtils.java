package org.tyrant.core.utils;

import org.apache.commons.lang.StringUtils;


public abstract class StringExUtils {
	/**
	 * 检查字符串数组是否全都不为空
	 * @param inputs
	 * @return
	 */
	public static boolean isEmpty(String... inputs) {
		if (inputs == null || inputs.length == 0) {
			return true;
		}
		for (String input : inputs) {
			if (StringUtils.isEmpty(input)) return true;
		}
		return false;
	}
	
}
