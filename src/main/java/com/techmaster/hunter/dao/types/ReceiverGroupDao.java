package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.json.ReceiverGroupDropDownJson;
import com.techmaster.hunter.json.ReceiverGroupJson;
import com.techmaster.hunter.obj.beans.ReceiverGroup;

public interface ReceiverGroupDao {
	
	public abstract void insertReceiverGroup(ReceiverGroup receiverGroup);
	public ReceiverGroup getReceiverGroupById(Long groupId);
	public List<ReceiverGroup> getAllReceiverGroups();
	public List<ReceiverGroup> getAllRcvrGrpForUsrNam(String userName);
	public String deleteGroup(ReceiverGroup receiverGroup);
	public void updateGroup(ReceiverGroup update);
	public List<ReceiverGroupJson> getAllGrouspJson();
	public String getGroupImportBeansDetails(Long groupId);
	public List<ReceiverGroupDropDownJson> getAllRcvrGrpDrpDwnJsnForMsgTyp(String messageType);
	public String getGroupNameById(Long groupId);
	 

}
