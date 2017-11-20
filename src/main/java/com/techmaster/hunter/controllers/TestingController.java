package com.techmaster.hunter.controllers;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techmaster.hunter.constants.HunterDaoConstants;
import com.techmaster.hunter.json.HunterUserJson;
import com.techmaster.hunter.util.HunterQueryToBeanMapper;
import com.techmaster.hunter.util.HunterUtility;

@Controller
@RequestMapping(value="/test")
public class TestingController extends HunterBaseController{
	
	private static Logger logger = Logger.getLogger(TestingController.class);
	
	@RequestMapping(value="/task/action/taskPage" )
	public String goToNewTaskPage(){
		return "taskTesting"; 
	}
	
	@RequestMapping(value="/task/action/newTextMsg" )
	public String getNewTextMsgView(){
		return "templates/taskTesting"; 
	}
	
	@RequestMapping(value="bootstrap/home" )
	public String jQueryUIHome(){
		List<HunterUserJson> hunterUserJsons = HunterQueryToBeanMapper.getInstance().map(HunterUserJson.class, HunterDaoConstants.GET_ALL_CLIENTS_DETAILS, null);
		logger.debug(HunterUtility.stringifyList(hunterUserJsons)); 
		return "bootstrap/home"; 
	}
	
	@RequestMapping(value="bootstrap/login" )
	public String login(){
		return "bootstrap/login"; 
	}
	
	@RequestMapping(value="bootstrap/demo" )
	public String demo(){
		return "bootstrap/demo"; 
	}
	
	@RequestMapping(value="bootstrap/dataTable" )
	public String dataTable(){
		return "bootstrap/dataTable";
	}
}
