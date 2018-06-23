package org.tyrant.surfboard.service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.tyrant.core.utils.IdFactoryUtils;
import org.tyrant.dao.Idao;
import org.tyrant.surfboard.dto.AddUserDTO;
import org.tyrant.surfboard.dto.DelUserDTO;
import org.tyrant.surfboard.dto.SetUserPower;
import org.tyrant.surfboard.entity.UserEntity;
import org.tyrant.surfboard.entity.UserProductEntity;

@Service
public class UserService {
	@Autowired
	Idao dao;

	public String addUser(AddUserDTO dto) throws SQLException {
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(dto, user);
		user.setId(IdFactoryUtils.generateId());
		dao.insert(user, "sb_user");
		return user.getId();
	}

	public void delUser(DelUserDTO dto) throws SQLException {
		dao.update("delete from sb_user where user_id=?", dto.getUserId());
	}
	
	@Transactional(rollbackFor = Exception.class)
	public void setUserPower(SetUserPower dto) throws SQLException {
		dao.update("delete from sb_user_product where user_id=?", dto.getUserId());
		List<UserProductEntity> userProducts = dto.getProducts().stream().map(product -> {
			UserProductEntity entity = new UserProductEntity();
			entity.setProductName(product);
			entity.setUserId(dto.getUserId());
			return entity;
		}).collect(Collectors.toList());
		dao.batchInsert(userProducts, "sb_user_product");
	}

	public boolean checkUser(String userId) throws SQLException {
		return dao.queryCount("select count(*) from sb_user where user_id=?", userId) > 0;
	}

}
