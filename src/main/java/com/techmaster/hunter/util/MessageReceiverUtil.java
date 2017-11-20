package com.techmaster.hunter.util;

import com.techmaster.hunter.obj.beans.ReceiverRegion;

public class MessageReceiverUtil {
	
	private static MessageReceiverUtil instance;

	private MessageReceiverUtil() {
		super();
	}
	
	static{
		if(instance == null){
			synchronized (MessageReceiverUtil.class) {
				instance = new MessageReceiverUtil();
			}
		}
	}
	
	public static MessageReceiverUtil getInstance(){
		return instance;
	}
	
	public static boolean validatePhoneNumber(String phoneNumber, ReceiverRegion receiverRegion){
		
		return false;
	}
	
	public static boolean getCodeForRegions(ReceiverRegion receiverRegion){
	
		return true;
	}
	
	public static void main(String[] args) {
		
		 
	}
	

}
