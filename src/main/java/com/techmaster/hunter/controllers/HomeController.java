package com.techmaster.hunter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/") 
public class HomeController extends HunterBaseController{

	@RequestMapping(value="/", method=RequestMethod.GET )
	public String goHome(){
		return "redirect:/hunter/login/after";
	}
	
}
