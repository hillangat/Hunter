package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.HunterRawReceiver;
import com.techmaster.hunter.obj.beans.HunterRawReceiverUser;

public interface HunterRawReceiverDao {

	public void insertHunterRawReceiver(HunterRawReceiver hunterRawReceiver);
	public void insertHunterRawReceivers(List<HunterRawReceiver> hunterRawReceivers);
	public void deleteHunterRawReceiver(HunterRawReceiver hunterRawReceiver);
	public void deleteHunterRawReceiverById(Long rawReceiverId);
	public HunterRawReceiver getHunterRawReceiverById(Long rawReceiverId);
	public List<HunterRawReceiver> getAllHunterRawReceivers();
	public List<HunterRawReceiver> getAllHunterRawReceiversByRawUser(HunterRawReceiverUser hunterRawReceiverUser);
	public void updateHunterRawReceiver(HunterRawReceiver hunterRawReceiver);
	
	
}
