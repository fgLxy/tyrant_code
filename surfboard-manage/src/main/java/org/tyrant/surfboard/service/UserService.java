package org.tyrant.surfboard.service;

import java.sql.SQLException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tyrant.core.utils.IdFactoryUtils;
import org.tyrant.dao.Idao;
import org.tyrant.dao.pagination.CommonList;
import org.tyrant.dao.pagination.Search;
import org.tyrant.surfboard.dto.AddUserDTO;
import org.tyrant.surfboard.dto.DelUserDTO;
import org.tyrant.surfboard.dto.UpdateUserDTO;
import org.tyrant.surfboard.entity.UserEntity;

@Service
public class UserService {
	
	private static final int MAX_QRY_LIST_PAGESIZE = 20;
	
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
		dao.update("delete from sb_user where id=?", dto.getId());
	}

	public boolean checkUser(String id) throws SQLException {
		return dao.queryCount("select count(*) from sb_user where id=?", id) > 0;
	}

	public CommonList<UserEntity> userList(Integer pageNo, Integer pageSize) throws Exception {
		Search search = new Search();
		search.setSearchSql("select * from sb_user");
		search.setCountSql("select count(*) from sb_user");
		search.setPageNo(pageNo);
		search.setPageSize(Math.min(pageSize, MAX_QRY_LIST_PAGESIZE));
		return dao.pagination(search, UserEntity.class);
	}

	public String updateUser(UpdateUserDTO dto) throws SQLException {
		UserEntity user = new UserEntity();
		BeanUtils.copyProperties(dto, user);
		dao.update(user, "sb_user");
		return user.getId();
	}

}
