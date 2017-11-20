package com.techmaster.hunter.test;

import java.util.Date;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.MessageDaoImpl;
import com.techmaster.hunter.dao.types.MessageDao;
import com.techmaster.hunter.obj.beans.ServiceProvider;
import com.techmaster.hunter.obj.beans.TextMessage;

public class MessageTest {
	
	public static void main(String[] args) {
		
		TextMessage message = new TextMessage();
		message.setActualReceivers(400);
		message.setConfirmedReceivers(350); 
		message.setCreatedBy("hlangat01");
		message.setCretDate(new Date()); 
		message.setDesiredReceivers(1000); 
		message.setDisclaimer("We cannot be prosecuted for this hate speech"); 
		message.setFromPhone(HunterConstants.HUNTER_DEFAULT_PHONE_NUMBER); 
		message.setLastUpdate(new Date()); 
		message.setLastUpdatedBy("hlangat01"); 
		message.setMsgDeliveryStatus(HunterConstants.STATUS_STARTED);
		message.setMsgId(1L); 
		message.setMsgLifeStatus(HunterConstants.STATUS_DRAFT); 
		message.setMsgSendDate(new Date()); 
		message.setMsgTaskType(HunterConstants.TASK_TYPE_POLITICAL); 
		message.setMsgText("Hi! Text message testing!"); 
		message.setPageable(true); 
		message.setPageWordCount(message.getMsgText().length()); 
		message.setProvider(ServiceProvider.getHunterDefaultServiceProvider());
		message.setText(message.getMsgText()); 
		message.setToPhone("2547264704894");
		 
		MessageDao messageDao = new MessageDaoImpl();
		messageDao.insertMessage(message);
		
		
	}

}
