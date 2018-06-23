package org.tyrant.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.tyrant.dao.pagination.CommonList;
import org.tyrant.dao.pagination.Search;

public interface Idao {

	<T extends BaseEntity> int insert(T object, String tableName) throws SQLException;

	<T extends BaseEntity> long insertAndGetId(T object, String tableName) throws SQLException;

	<T> int[] batchInsert(List<T> objects, String tableName)
			throws SQLException;

	int[] batchInsert(String sql, Object[][] objects) throws SQLException;

	int update(String sql, Object... paramValues) throws SQLException;

	<T extends BaseEntity> int forceUpdate(T object, String tableName) throws SQLException;

	<T extends BaseEntity> int update(T object, String tableName) throws SQLException;

	<T> int[] batchUpdate(T object, String tableName, int[] ids)
			throws SQLException;

	<T> int[] batchUpdate(List<T> objects, String tableName, int[] ids)
			throws SQLException;

	<T> T queryObject(Class<T> clazz, String tableName, Integer id)
			throws SQLException;

	<T> T queryObject(Class<T> clazz, String sql, Object... paramValues)
			throws SQLException;

	<T> List<T> queryObjects(Class<T> clazz, String sql, Object... paramValues)
			throws SQLException;

	Map<String, Object> queryResult(String sql, Object... paramValues)
			throws SQLException;

	List<Map<String, Object>> queryResults(String sql, Object... paramValues)
			throws SQLException;

	<T> T queryScalar(String sql, Object... paramValues) throws SQLException;

	<T> List<T> queryOneColumnList(String sql, Object... paramValues)
			throws SQLException;

	long queryCount(String sql, Object... paramValues) throws SQLException;

	CommonList<Map<String, Object>> pagination(Search search) throws Exception;

	<T> CommonList<T> pagination(Search search, Class<T> clazz)
			throws Exception;

	List<String> queryTablesName() throws SQLException;

	Set<String> queryTableFields(String tableName) throws SQLException;

}
