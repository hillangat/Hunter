package com.techmaster.hunter.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterRawReceiverUserDao;
import com.techmaster.hunter.obj.beans.HunterRawReceiverUser;
import com.techmaster.hunter.util.HunterHibernateHelper;
import com.techmaster.hunter.util.HunterUtility;

public class HunterRawReceiverUserDaoImpl implements HunterRawReceiverUserDao {
	
	private Logger logger = Logger.getLogger(getClass());
	
	@Autowired private HunterHibernateHelper hunterHibernateHelper;

	@Override
	public void insertRawUser(HunterRawReceiverUser rawReceiverUser) {
		logger.debug("Inserting raw receiver user ... ( " + rawReceiverUser.getRawUserName() + " )");  
		hunterHibernateHelper.saveEntity(rawReceiverUser); 
		logger.debug("Finished inserting receiver user");
	}

	@Override
	public void deleteRawUserByUserName(HunterRawReceiverUser rawReceiverUser) {
		logger.debug("Deleting raw receiver user ... ( " + rawReceiverUser.getRawUserName() + " )");  
		hunterHibernateHelper.deleteEntity(rawReceiverUser);  
		logger.debug("Finished deleting receiver user");
	}

	@Override
	public void updateRawUser(HunterRawReceiverUser rawReceiverUser) {
		logger.debug("Updating raw receiver user ... ( " + rawReceiverUser.getRawUserName() + " )");  
		hunterHibernateHelper.updateEntity(rawReceiverUser);  
		logger.debug("Finished deleting receiver user");
	}

	@Override
	public HunterRawReceiverUser getRawUserByUserName(String userName) {
		String query = "FROM HunterRawReceiverUser h WHERE h.rawUserName = '" + userName + "'"; 
		List<HunterRawReceiverUser> rawReceiverUser = hunterHibernateHelper.executeQueryForObjList(HunterRawReceiverUser.class, query);
		if(rawReceiverUser == null || rawReceiverUser.isEmpty()) 
			return null;
		return rawReceiverUser.get(0); 
	}
	
	@Override
	public HunterRawReceiverUser getRawUserByID(Long userId) {
		String query = "FROM HunterRawReceiverUser h WHERE h.userId = '" + userId + "'"; 
		List<HunterRawReceiverUser> rawReceiverUser = hunterHibernateHelper.executeQueryForObjList(HunterRawReceiverUser.class, query);
		if(rawReceiverUser == null || rawReceiverUser.isEmpty()) 
			return null;
		return rawReceiverUser.get(0); 
	}

	@Override
	public HunterRawReceiverUser getDefaultRawUser() {
		HunterRawReceiverUser user = new HunterRawReceiverUser();
		user.setAllContctNo(0); 
		user.setAuditInfo(HunterUtility.getAuditInfoFromRequestForNow(null, "admin")); 
		user.setCompensation(0F);
		user.setConstituency("Bomet Central");
		user.setConsWard("Chesoen");
		user.setCountry("Kenya");
		user.setCounty("Bomet");
		user.setRawUserName("admin");
		user.setVillage(null);
		user.setVrfdContctNo(0);
		
		if(getAllRawReceiverUsers().isEmpty())
			insertRawUser(user); 
		
		return user;
	}

	@Override
	public List<HunterRawReceiverUser> getAllRawReceiverUsers() {
		List<HunterRawReceiverUser> users = hunterHibernateHelper.getAllEntities(HunterRawReceiverUser.class);
		if(users == null || users.isEmpty()){
			logger.debug("No raw receivers found!"); 
			return new ArrayList<>();
		}
		return users;
	}

	
	
}
