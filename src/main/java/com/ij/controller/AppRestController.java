package com.ij.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * @author Indy
 *
 */
@RestController
public class AppRestController {

	@RequestMapping("/user")
	public Principal sayHello(Principal principal) {
		return principal;
	}

}
