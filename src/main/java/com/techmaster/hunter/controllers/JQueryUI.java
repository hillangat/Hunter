package com.techmaster.hunter.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/jquery")
public class JQueryUI {
	
	@RequestMapping(value="/home", method=RequestMethod.GET)
	public String home(){
		return "views/jqueryHome";
	}

}
