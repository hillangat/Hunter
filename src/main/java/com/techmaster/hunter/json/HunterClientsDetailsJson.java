package com.techmaster.hunter.json;

public class HunterClientsDetailsJson {
	
	private String firstName;
	private String lastName;
	private String userName;
	private Long clientId;
	private String clientText;
	private String fullName;
	
	public HunterClientsDetailsJson() {
		super();
	}
	

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Long getClientId() {
		return clientId;
	}
	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}
	public String getClientText() {
		return clientText;
	}
	public void setClientText(String clientText) {
		this.clientText = clientText;
	}
	public String getFullName() {
		return firstName + " " + lastName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result
				+ ((clientText == null) ? 0 : clientText.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((fullName == null) ? 0 : fullName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((userName == null) ? 0 : userName.hashCode());
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
		HunterClientsDetailsJson other = (HunterClientsDetailsJson) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (clientText == null) {
			if (other.clientText != null)
				return false;
		} else if (!clientText.equals(other.clientText))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (fullName == null) {
			if (other.fullName != null)
				return false;
		} else if (!fullName.equals(other.fullName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "HunterClientsDetailsJson [firstName=" + firstName
				+ ", lastName=" + lastName + ", userName=" + userName
				+ ", clientId=" + clientId + ", clientText=" + clientText
				+ ", fullName=" + fullName + "]";
	}

	

	
	
	

}
