package org.tyrant.dao;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.ResultSetHandler;

public class ListMapHandler implements
		ResultSetHandler<List<Map<String, Object>>> {

	@Override
	public List<Map<String, Object>> handle(ResultSet rs) throws SQLException {
		ResultSetMetaData metaData = rs.getMetaData();
		int count = metaData.getColumnCount();
		List<Map<String, Object>> rtn = new ArrayList<>();
		while (rs.next()) {
			Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < count; i++) {
				map.put(metaData.getColumnName(i + 1), rs.getObject(i + 1));
			}
			rtn.add(map);
		}
		return rtn;
	}

}
