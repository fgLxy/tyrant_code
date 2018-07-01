package org.tyrant.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.tyrant.core.utils.NameUtils;

public class SqlResolver {
	// 属性集合
	private Map<String, Object> params;
	// 表名
	private String tableName;
	// 是否允许空值更新
	private boolean nullIgnore;

	/**
	 * 自动插入/更新，不对null值进行处理
	 * 
	 * @param obj
	 * @throws SQLException
	 */
	public SqlResolver(Object obj, String tableName) throws SQLException {
		this(obj, tableName, true);
	}

	public SqlResolver(Object obj, String tableName, boolean nullIgnore)
			throws SQLException {
		Assert.notNull(obj, "解析的参数不能为null");
		this.tableName = tableName;
		params = new LinkedHashMap<>();
		this.nullIgnore = nullIgnore;
		try {
			initParams(obj);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new SQLException(e);
		}
	}

	private void initParams(Object obj) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		if (obj instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			for (Entry<String, Object> field : map.entrySet()) {
				String fieldName = field.getKey();
				Object value = field.getValue();
				if (nullIgnore && value == null) {
					continue;
				}
				params.put(fieldName, value);
			}
		} else {
			Class<?> clazz = obj.getClass();
			while (!clazz.equals(Object.class)) {
				Field[] fieldArray = clazz.getDeclaredFields();
				for (Field field : fieldArray) {
					if (isIgnored(field)) {
						continue;
					}
					String fieldName = field.getName();
					Object value = clazz.getMethod(
							"get" + StringUtils.capitalize(field.getName()))
							.invoke(obj);
					if (nullIgnore && value == null) {
						continue;
					}
					params.put(fieldName, value);
				}
				clazz = clazz.getSuperclass();
			}
		}
	}

	private boolean isIgnored(Field field) {
		return field.isAnnotationPresent(Ignore.class);
	}

	public String insertSql() {
		String[] keys = params.keySet().toArray(new String[0]);
		StringBuilder builder = new StringBuilder();
		builder.append("insert into `").append(tableName).append("`(");
		int length = keys.length;
		StringBuilder valueBuilder = new StringBuilder();
		valueBuilder.append(" values(");
		for (int i = 0; i < length; i++) {
			builder.append("`" + NameUtils.camelToUnderline(keys[i]) + "`");
			valueBuilder.append("?");
			if (i < length - 1) {
				builder.append(",");
				valueBuilder.append(",");
			}
		}
		builder.append(") ");
		valueBuilder.append(")");
		builder.append(valueBuilder.toString());
		return builder.toString();
	}

	public Object[] paramValues() {
		return params.values().toArray();
	}

	/**
	 * 根据id更新。
	 * 
	 * @return
	 */
	public String updateSql() {
		String[] keys = (String[]) params.keySet().toArray(new String[0]);
		StringBuilder builder = new StringBuilder();
		builder.append("update `").append(tableName).append("` set ");
		int length = keys.length;
		StringBuilder valueBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			builder.append("`" + NameUtils.camelToUnderline(keys[i]) + "`")
					.append("=").append("?");
			if (i < length - 1) {
				builder.append(",");
			}
		}
		builder.append(" where id='").append(params.get("id")).append("'");
		builder.append(valueBuilder.toString());
		return builder.toString();
	}

}
