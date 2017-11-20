package com.techmaster.hunter.enums;

public enum TaskLifeStatusEnums {
	
	
	DRAFT("Draft","Draft"),
	REVIEW("Review","Review"),
	APPROVED("Approved","Approved"),
	PROCESSED("Processed","Processed");
	
	private final String name;
	private final String code;
	
	TaskLifeStatusEnums(String name,String code){
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return code;
	}

}
