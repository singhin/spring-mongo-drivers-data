package com.ij.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ij.model.Driver;
import com.ij.model.service.DriverRepository;

@RestController
@RequestMapping("/drivers/admin")
public class AdminRestController {

	private DriverRepository driverRepository;

	public AdminRestController(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	@RequestMapping("/resource")
	public Map<String, Object> sayHello() {
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Hello Administrator");
		map.put("timestamp", new Date().getTime());
		return map;
	}
	
	
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("")
    public List<Driver> getAllUsers(){
        List<Driver> drivers = this.driverRepository.findAll();
        	
        return drivers;
    }

}
