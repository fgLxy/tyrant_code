package org.tyrant.surfboard.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.surfboard.dto.AddUserDTO;
import org.tyrant.surfboard.dto.DelUserDTO;
import org.tyrant.surfboard.dto.UpdateUserDTO;
import org.tyrant.surfboard.service.UserService;
import org.tyrant.surfboard.utils.ResponseUtils;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;
	
	@PostMapping("/addUser")
//	@TransCode("/user/addUser")
	public Map<String, Object> addUser(@RequestBody AddUserDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.addUser(dto));
	}
	
	@PostMapping("/updateUser")
//	@TransCode("/user/updateUser")
	public Map<String, Object> editUser(@RequestBody UpdateUserDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.updateUser(dto));
	}
	
	@PostMapping("/delUser")
//	@TransCode("/user/delUser")
	public Map<String, Object> delUser(@RequestBody DelUserDTO dto) throws SQLException {
		service.delUser(dto);
		return ResponseUtils.response(GlobalValue.SUCCESS, null);
	}
	
	
	@GetMapping("/userList")
//	@TransCode("/user/userList")
	public Map<String, Object> userList(Integer pageNo, Integer pageSize) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.userList(pageNo, pageSize));
	}
	
}
