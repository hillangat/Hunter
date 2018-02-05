package com.techmaster.hunter.angular.grid;

public enum GridQueryOperationEnum {
	// gt,lt,equals,before,after,contains,begins,ends;	
	gt( "gt", "Greater Than", " > " ),
	lt( "lt", "Less Than", " < " ),
	equals( "equals", "Equals", " = " ),
	before( "before", "Before", " < " ),
	after( "after", "After", " > " ),
	contains( "contains", "Contains", " IN " ),
	begins( "begins", "Begins With", " LIKE " ),
	ends( "ends", "Ends With", " ENDS WITH " );
	
	private GridQueryOperationEnum(String uiName, String uiDesc, String dbName) {
		this.uiName = uiName;
		this.uiDesc = uiDesc;
		this.dbName = dbName;
	}
	
	private final String uiName;
	private final String uiDesc;
	private final String dbName;
	
	public String getUiName() {
		return uiName;
	}
	public String getUiDesc() {
		return uiDesc;
	}
	public String getDbName() {
		return dbName;
	}
	
	public static GridQueryOperationEnum getEnumForName( String uiName ) {
		for( GridQueryOperationEnum en : values() ) {
			if ( en.getUiName().equals(uiName) ) {
				return en;
			}
		}
		return null;
	}
	
	public static String getSqlFragment( String fieldAlias, String dbName, String val, String uiName ) {
		switch ( getEnumForName(uiName) ) {
			case lt: 		return fieldAlias + "." + dbName + " < " + val;
			case gt: 		return fieldAlias + "." + dbName + " > " + val;
			case equals: 	return fieldAlias + "." + dbName + " = " + val;
			case contains:	return fieldAlias + "." + dbName + " like '%" + val + "%'";
			case before: 	return fieldAlias + "." + dbName + " < " + val;
			case after: 	return fieldAlias + "." + dbName + " > " + val;
			case begins: 	return fieldAlias + "." + dbName + " like '" + val + "%'";
			case ends: 		return fieldAlias + "." + dbName +  " like " + "'%" + val + "'";
			default : 		return null;
		}
	}
	
	
}
