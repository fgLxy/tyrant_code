package org.tyrant.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ColumnListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.tyrant.dao.pagination.CommonList;
import org.tyrant.dao.pagination.Search;

@Component("dao")
public class DaoImpl implements Idao {

	@Autowired
	@Qualifier("transactionQueryRunner")
	QueryRunner runner;

	static BasicRowProcessor DEFAULT_ROW_PROCESSOR = new TyrantRowProcessor(
			new BeanProcessor(new UnderlineToCamelMap()));

	/**
	 * 插入新数据
	 *
	 * @param object
	 * @return
	 */
	@Override
	public <T> int insert(T object, String tableName) throws SQLException {
		SqlResolver resolver = new SqlResolver(object, tableName);
		return runner.update(resolver.insertSql(), resolver.paramValues());
	}

	@Override
	public <T> long insertAndGetId(T object, String tableName)
			throws SQLException {
		insert(object, tableName);
		String sql = "select last_insert_id()";
		Number id = queryScalar(sql);
		return id == null ? 0 : id.longValue();
	}

	@Override
	public int[] batchInsert(String sql, Object[][] objects)
			throws SQLException {
		return runner.batch(sql, objects);
	}

	@Override
	public <T> int[] batchInsert(List<T> objects, String tableName)
			throws SQLException {
		BatchSqlResolver<T> resolver = new BatchSqlResolver<T>(objects,
				tableName);
		return runner.batch(resolver.insertSql(), resolver.paramValues());
	}

	@Override
	public <T> int[] batchUpdate(T object, String tableName, int[] ids)
			throws SQLException {
		BatchSqlResolver<T> resolver = new BatchSqlResolver<T>(object,
				tableName, ids);
		return runner.batch(resolver.updateSql(), resolver.updateParamValues());
	}

	@Override
	public <T> int[] batchUpdate(List<T> objects, String tableName, int[] ids)
			throws SQLException {
		BatchSqlResolver<T> resolver = new BatchSqlResolver<T>(objects,
				tableName, ids);
		return runner.batch(resolver.updateSql(), resolver.updateParamValues());
	}

	/**
	 * 数据库增insert删delete改update操作执行
	 *
	 * @param sql
	 * @param paramValues
	 *            参数列表的值
	 * @return int 影响数据库行数
	 */
	@Override
	public int update(String sql, Object... paramValues) throws SQLException {
		return (paramValues == null || paramValues.length == 0) ? runner
				.update(sql) : runner.update(sql, paramValues);
	}

	/**
	 * 根据ID来更新object
	 *
	 * @param object
	 * @return
	 * @throws SQLException
	 */
	@Override
	public <T> int update(T object, String tableName) throws SQLException {
		Assert.notNull(object, "update data cannot null");
		SqlResolver resolver = new SqlResolver(object, tableName);
		return runner.update(resolver.updateSql(), resolver.paramValues());
	}

	@Override
	public <T> int forceUpdate(T object, String tableName) throws SQLException {
		Assert.notNull(object, "update data cannot null");
		SqlResolver resolver = new SqlResolver(object, tableName, false);
		return runner.update(resolver.updateSql(), resolver.paramValues());
	}

	/**
	 * 根据id查询对象
	 *
	 * @param clazz
	 *            类需要有HdTable注解
	 * @param id
	 * @return
	 */
	@Override
	public <T> T queryObject(Class<T> clazz, String tableName, Integer id)
			throws SQLException {
		ResultSetHandler<T> handler = new BeanHandler<>(clazz,
				DEFAULT_ROW_PROCESSOR);
		return runner.query("select * from " + tableName + " where id=?",
				handler, id);
	}

	/**
	 * 根据sql查询单个对象
	 *
	 * @param clazz
	 * @param sql
	 * @param paramValues
	 * @return
	 */
	@Override
	public <T> T queryObject(Class<T> clazz, String sql, Object... paramValues)
			throws SQLException {
		return (paramValues == null || paramValues.length == 0) ? runner.query(
				sql, new BeanHandler<T>(clazz, DEFAULT_ROW_PROCESSOR)) : runner
				.query(sql, new BeanHandler<T>(clazz, DEFAULT_ROW_PROCESSOR),
						paramValues);
	}

	@Override
	public Map<String, Object> queryResult(String sql, Object... paramValues)
			throws SQLException {
		return (paramValues == null || paramValues.length == 0) ? runner.query(
				sql, new MapHandler(DEFAULT_ROW_PROCESSOR)) : runner.query(sql,
				new MapHandler(DEFAULT_ROW_PROCESSOR), paramValues);
	}

	/**
	 * 根据Sql查询单个字段值
	 *
	 * @param sql
	 *            select count(xxx)/sum(xxx)/id/username from .....
	 * @return count()函数返回Long sum()函数返回BigDecimal 其他返回字段对应的类型
	 */
	@Override
	public <T> T queryScalar(String sql, Object... paramValues)
			throws SQLException {
		return (paramValues == null || paramValues.length == 0) ? runner.query(
				sql, new ScalarHandler<T>()) : runner.query(sql,
				new ScalarHandler<T>(), paramValues);
	}

	@Override
	public <T> List<T> queryOneColumnList(String sql, Object... paramValues)
			throws SQLException {
		ColumnListHandler<T> hander = new ColumnListHandler<>(1);
		return (paramValues == null || paramValues.length == 0) ? runner.query(
				sql, hander) : runner.query(sql, hander, paramValues);
	}

	/**
	 * 查询数据列表
	 *
	 * @param clazz
	 * @param sql
	 * @return
	 */
	@Override
	public <T> List<T> queryObjects(Class<T> clazz, String sql,
			Object... paramValues) throws SQLException {
		return (paramValues == null || paramValues.length == 0) ? runner.query(
				sql, new BeanListHandler<T>(clazz, DEFAULT_ROW_PROCESSOR))
				: runner.query(sql, new BeanListHandler<T>(clazz,
						DEFAULT_ROW_PROCESSOR), paramValues);
	}

	/**
	 * 查询数量SQL
	 *
	 * @param sql
	 *            必须是查询数量的 select count(1)....
	 * @return
	 */
	@Override
	public long queryCount(String sql, Object... paramValues)
			throws SQLException {
		Number counts = queryScalar(sql, paramValues);
		return counts == null ? 0 : counts.longValue();
	}

	/**
	 * 分页查询
	 *
	 * @param search
	 * @param clazz
	 * @return
	 */
	@Override
	public <T> CommonList<T> pagination(Search search, Class<T> clazz)
			throws Exception {
		int recNum = 0; // 查询的总页数；

		List<T> objects = null;
		try {
			String countSql = search.getCountSql();
			String searchSql = search.getSearchSql();
			if (search.getParams().length == 0) {
				recNum = (int) queryCount(countSql);
				objects = queryObjects(clazz, searchSql); // 得到记录集合
			} else {
				recNum = (int) queryCount(countSql, search.getParams());
				objects = queryObjects(clazz, searchSql, search.getParams());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		CommonList<T> commonList = new CommonList<>(recNum, search.getPageNo(),
				search.getPageSize());
		if (objects != null) {
			commonList.setRecords(objects);
		}
		return commonList;
	}

	@Override
	public List<Map<String, Object>> queryResults(String sql,
			Object... paramValues) throws SQLException {
		return (paramValues == null || paramValues.length == 0) ? runner.query(
				sql, new ListMapHandler()) : runner.query(sql,
				new ListMapHandler(), paramValues);
	}

	@Override
	public CommonList<Map<String, Object>> pagination(Search search)
			throws Exception {
		int recNum = 0; // 查询的总页数；

		List<Map<String, Object>> objects = null;
		try {
			String countSql = search.getCountSql();
			String searchSql = search.getSearchSql();
			recNum = (int) queryCount(countSql, search.getParams());
			objects = queryResults(searchSql, search.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		}
		CommonList<Map<String, Object>> commonList = new CommonList<>(recNum,
				search.getPageNo(), search.getPageSize());
		if (objects != null) {
			commonList.setRecords(objects);
		}
		return commonList;
	}

	/**
	 * 查询数据库所有表名，数据源必须是TransactionQueryRunner类型
	 */
	@Override
	public List<String> queryTablesName() throws SQLException {
		TransactionQueryRunner transactionRunner = isTransactionQueryRunner();
		return transactionRunner.queryTablesName();
	}

	/**
	 * 查询指定表的键列表
	 */
	@Override
	public Set<String> queryTableFields(String tableName) throws SQLException {
		TransactionQueryRunner transactionRunner = isTransactionQueryRunner();
		return transactionRunner.queryTableFields(tableName);
	}

	/**
	 * 检查当前数据源是否为TransactionQueryRunner，如果是则返回如果不是则抛出异常
	 * 
	 * @return
	 */
	private TransactionQueryRunner isTransactionQueryRunner() {
		TransactionQueryRunner transactionRunner = null;
		if (runner instanceof TransactionQueryRunner) {
			transactionRunner = (TransactionQueryRunner) runner;
		} else {
			throw new UnsupportedOperationException(
					"当前QueryRunner不支持此操作,请使用TransactionQueryRunner");
		}
		return transactionRunner;
	}

}
