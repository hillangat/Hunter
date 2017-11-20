package com.techmaster.hunter.enums;

public enum TaskBusinessEmailEnums {
	
	PreTaskProcesNotification("",""); 
	
	private final String templateName;
	private final String description;
	
	TaskBusinessEmailEnums(String templateName, String description){
		this.templateName = templateName;
		this.description = description;
	}
	
	public String getTemplateName() {
		return templateName;
	}
	public String getDescription() {
		return description;
	}

}
