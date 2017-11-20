package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.json.ReceiverGroupJson;

public interface ReceiverGroupJsonDao {
	
	public abstract void insertReceiverGroup(ReceiverGroupJson receiverGroup);
	public ReceiverGroupJson getReceiverGroupById(Long groupId);
	public List<ReceiverGroupJson> getAllReceiverGroups();
	public List<ReceiverGroupJson> getAllRcvrGrpForUsrNam(String userName);
	public String deleteGroup(ReceiverGroupJson receiverGroup);
	public void updateGroup(ReceiverGroupJson update);
	public List<ReceiverGroupJson> getAllGrouspJson();
	public String getGroupImportBeansDetails(Long groupId);

}
