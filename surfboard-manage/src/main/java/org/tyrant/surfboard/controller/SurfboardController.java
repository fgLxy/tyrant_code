package org.tyrant.surfboard.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.surfboard.anno.TransCode;
import org.tyrant.surfboard.dto.SurfboardDTO;
import org.tyrant.surfboard.service.SurfboardService;
import org.tyrant.surfboard.utils.ResponseUtils;

@RestController
@RequestMapping("/surfboard")
public class SurfboardController {
	@Autowired
	SurfboardService service;
	
	@GetMapping("/qryList")
	@TransCode("/surfboard/qryList")
	public Map<String, Object> qryList(Integer pageNo, Integer pageSize, String orderKey, Boolean ascFlag) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.qryList(pageNo, pageSize, orderKey, ascFlag));
	}
	
	@GetMapping("/exportList")
	@TransCode("/surfboard/exportList")
	public void exportList(HttpServletResponse response, String orderKey, Boolean ascFlag) throws Exception {
		ResponseUtils.setDownloadFile(response, service.exportOperationLogList(orderKey, ascFlag));
	}
	
	@GetMapping("/qryOperationLogList")
	@TransCode("/surfboard/qryOperationLogList")
	public Map<String, Object> qryOperationLogList(Integer pageNo, Integer pageSize, String orderKey, Boolean ascFlag) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.qryOperationLogList(pageNo, pageSize, orderKey, ascFlag));
	}
	
	@GetMapping("/export/exportOperationLogList")
	@TransCode("/surfboard/exportOperationLogList")
	public void exportOperationLogList(HttpServletResponse response, String orderKey, Boolean ascFlag) throws Exception {
		ResponseUtils.setDownloadFile(response, service.exportOperationLogList(orderKey, ascFlag));
	}
	
	@PostMapping("/addSurfboard")
	@TransCode("/surfboard/addSurfboard")
	public Map<String, Object> addSurfboard(@RequestBody SurfboardDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.addSurfboard(dto));
	}
	
	@GetMapping("/qrySurfboardDetail")
	@TransCode("/surfboard/qrySurfboardDetail")
	public Map<String, Object> qrySurfboardDetail(String id) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.qrySurfboardDetail(id));
	}
}
