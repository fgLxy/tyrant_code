package org.tyrant.surfboard.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tyrant.dao.Idao;
import org.tyrant.dao.pagination.CommonList;
import org.tyrant.surfboard.dto.AddPowerDTO;
import org.tyrant.surfboard.dto.DeletePowerDTO;
import org.tyrant.surfboard.dto.PowerDTO;
import org.tyrant.surfboard.dto.UpdatePowerDTO;
import org.tyrant.surfboard.entity.GroupProductEntity;
import org.tyrant.surfboard.entity.ProductEntity;

import com.alibaba.druid.util.StringUtils;

@Service
public class PowerService {
	@Autowired
	Idao dao;

	public CommonList<PowerDTO> powerList() throws Exception {
		String sql = "select tmp.group_name group_name,group_concat(tmp.product_name) product_name from "
				+ "(select gp.group_name group_name,(case gp.status when 1 then gp.product_name else null end) "
				+ "product_name from sb_group g,sb_group_product gp,sb_product p "
				+ "where g.name=gp.group_name and p.name=gp.product_name) tmp group by tmp.group_name";
		List<GroupProductEntity> entitys = dao.queryObjects(GroupProductEntity.class, sql);
		CommonList<PowerDTO> powerList = new CommonList<>(entitys == null ? 0 : entitys.size(), 0, 0);
		List<PowerDTO> records = new ArrayList<>();
		if (entitys == null) {
			return powerList;
		}
		for (GroupProductEntity entity : entitys) {
			PowerDTO dto = new PowerDTO();
			dto.setGroupName(entity.getGroupName());
			if (!StringUtils.isEmpty(entity.getProductName())) {
				Set<String> powers = new HashSet<>();
				powers.addAll(Arrays.asList(entity.getProductName().split(",")));
				dto.setPowerList(powers);
			}
			else {
				dto.setPowerList(new HashSet<>());
			}
			records.add(dto);
		}
		powerList.setRecords(records);
		return powerList;
	}

	public void updatePower(UpdatePowerDTO dto) throws SQLException {
		String sql = "replace into sb_group_product(status,group_name,product_name) values(?,?,?)";
		dao.update(sql, dto.getNewValue(), dto.getGroupName(), dto.getProductName());
	}
	@Transactional(rollbackFor = Exception.class)
	public void addPower(AddPowerDTO dto) throws SQLException {
		String sql = "insert ignore into sb_group(name) values(?)";
		dao.update(sql, dto.getGroupName());
		sql = "select * from sb_product";
		List<ProductEntity> products = dao.queryObjects(ProductEntity.class, sql);
		if (products == null) {
			return;
		}
		sql = "insert ignore into sb_group_product(group_name,product_name,status) values(?,?,0)";
		Object[][] params = new Object[products.size()][2];
		for (int i = 0; i < products.size(); i++) {
			ProductEntity entity = products.get(i);
			params[i][0] = dto.getGroupName();
			params[i][1] = entity.getName();
		}
		dao.batchInsert(sql, params);
	}
	@Transactional(rollbackFor = Exception.class)
	public void deletePower(DeletePowerDTO dto) throws SQLException {
		String sql = "delete from sb_group where name=?";
		dao.update(sql, dto.getGroupName());
		sql = "delete from sb_group_product where group_name=?";
		dao.update(sql, dto.getGroupName());
	}

}
