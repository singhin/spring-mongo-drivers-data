package com.ij.security;

public class DriverException extends Exception{


	private static final long serialVersionUID = -8943512819298529640L;

	private String errorMessage;
	
	public DriverException() {
		super();
	}

	public DriverException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}


	public String getErrorMessage() {
		return errorMessage;
	}
	
}
