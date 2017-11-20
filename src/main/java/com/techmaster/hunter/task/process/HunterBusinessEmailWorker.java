package com.techmaster.hunter.task.process;

import java.util.Map;

import org.apache.log4j.Logger;

import com.techmaster.hunter.email.HunterBusinessEmailService;
import com.techmaster.hunter.util.HunterUtility;

public class HunterBusinessEmailWorker extends Thread {
	
	private String mailType;
	private String templateName;
	private Map<String, Object> cntntParams;
	private Logger logger = Logger.getLogger(getClass());
	
	public HunterBusinessEmailWorker(String mailType, String templateName, Map<String, Object> cntntParams) {
		super();
		this.mailType = mailType;
		this.templateName = templateName;
		this.cntntParams = cntntParams;
	}

	@Override
	public void run() {
		logger.debug("Sending task business email...");
		logger.debug("Mail type : " + mailType + ", template name : " + templateName + ", content params : " + HunterUtility.stringifyMap(cntntParams));  
		HunterBusinessEmailService.getInstance().send(mailType, cntntParams, templateName);	
		logger.debug("Finished sending task business email!!");
	}
}
