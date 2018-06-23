package org.tyrant.dao;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.util.Assert;

@Component
public class TransactionQueryRunner extends QueryRunner {

	DataSource dataSource;

	@Autowired
	public TransactionQueryRunner(DataSource dataSource) {
		super(dataSource);
		this.dataSource = dataSource;
	}

	@Override
	protected Connection prepareConnection() throws SQLException {
		return DataSourceUtils.getConnection(getDataSource());
	}

	@Override
	protected void close(Connection conn) throws SQLException {
		if (!TransactionSynchronizationManager.isSynchronizationActive()) {
			super.close(conn);
		}
	}

	public Set<String> queryTableFields(String tableName) throws SQLException {
		Assert.notNull(tableName, "表名不能为空");
		Connection conn = this.prepareConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData meta = conn.getMetaData();
			rs = meta.getColumns(null, "%", tableName, "%");
			Set<String> fieldSet = new HashSet<>();
			while (rs.next()) {
				fieldSet.add(rs.getString("COLUMN_NAME"));
			}
			return fieldSet;
		} finally {
			close(rs);
			close(conn);
		}
	}

	public List<String> queryTablesName() throws SQLException {
		Connection conn = this.prepareConnection();
		ResultSet rs = null;
		try {
			DatabaseMetaData meta = conn.getMetaData();
			List<String> tableNames = new ArrayList<>();
			rs = meta.getTables(null, null, null, new String[] { "TABLE" });
			while (rs.next()) {
				tableNames.add(rs.getString("TABLE_NAME"));
			}
			return tableNames;
		} finally {
			close(rs);
			close(conn);
		}
	}

}
