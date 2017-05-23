package com.ij.security;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(DriverException.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {

		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.UNAUTHORIZED.value());
		error.setMessage("You are not authorised to access the requested resource");

		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}

}
