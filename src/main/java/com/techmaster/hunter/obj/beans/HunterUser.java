package com.techmaster.hunter.obj.beans;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


public class HunterUser implements Serializable{
	
	private static final long serialVersionUID = -1084402280991050344L;
	
	private Long userId;
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private String phoneNumber;
	private String userType;
	private String userName;
	private String password;
	private boolean active;
	private byte[] profilePhoto;
	
	private java.util.Date cretDate;
	private java.util.Date lastUpdate;
	private String createdBy;
	private String lastUpdatedBy;
	
	private Set<UserRole> userRoles = new HashSet<>(); 
	private Set<HunterAddress> addresses;
	private Set<HunterCreditCard> creditCards;
	private UserLoginBean userLoginBean;
	private UserProfPhoto userProfPhoto;

	public HunterUser() {
		super();
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
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
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public byte[] getProfilePhoto() {
		return profilePhoto;
	}

	public void setProfilePhoto(byte[] profilePhoto) {
		this.profilePhoto = profilePhoto;
	}
	public Set<HunterAddress> getAddresses() {
		return addresses;
	}
	public void setAddresses(Set<HunterAddress> addresses) {
		this.addresses = addresses;
	}
	public Set<HunterCreditCard> getCreditCards() {
		return creditCards;
	}
	public void setCreditCards(Set<HunterCreditCard> creditCards) {
		this.creditCards = creditCards;
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Set<UserRole> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	public UserLoginBean getUserLoginBean() {
		return userLoginBean;
	}
	public void setUserLoginBean(UserLoginBean userLoginBean) {
		this.userLoginBean = userLoginBean;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public UserProfPhoto getUserProfPhoto() {
		return userProfPhoto;
	}
	public void setUserProfPhoto(UserProfPhoto userProfPhoto) {
		this.userProfPhoto = userProfPhoto;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		HunterUser other = (HunterUser) obj;
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
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
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
		return "HunterUser [userId=" + userId + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", middleName=" + middleName
				+ ", email=" + email + ", phoneNumber=" + phoneNumber
				+ ", userType=" + userType + ", userName=" + userName
				+ ", password=" + password + ", active=" + active
				+ ", profilePhoto=" + Arrays.toString(profilePhoto)
				+ ", cretDate=" + cretDate + ", lastUpdate=" + lastUpdate
				+ ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + ", userRoles=" + userRoles + ", addresses="
				+ addresses + ", creditCards=" + creditCards
				+ ", userLoginBean=" + userLoginBean + ", userProfPhoto="
				+ userProfPhoto + "]";
	}

	
	
	
	
	

}
