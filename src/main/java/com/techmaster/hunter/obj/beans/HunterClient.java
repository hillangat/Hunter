package com.techmaster.hunter.obj.beans;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
public class HunterClient implements Serializable{

	private static final long serialVersionUID = -5141167169110688014L;
	
	private Long clientId;
	private float clientTotalBudget;
	private boolean receiver;
	private Set<Task> tasks = new HashSet<>(); 
	private HunterUser user;
	
	private java.util.Date cretDate;
	private java.util.Date lastUpdate;
	private String createdBy;
	private String lastUpdatedBy;
	
	private String firstName;
	private String lastName;
	private String email;
	private String userName;
	
	public HunterClient() {
		super();
	}

	

	public Long getClientId() {
		return clientId;
	}



	public void setClientId(Long clientId) {
		this.clientId = clientId;
	}



	public float getClientTotalBudget() {
		return clientTotalBudget;
	}



	public void setClientTotalBudget(float clientTotalBudget) {
		this.clientTotalBudget = clientTotalBudget;
	}



	public boolean isReceiver() {
		return receiver;
	}



	public void setReceiver(boolean receiver) {
		this.receiver = receiver;
	}



	public Set<Task> getTasks() {
		return tasks;
	}



	public void setTasks(Set<Task> tasks) {
		this.tasks = tasks;
	}



	public HunterUser getUser() {
		return user;
	}



	public void setUser(HunterUser user) {
		this.user = user;
	}



	public java.util.Date getCretDate() {
		return cretDate;
	}



	public void setCretDate(java.util.Date cretDate) {
		this.cretDate = cretDate;
	}



	public java.util.Date getLastUpdate() {
		return lastUpdate;
	}



	public void setLastUpdate(java.util.Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}



	public String getCreatedBy() {
		return createdBy;
	}



	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}



	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}



	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clientId == null) ? 0 : clientId.hashCode());
		result = prime * result + Float.floatToIntBits(clientTotalBudget);
		result = prime * result + ((createdBy == null) ? 0 : createdBy.hashCode());
		result = prime * result + ((cretDate == null) ? 0 : cretDate.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((lastUpdate == null) ? 0 : lastUpdate.hashCode());
		result = prime * result + ((lastUpdatedBy == null) ? 0 : lastUpdatedBy.hashCode());
		result = prime * result + (receiver ? 1231 : 1237);
		result = prime * result + ((tasks == null) ? 0 : tasks.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
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
		HunterClient other = (HunterClient) obj;
		if (clientId == null) {
			if (other.clientId != null)
				return false;
		} else if (!clientId.equals(other.clientId))
			return false;
		if (Float.floatToIntBits(clientTotalBudget) != Float.floatToIntBits(other.clientTotalBudget))
			return false;
		if (createdBy == null) {
			if (other.createdBy != null)
				return false;
		} else if (!createdBy.equals(other.createdBy))
			return false;
		if (cretDate == null) {
			if (other.cretDate != null)
				return false;
		} else if (!cretDate.equals(other.cretDate))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (lastUpdate == null) {
			if (other.lastUpdate != null)
				return false;
		} else if (!lastUpdate.equals(other.lastUpdate))
			return false;
		if (lastUpdatedBy == null) {
			if (other.lastUpdatedBy != null)
				return false;
		} else if (!lastUpdatedBy.equals(other.lastUpdatedBy))
			return false;
		if (receiver != other.receiver)
			return false;
		if (tasks == null) {
			if (other.tasks != null)
				return false;
		} else if (!tasks.equals(other.tasks))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
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
		return "HunterClient [clientId=" + clientId + ", clientTotalBudget=" + clientTotalBudget + ", receiver="
				+ receiver + ", tasks=" + tasks + ", user=" + user + ", cretDate=" + cretDate + ", lastUpdate="
				+ lastUpdate + ", createdBy=" + createdBy + ", lastUpdatedBy=" + lastUpdatedBy + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", userName=" + userName + "]";
	}



	

		
	
	
}
