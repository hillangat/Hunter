package com.techmaster.hunter.json;

public class ReceiverGroupDropDownJson {

	private Long groupId;
	private String text;
	
	public ReceiverGroupDropDownJson() {
		super();
	}

	public ReceiverGroupDropDownJson(Long groupId, String text) {
		super();
		this.groupId = groupId;
		this.text = text;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((groupId == null) ? 0 : groupId.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		ReceiverGroupDropDownJson other = (ReceiverGroupDropDownJson) obj;
		if (groupId == null) {
			if (other.groupId != null)
				return false;
		} else if (!groupId.equals(other.groupId))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReceiverGroupDropDownJson [groupId=" + groupId + ", text="
				+ text + "]";
	}
	
	
	
	
}
