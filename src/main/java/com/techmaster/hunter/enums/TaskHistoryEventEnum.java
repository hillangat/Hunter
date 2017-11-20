package com.techmaster.hunter.enums;

public enum TaskHistoryEventEnum {
	
	CREATE("Create", "Create Task"),
	CLONE("Clone", "Clone Task"),
	UPDATE("Update", "Update Task"),
	DELETE("Delete", "Delete Task"),
	STATUS_CHANGE("Change Status", "Change Task status"),
	DUPLICATE("Duplicate", "Duplicate Task"),
	ADD_GROUP("Add Group", "Add group to Task"),
	REMOVE_GROUP("Remove Group", "Remove group from Task"),
	ADD_REGION("Add Region", "Add region to Task"),
	REMOVE_REGION("Remove Region", "Remove region from Task"),
	DELETE_MESSAGE("Delete Message", "Delete Message from Task"),
	ADD_MESSAGE("Add Message", "Add Message to Task"),
	PRE_EMPT("Preempt", "Preempt Task Process"),
	VALIDATE_PROCESS("Validate Process", "Validate Task Process"),
	PROCESS("Process", "Process Task"),
	UNCOMPLETE("Uncomplete", "Uncomplete Task"),
	DELETE_SCL_IMG("Delete Social Image", "Delete Social Message Media Image");
	
	private final String eventName;
	private final String eventDesc;
	
	TaskHistoryEventEnum(String eventName, String eventDescription){
		this.eventName = eventName;
		this.eventDesc = eventDescription;
	}

	public String getEventName() {
		return eventName;
	}
	public String getEventDesc() {
		return eventDesc;
	}

	public String toString(){
		return "[ eventName = " + eventName + " eventDesc = "+ eventDesc + "]";
	}
	
}
