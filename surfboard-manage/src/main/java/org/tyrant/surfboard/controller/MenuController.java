package org.tyrant.surfboard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.surfboard.anno.TransCode;
import org.tyrant.surfboard.dto.AuthInfo;
import org.tyrant.surfboard.service.AuthService;
import org.tyrant.surfboard.service.MenuService;
import org.tyrant.surfboard.utils.ResponseUtils;

@RestController
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	MenuService service;
	@Autowired
	AuthService authService;
	
	@GetMapping("/qryMenu")
	@TransCode("/menu/qryMenu")
	public Map<String, Object> qryMenu() throws Exception {
		AuthInfo auth = authService.getAuthInfo();
		return ResponseUtils.response(GlobalValue.SUCCESS, service.qryMenu(auth.getUserId()));
	}
	
}
