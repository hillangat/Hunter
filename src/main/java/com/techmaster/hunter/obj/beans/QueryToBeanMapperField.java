package com.techmaster.hunter.obj.beans;

public class QueryToBeanMapperField {
	
	private String parentId;
	private String dbField;
	private String fieldName;
	private boolean yesNo;
	private String type;
	
	public QueryToBeanMapperField(String parentId, String dbField, String fieldName, boolean yesNo, String type) {
		super();
		this.parentId = parentId;
		this.dbField = dbField;
		this.fieldName = fieldName;
		this.yesNo = yesNo;
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getDbField() {
		return dbField;
	}

	public void setDbField(String dbField) {
		this.dbField = dbField;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public boolean isYesNo() {
		return yesNo;
	}

	public void setYesNo(boolean yesNo) {
		this.yesNo = yesNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dbField == null) ? 0 : dbField.hashCode());
		result = prime * result + ((fieldName == null) ? 0 : fieldName.hashCode());
		result = prime * result + ((parentId == null) ? 0 : parentId.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + (yesNo ? 1231 : 1237);
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
		QueryToBeanMapperField other = (QueryToBeanMapperField) obj;
		if (dbField == null) {
			if (other.dbField != null)
				return false;
		} else if (!dbField.equals(other.dbField))
			return false;
		if (fieldName == null) {
			if (other.fieldName != null)
				return false;
		} else if (!fieldName.equals(other.fieldName))
			return false;
		if (parentId == null) {
			if (other.parentId != null)
				return false;
		} else if (!parentId.equals(other.parentId))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (yesNo != other.yesNo)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "QueryToBeanMapperField [parentId=" + parentId + ", dbField=" + dbField + ", fieldName=" + fieldName
				+ ", yesNo=" + yesNo + ", type=" + type + "]";
	}

	
	
	

}
