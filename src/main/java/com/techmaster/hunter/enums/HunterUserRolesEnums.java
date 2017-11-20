package com.techmaster.hunter.enums;

public enum HunterUserRolesEnums {
	
	ROLE_ADMIN("ROLE_ADMIN","Admin", 1),
	ROLE_USER("ROLE_USER","User", 2),
	ROLE_EXT_APP("ROLE_EXT_APP","External Application", 3),
	ROLE_TASK_APPROVER("ROLE_TASK_APPROVER","Task Approver", 4),
	ROLE_TASK_PROCESSOR("ROLE_TASK_PROCESSOR","Task Processor", 5),
	HNTR_RW_MSG_RCVR_USR("ROLE_RAW_USER","Hunter Raw Message Receiver User", 6),
	HUNTR_EML_TMPLT_ROLE("ROLE_EML_TMPLT_ROLE_USER","Hunter Email Template Role", 7),
	HUNTR_ASSGNBL_USER_ROLE("HUNTR_ASSGNBL_USER_ROLE","Hunter Assignable User Role", 8);
	
	private final String name;
	private final String label;
	private final int level;
	
	
	private HunterUserRolesEnums(String name, String label, int level) {
		this.name = name;
		this.label = label;
		this.level = level;
	}
	
	public String getName() {
		return name;
	}
	public String getLabel() {
		return label;
	}
	public int getLevel() {
		return level;
	}

}
