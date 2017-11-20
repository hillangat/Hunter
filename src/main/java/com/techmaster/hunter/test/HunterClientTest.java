package com.techmaster.hunter.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterAddressDaoImpl;
import com.techmaster.hunter.dao.impl.HunterClientDaoImpl;
import com.techmaster.hunter.dao.impl.HunterCreditCardDaoImpl;
import com.techmaster.hunter.dao.impl.HunterDaoFactory;
import com.techmaster.hunter.dao.impl.HunterMessageDaoHelper;
import com.techmaster.hunter.dao.impl.TaskMessageReceiverDaoImpl;
import com.techmaster.hunter.dao.impl.HunterUserDaoImpl;
import com.techmaster.hunter.dao.impl.ReceiverRegionDaoImpl;
import com.techmaster.hunter.dao.impl.TaskDaoImpl;
import com.techmaster.hunter.dao.types.HunterAddressDao;
import com.techmaster.hunter.dao.types.HunterClientDao;
import com.techmaster.hunter.dao.types.HunterCreditCardDao;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.dao.types.TaskDao;
import com.techmaster.hunter.obj.beans.HunterAddress;
import com.techmaster.hunter.obj.beans.HunterClient;
import com.techmaster.hunter.obj.beans.HunterCreditCard;
import com.techmaster.hunter.obj.beans.TaskMessageReceiver;
import com.techmaster.hunter.obj.beans.HunterUser;
import com.techmaster.hunter.obj.beans.ReceiverRegion;
import com.techmaster.hunter.obj.beans.ServiceProvider;
import com.techmaster.hunter.obj.beans.Task;
import com.techmaster.hunter.obj.beans.TextMessage;

public class HunterClientTest {
	
	public static void main(String[] args) {
		
		HunterClientDao clientDao = new HunterClientDaoImpl();
		Long nextClientId = clientDao.nextClientId();

		HunterUserDao hunterUserDao = new HunterUserDaoImpl();
		Long nextUserId = hunterUserDao.getNextUserId();
		
		HunterAddressDao hunterAddressDao = new HunterAddressDaoImpl();
		Long nextAddressId = hunterAddressDao.getNextAddressId();
		
		HunterCreditCardDao hunterCreditCardDao = new HunterCreditCardDaoImpl();
		Long nextCreditCardId = hunterCreditCardDao.getNextCreditCardId();
		
		TaskDao taskDao = new TaskDaoImpl();
		Long nextTaskId = taskDao.getNextTaskId();
		
		for(int i=0; i<=1; i++){
		
		HunterClient client = new HunterClient();
		client.setClientTotalBudget(450000);
		client.setReceiver(true);
		client.setClientId(nextClientId); 
		client.setCreatedBy("hlangat01");
		client.setCretDate(new Date()); 
		client.setLastUpdate(new Date());
		client.setLastUpdatedBy("hlangat01"); 
		nextClientId++;
		
		HunterUser clientUser = new HunterUser();
		clientUser.setUserId(nextUserId); 
		nextUserId++;
		clientUser.setFirstName("Sonkoff");
		clientUser.setLastName("Mbuvi");
		clientUser.setMiddleName("S");
		clientUser.setEmail("sonko@sonkoreskuteam.com"); 
		clientUser.setCretDate(new Date()); 
		clientUser.setLastUpdate(new Date()); 
		clientUser.setLastUpdatedBy("hlangat01");
		clientUser.setCreatedBy("hlangat01"); 
		clientUser.setUserName("srkteam");
		clientUser.setPassword("897k..,89kLL");  
		clientUser.setUserType(HunterConstants.HUNTER_CLIENT_USER); 
		clientUser.setPhoneNumber(HunterConstants.HUNTER_DEFAULT_PHONE_NUMBER); 
		
		client.setUser(clientUser); 
		
		HunterAddress address = new HunterAddress();
		address.setId(nextAddressId);
		address.setUserId(client.getUser().getUserId()); 
		nextAddressId++;
		address.setAptNo("H");
		address.setCity("Somerset"); 
		address.setCountry("USA"); 
		address.setState("New Jersey");
		address.setStreetName("34 Reler Lane");
		address.setType(HunterConstants.ADDRESS_TYPE_BILLING);
		address.setZip("00873");
		
		HunterAddress address1 = new HunterAddress();
		address1.setId(nextAddressId);
		address1.setUserId(client.getUser().getUserId()); 
		nextAddressId++;
		address1.setAptNo("H");
		address1.setCity("Somerset"); 
		address1.setCountry("USA"); 
		address1.setState("New Jersey");
		address1.setStreetName("34 Reler Lane1");
		address1.setType(HunterConstants.ADDRESS_TYPE_BILLING);
		address1.setZip("00873");
		
		Set<HunterAddress> addresses = new HashSet<>();
		addresses.add(address);
		addresses.add(address1);
		
		clientUser.setAddresses(addresses); 
		
		
		HunterCreditCard creditCard = new HunterCreditCard();
		creditCard.setCardId(nextCreditCardId);
		nextCreditCardId++;
		creditCard.setUserId(client.getUser().getUserId());
		creditCard.setCardNumber("256989FFFFFFFFFFFFFFFF65896ffff"); 
		creditCard.setCardType(HunterConstants.CARD_TYPE_VISA);
		creditCard.setExpirationDate(new Date());
		creditCard.setNameOnCard("Kip Langat");
		creditCard.setSecurityNumber("456"); 
		creditCard.setSelForBilling(true); 
		creditCard.setValid(true);
		
		HunterCreditCard creditCard1 = new HunterCreditCard();
		creditCard1.setUserId(client.getUser().getUserId());
		creditCard1.setCardId(nextCreditCardId); 
		nextCreditCardId++;
		creditCard1.setCardNumber("256FFFFFFFFFFFFFFFFF98965896fff"); 
		creditCard1.setCardType(HunterConstants.CARD_TYPE_VISA);
		creditCard1.setExpirationDate(new Date());
		creditCard1.setNameOnCard("Kip Langat");
		creditCard1.setSecurityNumber("456"); 
		creditCard1.setSelForBilling(true); 
		creditCard1.setValid(true); 
		
		Set<HunterCreditCard> creditCards = new HashSet<>();
		creditCards.add(creditCard);
		creditCards.add(creditCard1);
		
		clientUser.setCreditCards(creditCards); 
		
		
		Task task = new Task();
		task.setTaskId(nextTaskId); 
		nextTaskId++;
		task.setClientId(client.getClientId()); 
		task.setCretDate(new Date());
		task.setDescription("Testing task");
		task.setLastUpdate(new Date());
		task.setRecurrentTask(false); 
		task.setTaskApproved(false); 
		task.setTaskBudget(450000); 
		task.setTaskCost(500000);
		task.setTaskDateline(new Date());
		task.setTaskName("Kamugunji Compaign"); 
		task.setTaskObjective("Send message to 1000 Kamukunji residents on 10th Octover 2015 about the compaign scheduled for Octover first."); 
		task.setTaskType(HunterConstants.TASK_TYPE_POLITICAL); 
		task.setUpdatedBy("hlangat01");
		task.setCreatedBy("hlangat01");
		task.setTaskLifeStatus(HunterConstants.STATUS_DRAFT); 
		
		Long nextTxtMsgId = HunterDaoFactory.getDaoObject(HunterMessageDaoHelper.class).getNextMessageId(TextMessage.class);
		
		TextMessage message = new TextMessage();
		message.setMsgId(nextTxtMsgId);
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
		message.setMsgLifeStatus(HunterConstants.STATUS_DRAFT); 
		message.setMsgSendDate(new Date()); 
		message.setMsgTaskType(HunterConstants.TASK_TYPE_POLITICAL); 
		message.setMsgText("Hi! Text message testing!"); 
		message.setPageable(true); 
		message.setPageWordCount(message.getMsgText().length()); 
		message.setProvider(ServiceProvider.getHunterDefaultServiceProvider());
		message.setText(message.getMsgText()); 
		message.setToPhone("2547264704894");
		message.setMsgOwner(client.getUser().getUserName());  
		
		task.setTaskMessage(message);
		
		
		Long receiverRegionId = new ReceiverRegionDaoImpl().getNextReceiverRegionId();
		
		ReceiverRegion receiverRegion = new ReceiverRegion();
		receiverRegion.setRegionId(receiverRegionId); 
		receiverRegion.setCity("Bomet");
		receiverRegion.setConstituency("Bomet");
		receiverRegion.setCountry("Kenya");
		receiverRegion.setCounty("Bomet");
		receiverRegion.setCurrentLevel(HunterConstants.RECEIVER_LEVEL_COUNTY); 
		receiverRegion.setHasState(false);
		receiverRegion.setLatitude(-5.365);
		receiverRegion.setLongitude(-15.365);
		receiverRegion.setState(null);
		receiverRegion.setVillage(null);
		receiverRegion.setWard(null); 
		
		Long nextReiverId = new TaskMessageReceiverDaoImpl().getNextHunterMessageReceiver();
		
		TaskMessageReceiver receiver1 = new TaskMessageReceiver();
		receiver1.setReceiverId(nextReiverId);
		nextReiverId++;
		receiver1.setTaskId(task.getTaskId());
		receiver1.setReceiverContact("12569865458781");
		receiver1.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_COUNTY);
		receiver1.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT); 
		
		TaskMessageReceiver receiver2 = new TaskMessageReceiver();
		receiver2.setTaskId(task.getTaskId());
		receiver2.setReceiverId(nextReiverId);
		nextReiverId++;
		receiver2.setReceiverContact("12569865458782");
		receiver2.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_COUNTY);
		receiver2.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT);
		
		TaskMessageReceiver receiver3 = new TaskMessageReceiver();
		receiver3.setReceiverId(nextReiverId);
		receiver3.setTaskId(task.getTaskId());
		nextReiverId++;
		receiver3.setReceiverContact("12569865458783");
		receiver3.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_COUNTY);
		receiver3.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT);
		
		
		TaskMessageReceiver receiver4 = new TaskMessageReceiver();
		receiver4.setTaskId(task.getTaskId());
		receiver4.setReceiverId(nextReiverId);
		receiver4.setReceiverContact("1256986545878");
		receiver4.setReceiverRegionLevel(HunterConstants.RECEIVER_LEVEL_COUNTY);
		receiver4.setReceiverType(HunterConstants.RECEIVER_TYPE_TEXT);
		
		Set<TaskMessageReceiver> receivers = new HashSet<>();
		receivers.add(receiver1);
		receivers.add(receiver2);
		receivers.add(receiver3);
		
		Set<Task> tasks = new HashSet<>();
		tasks.add(task);
		client.setTasks(tasks);
		
		
		clientDao.insertHunterClient(client);  
		

		}
	}

}
