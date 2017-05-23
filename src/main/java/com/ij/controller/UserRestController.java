package com.ij.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ij.model.Driver;
import com.ij.model.service.DriverRepository;
import com.ij.security.DriverException;

@RestController
@RequestMapping("/drivers")
public class UserRestController {

	
    private DriverRepository driverRepository;

    public UserRestController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }
	
	@GetMapping("user/{userId}")
    public ResponseEntity<Driver> getByUserId(@PathVariable("userId") String userId) throws DriverException{
        
		Driver driver = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        for(GrantedAuthority a : auth.getAuthorities())
        {
        	if(userId.equals(auth.getName()) || a.getAuthority().equals("ADMIN"))
        	{
        		driver = this.driverRepository.findByUserId(userId);
        		break;
        	}
        }
        
        if(driver == null)
        {
        	 throw new DriverException("You are not authorised to access the request resource");
        }
        
        return new ResponseEntity<Driver>(driver, HttpStatus.OK);
    }	
}
