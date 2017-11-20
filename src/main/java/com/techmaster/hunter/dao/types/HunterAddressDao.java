package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.HunterAddress;


public interface HunterAddressDao {
	
	public void insertAddress(HunterAddress address);
	public HunterAddress getAddressById(Long id);
	public void deleteAddress(HunterAddress address);
	public List<HunterAddress> getAllAddresses();
	public void deleteAddressById(Long id);
	public void updateAddress(HunterAddress address);
	public void insertAddresses(List<HunterAddress> addresses);
	public Long getNextAddressId();
	public List<HunterAddress> getAddressesByUserId(Long userId);

}
