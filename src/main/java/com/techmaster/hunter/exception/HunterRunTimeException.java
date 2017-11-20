package com.techmaster.hunter.exception;

public class HunterRunTimeException extends RuntimeException {
	
	private static final long serialVersionUID = -3020480503908180626L;
	
	String message = "";
	
	public HunterRunTimeException(String message) {
		super();
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

}
