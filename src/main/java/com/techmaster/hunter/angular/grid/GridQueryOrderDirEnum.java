package com.techmaster.hunter.angular.grid;

public enum GridQueryOrderDirEnum {
	
	asc( "asc", "Ascending" ),
	desc( "desc", "Descending" );
	
	private GridQueryOrderDirEnum( String name, String description ) {
		this.name= name;
		this.description = description;
	}
	
	private String name;
	private String description;
	
	
	public static GridQueryOrderDirEnum getEnumForName( String name ) {
		for( GridQueryOrderDirEnum en : values() ) {
			if ( en.getName().equals(name) ) {
				return en;
			}
		}
		return null;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
