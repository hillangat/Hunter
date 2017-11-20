package com.techmaster.hunter.test;

import java.util.Date;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.EmailMessageDaoImpl;
import com.techmaster.hunter.dao.types.EmailMessageDao;
import com.techmaster.hunter.obj.beans.EmailMessage;
import com.techmaster.hunter.util.HunterLogFactory;

public class EmailTest {
	
	private static Long msgId;
	
	public static void main(String[] args) {
		
		EmailMessageDao emailMessageDao = new EmailMessageDaoImpl();
		msgId = emailMessageDao.getNextEmailMessageId();
		HunterLogFactory.getLog(EmailTest.class).debug("next email message id >> " + msgId); 
		
		for(int i=0; i<10; i++){
			EmailMessage email = new EmailMessage();
			email.setActualReceivers(10);
			email.setCcList("hillangat@gmail.com");
			email.setConfirmedReceivers(5);
			email.setCreatedBy("hillangat01"); 
			email.setCretDate(new Date()); 
			email.setDesiredReceivers(10); 
			email.seteBody("Hey, what's up?"); 
			email.seteFooter("This message is from Arap Langat"); 
			email.seteFrom("hillangat@gmail.com");
			email.seteSubject("Techmasters sending email"); 
			email.setLastUpdate(new Date()); 
			email.setLastUpdatedBy("hillangat01"); 
			email.setMsgDeliveryStatus(HunterConstants.STATUS_STARTED); 
			email.setMsgId(msgId); 
			email.setMsgLifeStatus(HunterConstants.STATUS_DRAFT); 
			email.setMsgOwner("hillangat01"); 
			email.setMsgSendDate(new Date()); 
			email.setMsgTaskType(HunterConstants.TASK_TYPE_TESTING); 
			email.setMsgText("Tech Master Testing Sending Emails" );
			email.setProvider(null);
			email.setToList("hillangat@gmail.com"); 
			emailMessageDao.insertEmailMessage(email); 
			msgId++;
		}
		
		
		
		
		
		
	}

}
