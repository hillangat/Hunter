package com.techmaster.hunter.angular.grid;

public class GridFieldUserInput {

	private String fieldName;
	private String dbName;
	private String fieldAlias;
    private String userInput;
    private GridQueryOperationEnum operation;
    private GridQueryOrderDirEnum dir;
	
    public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getUserInput() {
		return userInput;
	}
	public void setUserInput(String userInput) {
		this.userInput = userInput;
	}
	public GridQueryOperationEnum getOperation() {
		return operation;
	}
	public void setOperation(GridQueryOperationEnum operation) {
		this.operation = operation;
	}
	public GridQueryOrderDirEnum getDir() {
		return dir;
	}
	public void setDir(GridQueryOrderDirEnum dir) {
		this.dir = dir;
	}
	public String getFieldAlias() {
		return fieldAlias;
	}
	public void setFieldAlias(String fieldAlias) {
		this.fieldAlias = fieldAlias;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dbName == null) ? 0 : dbName.hashCode());
		result = prime * result + ((dir == null) ? 0 : dir.hashCode());
		result = prime * result + ((fieldAlias == null) ? 0 : fieldAlias.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((operation == null) ? 0 : operation.hashCode());
		result = prime * result + ((userInput == null) ? 0 : userInput.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GridFieldUserInput other = (GridFieldUserInput) obj;
		if (dbName == null) {
			if (other.dbName != null)
				return false;
		} else if (!dbName.equals(other.dbName))
			return false;
		if (dir != other.dir)
			return false;
		if (fieldAlias == null) {
			if (other.fieldAlias != null)
				return false;
		} else if (!fieldAlias.equals(other.fieldAlias))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (operation != other.operation)
			return false;
		if (userInput == null) {
			if (other.userInput != null)
				return false;
		} else if (!userInput.equals(other.userInput))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "GridFieldUserInput [fieldName=" + fieldName + ", dbName=" + dbName + ", fieldAlias=" + fieldAlias
				+ ", userInput=" + userInput + ", operation=" + operation + ", dir=" + dir + "]";
	}
	
    
}
