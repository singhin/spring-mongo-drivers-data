package com.ij.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ij.model.Driver;
import com.ij.model.service.DriverRepository;

/**
 * 
 * @author Indy
 *
 */
@RestController
@RequestMapping("/drivers/user")
public class UserRestController {

	
    private DriverRepository driverRepository;

    public UserRestController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
    
	@RequestMapping("/resource")
	public Map<String, Object> sayHello() {
		Map<String, Object> map = new HashMap<>();
		map.put("message", "Hello User");
		map.put("timestamp", new Date().getTime());
		return map;
	}
	
	
    @GetMapping("/{userId}")
    public Driver getByUserId(@PathVariable("userId") String userId){
        Driver driver = this.driverRepository.findByUserId(userId);
        	
        return driver;
    }

}
