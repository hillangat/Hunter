package com.techmaster.hunter.enums;

public enum TaskDeliveryStatusEnums {

	CONCPETUAL("Conceptual","Not started"),
	PARTIAL("Partial","Some task messages failed"),
	FAILED("Failed","Task processing failed"),
	PENDING("Pending","Pending task processing"),
	PROCESSED("Processed","Successfully processed");
	
	private final String name;
	private final String description;
	
	TaskDeliveryStatusEnums(String name,String description){
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	
	
}
