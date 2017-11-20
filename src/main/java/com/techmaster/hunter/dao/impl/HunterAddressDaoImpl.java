package com.techmaster.hunter.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.techmaster.hunter.dao.types.HunterAddressDao;
import com.techmaster.hunter.obj.beans.HunterAddress;
import com.techmaster.hunter.util.HunterHibernateHelper;

public class HunterAddressDaoImpl implements HunterAddressDao{
	
	private final Logger logger = Logger.getLogger(getClass());
	
	private HunterHibernateHelper hunterHibernateHelper;

	public HunterHibernateHelper getHunterHibernateHelper() {
		return hunterHibernateHelper;
	}

	public void setHunterHibernateHelper(HunterHibernateHelper hunterHibernateHelper) {
		System.out.println(".......................................Setting hunte hibernate helper.................."); 
		this.hunterHibernateHelper = hunterHibernateHelper;
	}

	@Override
	public void insertAddress(HunterAddress address) {
		logger.debug("Inserting address...");
		hunterHibernateHelper.saveEntity(address);
		logger.debug("Finished inserting address"); 
	}

	@Override
	public HunterAddress getAddressById(Long id) {
		logger.debug("Getting address for id >> "+ id);
		HunterAddress address = hunterHibernateHelper.getEntityById(id, HunterAddress.class);
		logger.debug("Obtained addres >> " + address); 
		return address;
	}

	@Override
	public void deleteAddress(HunterAddress address) {
		logger.debug("Deleting address ... ");
		hunterHibernateHelper.deleteEntity(address);
		logger.debug("Sucessfully deleted address"); 
	}

	@Override
	public List<HunterAddress> getAllAddresses() {
		logger.debug("Fetching all hunter addresses"); 
		List<HunterAddress> addresses = hunterHibernateHelper.getAllEntities(HunterAddress.class);
		logger.debug("Successfully returned addresses size( " + addresses.size()+ " )"); 
		return addresses;
	}

	@Override
	public void deleteAddressById(Long id) {
		logger.debug("Deleting address for id >> " + id);
		HunterAddress hunterAddress = hunterHibernateHelper.getEntityById(id, HunterAddress.class);
		hunterHibernateHelper.deleteEntity(hunterAddress);
		logger.debug("Finished deleting addres by id");
	}

	@Override
	public void updateAddress(HunterAddress update) {
		logger.debug("Updating hunter address >> " + update);
		hunterHibernateHelper.updateEntity(update);
		logger.debug("Successfully updated hunter address!");
	}

	@Override
	public void insertAddresses(List<HunterAddress> addresses) {
		logger.debug("Inserting address...");
		hunterHibernateHelper.saveEntity(addresses);;
		logger.debug("Successfully inserted hunter address!"); 
	}

	@Override
	public Long getNextAddressId() {
		logger.debug("Obtaining next address id");
		Long maxId = hunterHibernateHelper.getMaxEntityIdAsNumber(HunterAddress.class, Long.class, "id");
		maxId = maxId == null ? 1 : maxId + 1;
		logger.debug("Successfully obtained next address Id >> " + maxId);
		return maxId;
	}

	@Override
	public List<HunterAddress> getAddressesByUserId(Long userId) {
		logger.debug("Fetching addresses for user id : " + userId + "..."); 
		String query = "FROM HunterAddress h WHERE h.userId = " + userId;
		List<HunterAddress> hunterAddresses = hunterHibernateHelper.executeQueryForObjList(HunterAddress.class, query);
		logger.debug("Finished fetching addresses ! Size ( " +( hunterAddresses != null ? hunterAddresses.size() : 0 ) + " )"); 
		return hunterAddresses;
	}

	
}
