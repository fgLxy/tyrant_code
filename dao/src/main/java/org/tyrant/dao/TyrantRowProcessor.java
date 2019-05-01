package org.tyrant.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.BeanProcessor;
import org.tyrant.core.utils.NameUtils;

public class TyrantRowProcessor extends BasicRowProcessor {
	
	
	public TyrantRowProcessor(BeanProcessor beanProcessor) {
		super(beanProcessor);
	}

	@Override
    public Map<String, Object> toMap(ResultSet rs) throws SQLException {
		Map<String, Object> result = super.toMap(rs);
		Map<String, Object> newResult = new HashMap<>();
		for (Entry<String, Object> entry : result.entrySet()) {
			if ("status".equals(entry.getKey()) && entry.getValue() instanceof Boolean) {
				newResult.put(NameUtils.underlineToCamel(entry.getKey()), (boolean) entry.getValue() ? 0 : 1);
			}
			else {
				newResult.put(NameUtils.underlineToCamel(entry.getKey()), entry.getValue());
			}
		}
        return newResult;
    }
	
}
