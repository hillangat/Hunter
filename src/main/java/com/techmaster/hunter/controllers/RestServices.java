package com.techmaster.hunter.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.techmaster.hunter.util.HunterUtility;

@Controller(value="/restservices")
public class RestServices {

	@Produces("application/json")
	@Consumes("application/json") 
	@RequestMapping( value="/client/tasks/tasks", method=RequestMethod.POST )
	public String getClientTasks( HttpServletRequest request ){
		return HunterUtility.setJSONObjectForSuccess(null, "Succeeded!!").toString();
	}
	
}
