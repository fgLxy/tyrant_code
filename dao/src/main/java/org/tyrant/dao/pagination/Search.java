package org.tyrant.dao.pagination;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.Assert;

/**
 * 用于构造分页查询语句
 * @author liu
 */
public class Search {
	
	private static final String WHERE = " where 1=1 ";
	private static final Integer DEFAULT_PAGE_NO = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 8;
	private static final String AND_STR = " and ";
	private static final String OR_STR = " or ";
	private static final String LIMIT = " limit ";
	
	public static enum AndOr {
		AND,OR
	}
	public static enum Order {
		DESC,ASC
	}
	public static final AndOr AND = AndOr.AND;
	public static final AndOr OR = AndOr.OR;
	public static final Order DESC = Order.DESC;
	public static final Order ASC = Order.ASC;
	
	//最终生成的sql语句
	private String searchSql;
	//最终生成的数量查询语句
	private String countSql;
	//记录查询条件
	private List<String> wheres;
	//分组查询条件
	private Map<String, List<String>> groupWhere;
	//需要注入的分组参数
	private Map<String, List<Object>> groupParams;
	//需要注入的参数
	private List<Object> params;
	//记录排序条件 Map<字段，排序方式>
	private Map<String, String> orders;
	//页号
	private Integer pageNo;
	//页长
	private Integer pageSize;
	
	private String where;
	
	public Search() {
		this.wheres = new LinkedList<>();
		this.params = new LinkedList<>();
		this.orders = new LinkedHashMap<>();
		this.pageNo = DEFAULT_PAGE_NO;
		this.pageSize = DEFAULT_PAGE_SIZE;
		this.groupWhere = new LinkedHashMap<>();
		this.groupParams = new LinkedHashMap<>();
	}
	
	/**
	 * 添加查询条件
	 * @param operation 指定是and还是or
	 * @param property 指定查询的字段，例如name='test'
	 * @return
	 */
	public Search addWhere(AndOr operation, String property) {
		Assert.notNull(property, "查询条件不能为空");
		if (wheres.isEmpty()) {
			wheres.add(AND_STR + property);
		} else {
			wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
		}
		return this;
	}
	
	public Search addGroupWhere(String groupId, AndOr operation, String property) {
		List<String> wheres = this.groupWhere.get(groupId);
		if (wheres == null) {
			wheres = new ArrayList<>();
			params = new ArrayList<>();
			this.groupWhere.put(groupId, wheres);
			this.groupParams.put(groupId, params);
		}
		if (wheres.isEmpty()) {
			wheres.add(AND_STR + property);
		} else {
			wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
		}
		return this;
	}
	
	/**
	 * 添加查询条件
	 * @param operation 指定是and还是or
	 * @param property 指定查询的字段，例如name=?
	 * @param param 要注入的参数
	 * @return
	 */
	public Search addWhere(AndOr operation, String property, Object param) {
		Assert.notNull(property, "查询条件不能为空");
		if (wheres.isEmpty()) {
			wheres.add(AND_STR + property);
		} else {
			wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
		}
		params.add(param);
		return this;
	}
	
	public Search addGroupWhere(String groupId, AndOr operation, String property, Object param) {
		List<String> wheres = this.groupWhere.get(groupId);
		List<Object> params = this.groupParams.get(groupId);
		if (wheres == null) {
			wheres = new ArrayList<>();
			params = new ArrayList<>();
			this.groupWhere.put(groupId, wheres);
			this.groupParams.put(groupId, params);
		}
		if (wheres.isEmpty()) {
			wheres.add(AND_STR + property);
		} else {
			wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
		}
		params.add(param);
		return this;
	}

	/**
	 * 添加查询条件
	 * @param operation 指定是and还是or
	 * @param property 指定查询的字段，例如name=?
	 * @param params 要注入的参数数组
	 * @return
	 */
	public Search addWhere(AndOr operation, String property, Object... paramArr) {
		Assert.notNull(property, "查询条件不能为空");
		if (wheres.isEmpty()) {
			wheres.add(AND_STR + property);
		} else {
			wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
		}
		if (paramArr != null && paramArr.length > 0) {
			for (Object param : paramArr) {
				params.add(param);
			}
		}
		return this;
	}

	/**
	 * 添加排序条件
	 * @param operation
	 * @param property
	 * @return
	 */
	public Search addOrder(Order order, String field) {
		order = order == null ? DESC : order;
		orders.put(field, (order == ASC) ? "asc" : "desc");
		return this;
	}
	/**
	 * 设置页号
	 * @param pageNo
	 * @return
	 */
	public Search setPageNo(Integer pageNo) {
		if (pageNo == null || pageNo <= DEFAULT_PAGE_NO) {
			return this;
		}
		this.pageNo = pageNo;
		return this;
	}
	/**
	 * 设置每页条数
	 * @param pageSize
	 * @return
	 */
	public Search setPageSize(Integer pageSize) {
		if (pageSize == null || pageSize <= 0) {
			return this;
		}
		this.pageSize = pageSize;
		return this;
	}
	/**
	 * 直接设置查询语句（设置后就不再生成，直接使用）
	 * @param searchSql
	 * @return
	 */
	public Search setSearchSql(String searchSql) {
		this.searchSql = searchSql;
		return this;
	}
	/**
	 * 直接设置条数统计语句（设置后就不再生成，直接使用）
	 * @param countSql
	 * @return
	 */
	public Search setCountSql(String countSql) {
		this.countSql = countSql;
		return this;
	}
	
	public String getCountSql() {
		return this.buildCountSql();
	}
	
	public String getSearchSql() {
		return this.buildSearchSql();
	}
	
	public Object[] getParams() {
		return this.params.toArray();
	}
	
	private String buildSearchSql() {
		init();
		StringBuilder builder = new StringBuilder(searchSql);
		if (searchSql.toLowerCase().indexOf("where") < 0) {
			builder.append(WHERE);
		}
		builder.append(buildWhere());
		builder = appendOrder(builder);
		builder.append(LIMIT).append(getPreRec()).append(",").append(pageSize);
		return builder.toString();
	}
	
	private void init() {
		if (!this.groupWhere.isEmpty()) {
			for (Entry<String, List<String>> entry : this.groupWhere.entrySet()) {
				List<Object> groupParams = this.groupParams.get(entry.getKey());
				this.params.addAll(groupParams);
				groupParams.clear();
			}
		}
	}

	private String buildWhere() {
		if (this.where != null && this.where.trim().equals("")) {
			return this.where;
		}
		StringBuilder builder = new StringBuilder();
		builder = appendList(builder, wheres, " ");
		if (!this.groupWhere.isEmpty()) {
			for (Entry<String, List<String>> entry : this.groupWhere.entrySet()) {
				builder.append(" and (1=1 ");
				builder = appendList(builder, entry.getValue(), " ");
				builder.append(") ");
			}
		}
		this.where = builder.toString();
		return this.where;
	}

	/**
	 * 拼接排序条件
	 * @param builder
	 * @return
	 */
	private StringBuilder appendOrder(StringBuilder builder) {
		int count = 0;
		for (Entry<String, String> order : orders.entrySet()) {
			String field = order.getKey();
			String orderType = order.getValue();
			builder.append(" order by ").append(field).append(" ").append(orderType);
			if (++count < orders.size()) {
				builder.append(",");
			}
		}
		return builder;
	}

	private String buildCountSql() {
		init();
		StringBuilder builder = new StringBuilder(countSql);
		if (countSql.toLowerCase().indexOf("where") < 0) {
			builder.append(WHERE);
		}
		builder.append(buildWhere());
		return builder.toString();
	}
	
	private StringBuilder appendList(StringBuilder builder, List<String> list, String split) {
		int length = list.size();
		for (int i = 0; i < length; i++) {
			builder.append(list.get(i));
			if (i < length - 1) {
				builder.append(split == null ? "" : split);
			}
			else {
				
			}
		}
		return builder;
	}
	
	private int getPreRec() {
        return pageSize * (pageNo - 1);
    }
	public Integer getPageNo() {
		return this.pageNo;
	}
	public Integer getPageSize() {
		return this.pageSize;
	}
	
}
