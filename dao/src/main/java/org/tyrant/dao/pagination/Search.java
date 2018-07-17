package org.tyrant.dao.pagination;

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
	//需要注入的参数
	private List<Object> params;
	//记录排序条件 Map<字段，排序方式>
	private Map<String, String> orders;
	//页号
	private Integer pageNo;
	//页长
	private Integer pageSize;
	
	public Search() {
		this.wheres = new LinkedList<>();
		this.params = new LinkedList<>();
		this.orders = new LinkedHashMap<>();
		this.pageNo = DEFAULT_PAGE_NO;
		this.pageSize = DEFAULT_PAGE_SIZE;
	}
	
	/**
	 * 添加查询条件
	 * @param operation 指定是and还是or
	 * @param property 指定查询的字段，例如name='test'
	 * @return
	 */
	public Search addWhere(AndOr operation, String property) {
		Assert.notNull(property, "查询条件不能为空");
		wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
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
		wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
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
	public Search addWhere(AndOr operation, String property, Object... params) {
		Assert.notNull(property, "查询条件不能为空");
		wheres.add((operation.equals(AndOr.AND) ? AND_STR : OR_STR) + property);
		if (params != null && params.length > 0) {
			for (Objecrt param : params) {
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
		StringBuilder builder = new StringBuilder(searchSql);
		if (searchSql.toLowerCase().indexOf("where") < 0) {
			builder.append(WHERE);
		}
		builder = appendList(builder, wheres, " ");
		builder = appendOrder(builder);
		builder.append(LIMIT).append(getPreRec()).append(",").append(pageSize);
		return builder.toString();
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
		StringBuilder builder = new StringBuilder(countSql);
		if (countSql.toLowerCase().indexOf("where") < 0) {
			builder.append(WHERE);
		}
		builder = appendList(builder, wheres, " ");
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
