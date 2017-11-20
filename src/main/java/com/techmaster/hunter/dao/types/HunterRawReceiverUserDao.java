package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.HunterRawReceiverUser;

public interface HunterRawReceiverUserDao {
	
	public void insertRawUser(HunterRawReceiverUser rawReceiverUser);
	public void deleteRawUserByUserName(HunterRawReceiverUser rawReceiverUser);
	public void updateRawUser(HunterRawReceiverUser rawReceiverUser);
	public HunterRawReceiverUser getRawUserByUserName(String userName);
	public HunterRawReceiverUser getRawUserByID(Long userId);
	public HunterRawReceiverUser getDefaultRawUser();
	public List<HunterRawReceiverUser> getAllRawReceiverUsers();

}
