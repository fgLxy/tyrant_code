package org.tyrant.dao;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.tyrant.core.utils.NameUtils;

/**
 * 批量操作数据库。批量不忽略空值。
 * 
 * @author liu
 *
 * @param <T>
 */
public class BatchSqlResolver<T> {
	// 属性集合
	private Set<String> params;
	private Object[][] values;
	// 表名
	private String tableName;

	private int[] updateIds;

	/**
	 * 自动插入/更新，不对null值进行处理
	 * 
	 * @param obj
	 * @throws SQLException
	 */
	public BatchSqlResolver(List<T> objs, String tableName) throws SQLException {
		this(objs, tableName, null);
	}

	public BatchSqlResolver(List<T> objs, String tableName, int[] updateIds)
			throws SQLException {
		this.updateIds = updateIds;
		Assert.notNull(objs, "解析的参数不能为null");
		this.tableName = tableName;
		params = new LinkedHashSet<>();
		values = new Object[objs.size()][];
		try {
			initParams(objs.get(0));
			for (int i = 0; i < objs.size(); i++) {
				values[i] = getValue(objs.get(i));
			}
			;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new SQLException(e);
		}
	}

	public BatchSqlResolver(T obj, String tableName, int[] updateIds)
			throws SQLException {
		this.updateIds = updateIds;
		Assert.notNull(obj, "解析的参数不能为null");
		this.tableName = tableName;
		params = new LinkedHashSet<>();
		values = new Object[updateIds.length][];
		try {
			initParams(obj);
			for (int i = 0; i < updateIds.length; i++) {
				values[i++] = getValue(obj);
			}
			;
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			throw new SQLException(e);
		}
	}

	private Object[] getValue(Object obj) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		Object[] value = new Object[params.size()];
		String[] paramStrs = new String[params.size()];
		params.toArray(paramStrs);
		if (obj instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			for (int i = 0; i < paramStrs.length; i++) {
				value[i] = map.get(paramStrs[i]);
			}
		} else {
			Class<?> clazz = obj.getClass();
			for (int i = 0; i < paramStrs.length; i++) {
				value[i] = clazz.getMethod(
						"get"
								+ StringUtils.capitalize(NameUtils
										.underlineToCamel(paramStrs[i])))
						.invoke(obj);
			}
		}
		return value;
	}

	private void initParams(Object obj) throws IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException {
		if (obj instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) obj;
			for (Entry<String, Object> field : map.entrySet()) {
				String fieldName = field.getKey();
				params.add(fieldName);
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
					params.add(fieldName);
				}
				clazz = clazz.getSuperclass();
			}
		}
	}

	private boolean isIgnored(Field field) {
		return field.isAnnotationPresent(Ignore.class);
	}

	public String insertSql() {
		String[] keys = (String[]) params.toArray(new String[0]);
		StringBuilder builder = new StringBuilder();
		builder.append("insert into ").append(tableName).append("(");
		int length = keys.length;
		StringBuilder valueBuilder = new StringBuilder();
		valueBuilder.append(" values(");
		for (int i = 0; i < length; i++) {
			builder.append(NameUtils.camelToUnderline(keys[i]));
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

	/**
	 * 根据id更新。
	 * 
	 * @return
	 */
	public String updateSql() {
		String[] keys = (String[]) params.toArray(new String[0]);
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
		builder.append(" where id=?");
		builder.append(valueBuilder.toString());
		return builder.toString();
	}

	public Object[][] paramValues() {
		return values;
	}

	public Object[][] updateParamValues() {
		Object[][] newValues = new Object[values.length][];
		for (int i = 0; i < newValues.length; i++) {
			newValues[i] = new Object[values[i].length + 1];
			for (int j = 0; j < values[i].length; j++) {
				newValues[i][j] = values[i][j];
			}
			newValues[i][values[i].length] = updateIds[i];
		}
		return newValues;
	}
}
