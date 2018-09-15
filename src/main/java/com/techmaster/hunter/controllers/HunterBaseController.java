package com.techmaster.hunter.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.techmaster.hunter.obj.beans.AuditInfo;
import com.techmaster.hunter.util.HunterUtility;

public class HunterBaseController{
	
	protected String getUserName(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String userName = auth.getName();
		return userName == null ? "admin" : userName;
	}
	
	protected AuditInfo getAuditInfo(){
		return HunterUtility.getAuditInfoFromRequestForNow(null, getUserName());
	}
	
	protected Authentication getAuthentication(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		auth.getDetails();
		return auth;
	}

}
