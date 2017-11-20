package com.techmaster.hunter.email;

import org.springframework.beans.factory.annotation.Autowired;

public class HunterEmailSenderImpl implements HunterEmailSender{
	
	@Autowired private TaskEmailManagerImpl taskEmailManager;
	
}
