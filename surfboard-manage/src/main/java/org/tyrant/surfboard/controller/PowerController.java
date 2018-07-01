package org.tyrant.surfboard.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.tyrant.core.constant.GlobalValue;
import org.tyrant.surfboard.dto.AddPowerDTO;
import org.tyrant.surfboard.dto.DeletePowerDTO;
import org.tyrant.surfboard.dto.UpdatePowerDTO;
import org.tyrant.surfboard.service.PowerService;
import org.tyrant.surfboard.utils.ResponseUtils;

@RestController
@RequestMapping("/power")
public class PowerController {
	
	@Autowired
	PowerService service;
	
	
	@GetMapping("/powerList")
//	@TransCode("/power/powerList")
	public Map<String, Object> powerList() throws Exception {
		return ResponseUtils.response(GlobalValue.SUCCESS, service.powerList());
	}
	
	@PostMapping("/updatePower")
//	@TransCode("/power/updatePower")
	public Map<String, Object> updatePower(@RequestBody UpdatePowerDTO dto) throws Exception {
		service.updatePower(dto);
		return ResponseUtils.response(GlobalValue.SUCCESS, null);
	}
	
	@PostMapping("/addPower")
//	@TransCode("/power/addPower")
	public Map<String, Object> addPower(@RequestBody AddPowerDTO dto) throws Exception {
		service.addPower(dto);
		return ResponseUtils.response(GlobalValue.SUCCESS, null);
	}
	
	@PostMapping("/deletePower")
//	@TransCode("/power/deletePower")
	public Map<String, Object> deletePower(@RequestBody DeletePowerDTO dto) throws Exception {
		service.deletePower(dto);
		return ResponseUtils.response(GlobalValue.SUCCESS, null);
	}
}
