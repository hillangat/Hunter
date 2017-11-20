package com.techmaster.hunter.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.techmaster.hunter.constants.HunterConstants;
import com.techmaster.hunter.dao.impl.HunterUserDaoImpl;
import com.techmaster.hunter.dao.types.HunterUserDao;
import com.techmaster.hunter.obj.beans.HunterAddress;
import com.techmaster.hunter.obj.beans.HunterCreditCard;
import com.techmaster.hunter.obj.beans.HunterUser;

public class HunterUserTest {
	
	public static void main(String[] args) {
		
		
		HunterUserDao userDao = new HunterUserDaoImpl();
		
		for(int i=0; i<10; i++){
		
		HunterUser hUser = new HunterUser();
		hUser.setFirstName("Sonkoff");
		hUser.setLastName("Mbuvi");
		hUser.setMiddleName("S");
		hUser.setEmail("sonko@sonkoreskuteam.com"); 
		hUser.setCretDate(new Date()); 
		hUser.setLastUpdate(new Date()); 
		hUser.setLastUpdatedBy("hlangat01");
		hUser.setCreatedBy("hlangat01"); 
		hUser.setUserName("srkteam");
		hUser.setPassword("897k..,89kLL");  
		hUser.setUserType(HunterConstants.HUNTER_CLIENT_USER); 
		
		
		HunterAddress address = new HunterAddress();
		address.setAptNo("H");
		address.setCity("Somerset"); 
		address.setCountry("USA"); 
		address.setState("New Jersey");
		address.setStreetName("34 Reler Lane");
		address.setType(HunterConstants.ADDRESS_TYPE_BILLING);
		address.setZip("00873");
		
		HunterAddress address1 = new HunterAddress();
		address1.setAptNo("H1");
		address1.setCity("Somerset"); 
		address1.setCountry("USA"); 
		address1.setState("New Jersey");
		address1.setStreetName("34 Reler Lane1");
		address1.setType(HunterConstants.ADDRESS_TYPE_SHIPPING);
		address1.setZip("00873");
		
		Set<HunterAddress> addresses = new HashSet<>();
		addresses.add(address);
		addresses.add(address1);
		
		hUser.setAddresses(addresses);
		
		HunterCreditCard creditCard = new HunterCreditCard();
		creditCard.setCardNumber("25698965896ffff"); 
		creditCard.setCardType(HunterConstants.CARD_TYPE_VISA);
		creditCard.setExpirationDate(new Date());
		creditCard.setNameOnCard("Kip Langat");
		creditCard.setSecurityNumber("456"); 
		creditCard.setSelForBilling(true); 
		creditCard.setValid(true);
		
		HunterCreditCard creditCard1 = new HunterCreditCard();
		creditCard1.setCardNumber("25698965896fff"); 
		creditCard1.setCardType(HunterConstants.CARD_TYPE_VISA);
		creditCard1.setExpirationDate(new Date());
		creditCard1.setNameOnCard("Kip Langat");
		creditCard1.setSecurityNumber("456"); 
		creditCard1.setSelForBilling(true); 
		creditCard1.setValid(true); 
		
		Set<HunterCreditCard> creditCards = new HashSet<>();
		creditCards.add(creditCard);
		creditCards.add(creditCard1);
		
		hUser.setCreditCards(creditCards); 
		
		userDao.insertHunterUser(hUser); 
		
		}
	}

}
