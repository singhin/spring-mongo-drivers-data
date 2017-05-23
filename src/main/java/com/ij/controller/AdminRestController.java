package com.ij.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("/admin")
public class AdminRestController {

	private DriverRepository driverRepository;

	public AdminRestController(DriverRepository driverRepository) {
		this.driverRepository = driverRepository;
	}

	@GetMapping("username/{username}")
    public ResponseEntity<Driver> getByUsername(@PathVariable("username") String username) throws DriverException{
        
		Driver driver = null;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        
        for(GrantedAuthority a : auth.getAuthorities())
        {
        	if(a.getAuthority().equals("ADMIN"))
        	{
        		driver = this.driverRepository.findByUsername(username);
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
