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
import org.tyrant.surfboard.dto.AddStorageDTO;
import org.tyrant.surfboard.dto.DelStorageDTO;
import org.tyrant.surfboard.dto.SurfboardDTO;
import org.tyrant.surfboard.dto.SurfboardStateListDTO;
import org.tyrant.surfboard.service.SurfboardService;
import org.tyrant.surfboard.utils.ResponseUtils;

@RestController
@RequestMapping("/surfboard")
public class SurfboardController {
	@Autowired
	SurfboardService service;
	
	@GetMapping("/surfboardList")
//	@TransCode("/surfboard/surfboardList")
	public Map<String, Object> surfboardList(Integer pageNo, Integer pageSize, String orderKey, Boolean ascFlag) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.surfboardList(pageNo, pageSize, orderKey, ascFlag));
	}
	
	@PostMapping("/surfboardStateList")
//	@TransCode("/surfboard/surfboardList")
	public Map<String, Object> surfboardStateList(@RequestBody SurfboardStateListDTO dto) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.surfboardStateList(dto));
	}
	
	@GetMapping("/dataList")
//	@TransCode("/surfboard/dataList")
	public Map<String, Object> dataList(Integer pageNo, Integer pageSize, String startDate, String endDate) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.dataList(pageNo, pageSize, startDate, endDate));
	}
	
	@GetMapping("/totalIncome")
//	@TransCode("/surfboard/totalIncome")
	public Map<String, Object> totalIncome(String startDate, String endDate) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.totalIncome(startDate, endDate));
	}
	
	@GetMapping("/exportDataList")
//	@TransCode("/surfboard/dataList")
	public void exportDataList(HttpServletResponse response, String startDate, String endDate) throws Exception {
		ResponseUtils.setDownloadFile(response, service.exportDataList(startDate, endDate));
	}
	
	@PostMapping("/addSurfboard")
//	@TransCode("/surfboard/addSurfboard")
	public Map<String, Object> addSurfboard(@RequestBody SurfboardDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.addSurfboard(dto));
	}
	
	@PostMapping("/updateSurfboard")
//	@TransCode("/surfboard/updateSurfboard")
	public Map<String, Object> updateSurfboard(@RequestBody SurfboardDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.updateSurfboard(dto));
	}
	
	@PostMapping("/delSurfboard")
//	@TransCode("/surfboard/delSurfboard")
	public Map<String, Object> delSurfboard(@RequestBody SurfboardDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.delSurfboard(dto));
	}
	
	@GetMapping("/storageList")
//	@TransCode("/surfboard/storageList")
	public Map<String, Object> storageList(Integer pageNo, Integer pageSize) throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.storageList(pageNo, pageSize));
	}
	
	@PostMapping("/addStorage")
//	@TransCode("/surfboard/addStorage")
	public Map<String, Object> addStorage(@RequestBody AddStorageDTO dto) throws SQLException {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.addStorage(dto));
	}
	
	@PostMapping("/delStorage")
//	@TransCode("/surfboard/delStorage")
	public Map<String, Object> delStorage(@RequestBody DelStorageDTO dto) throws SQLException {
		service.delStorage(dto);
		return ResponseUtils.response(GlobalValue.SUCCESS, null);
	}
}
