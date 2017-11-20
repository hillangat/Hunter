package com.techmaster.hunter.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value="/login")
public class LoginController extends HunterBaseController{
	
	private Logger logger = Logger.getLogger(getClass());
	
	@RequestMapping(value="/loginfailed", method=RequestMethod.GET)
	public String postLogin(){	
		return "access/loginfailed";
	}
	
	@RequestMapping(value="/expiredSession", method=RequestMethod.GET)
	public String expiredSession(){	
		return "access/loginfailed";
	}
	
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if(authentication != null){
			logger.debug("User was authenticated before. Logging out..."); 
			new SecurityContextLogoutHandler().logout(request, response, authentication);
			logger.debug("Done Logging out..."); 
		}else{
			logger.debug("User was NOT authenticated before. Returning...");
		}
		SecurityContextHolder.getContext().setAuthentication(null);
	     return "access/logout";
	}
	

	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String loginPage(){	
		return "access/login";
	}
	
	@RequestMapping(value="/postlogin", method=RequestMethod.GET)
	public String postLoginPage(){	
		return "access/postLogin";
	}
	
	




}
