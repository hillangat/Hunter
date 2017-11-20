package com.techmaster.hunter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/email")
public class EmailTemplateViewController  extends HunterBaseController{
	
	@RequestMapping(value="/template/action/home")
	private String goHome(){
		return "views/emailTemplateView";
	}

}
