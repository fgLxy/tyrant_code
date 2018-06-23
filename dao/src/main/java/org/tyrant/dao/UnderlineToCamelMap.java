package org.tyrant.dao;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.tyrant.core.utils.NameUtils;

/**
 * 用于数据库中结果集命名转换
 * 
 * @author liu
 *
 */
public class UnderlineToCamelMap implements Map<String, String> {

	@Override
	public int size() {
		return 0;
	}

	@Override
	public boolean isEmpty() {
		return false;
	}

	@Override
	public boolean containsKey(Object key) {
		return false;
	}

	@Override
	public boolean containsValue(Object value) {
		return false;
	}

	@Override
	public String get(Object key) {
		String strKey = (String) key;
		return NameUtils.underlineToCamel(strKey);
	}

	@Override
	public String put(String key, String value) {
		return null;
	}

	@Override
	public String remove(Object key) {
		return null;
	}

	@Override
	public void putAll(Map<? extends String, ? extends String> m) {

	}

	@Override
	public void clear() {

	}

	@Override
	public Set<String> keySet() {
		return null;
	}

	@Override
	public Collection<String> values() {
		return null;
	}

	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return null;
	}

}
