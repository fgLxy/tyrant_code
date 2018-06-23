package org.tyrant.surfboard.controller;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.surfboard.anno.TransCode;
import org.tyrant.surfboard.consts.RetCodeConstants;
import org.tyrant.surfboard.dto.AddUserDTO;
import org.tyrant.surfboard.dto.DelUserDTO;
import org.tyrant.surfboard.dto.SetUserPower;
import org.tyrant.surfboard.service.UserService;
import org.tyrant.surfboard.utils.ResponseUtils;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService service;
	
	@PostMapping("/addUser")
	@TransCode("/user/addUser")
	public Map<String, Object> addUser(@RequestBody AddUserDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.addUser(dto));
	}
	
	@PostMapping("/delUser")
	@TransCode("/user/delUser")
	public Map<String, Object> delUser(@RequestBody DelUserDTO dto) throws SQLException {
		service.delUser(dto);
		return ResponseUtils.response(GlobalValue.SUCCESS, null);
	}
	
	@PostMapping("/setUserPower")
	@TransCode("/user/setUserPower")
	public Map<String, Object> setUserPower(@RequestBody SetUserPower dto) throws SQLException {
		if (!service.checkUser(dto.getUserId())) {
			return ResponseUtils.response(RetCodeConstants.NO_USER, null);
		}
		service.setUserPower(dto);
		return ResponseUtils.response(GlobalValue.SUCCESS, null);
	}
	
}
