package com.techmaster.hunter.exception;

public class HunterRemoteException extends Exception {
	
	
	private static final long serialVersionUID = -7873556399183752402L;
	
	String message;
	
	public HunterRemoteException(String message){
		this.message = message;
	}
	public String getMessage() {
		return message;
	}

}
