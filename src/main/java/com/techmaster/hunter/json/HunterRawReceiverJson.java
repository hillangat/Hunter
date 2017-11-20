package com.techmaster.hunter.json;


public class HunterRawReceiverJson {

	private long rawReceiverId;
	private String receiverContact;
	private String receiverType;
	private String firstName;
	private String lastName;
	private String countryName;
	private String countyName;
	private String consName;
	private String consWardName;
	private String village;
	private Long countryId;
	private Long countyId;
	private Long consId;
	private Long consWardId;
	private Long villageId;
	private String givenByUserName;
	private boolean verified;
	private String verifiedBy;
	private int receiverVersion;
	private String errorMessage;
	private java.util.Date cretDate;
	private String createdBy;
	private java.util.Date lastUpdate;
	private String lastUpdatedBy;
	private boolean selected;
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public long getRawReceiverId() {
		return rawReceiverId;
	}
	public void setRawReceiverId(long rawReceiverId) {
		this.rawReceiverId = rawReceiverId;
	}
	public String getReceiverContact() {
		return receiverContact;
	}
	public void setReceiverContact(String receiverContact) {
		this.receiverContact = receiverContact;
	}
	public String getReceiverType() {
		return receiverType;
	}
	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
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
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
	public String getConsName() {
		return consName;
	}
	public void setConsName(String consName) {
		this.consName = consName;
	}
	public String getConsWardName() {
		return consWardName;
	}
	public void setConsWardName(String consWardName) {
		this.consWardName = consWardName;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public Long getCountyId() {
		return countyId;
	}
	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
	public Long getConsId() {
		return consId;
	}
	public void setConsId(Long consId) {
		this.consId = consId;
	}
	public Long getConsWardId() {
		return consWardId;
	}
	public void setConsWardId(Long consWardId) {
		this.consWardId = consWardId;
	}
	public Long getVillageId() {
		return villageId;
	}
	public void setVillageId(Long villageId) {
		this.villageId = villageId;
	}
	public String getGivenByUserName() {
		return givenByUserName;
	}
	public void setGivenByUserName(String givenByUserName) {
		this.givenByUserName = givenByUserName;
	}
	public boolean isVerified() {
		return verified;
	}
	public void setVerified(boolean verified) {
		this.verified = verified;
	}
	public String getVerifiedBy() {
		return verifiedBy;
	}
	public void setVerifiedBy(String verifiedBy) {
		this.verifiedBy = verifiedBy;
	}
	public int getReceiverVersion() {
		return receiverVersion;
	}
	public void setReceiverVersion(int receiverVersion) {
		this.receiverVersion = receiverVersion;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public java.util.Date getCretDate() {
		return cretDate;
	}
	public void setCretDate(java.util.Date cretDate) {
		this.cretDate = cretDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public java.util.Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(java.util.Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consId == null) ? 0 : consId.hashCode());
		result = prime * result
				+ ((consName == null) ? 0 : consName.hashCode());
		result = prime * result
				+ ((consWardName == null) ? 0 : consWardName.hashCode());
		result = prime * result
				+ ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result
				+ ((countyName == null) ? 0 : countyName.hashCode());
		result = prime * result
				+ ((givenByUserName == null) ? 0 : givenByUserName.hashCode());
		result = prime * result
				+ ((receiverContact == null) ? 0 : receiverContact.hashCode());
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
		HunterRawReceiverJson other = (HunterRawReceiverJson) obj;
		if (consId == null) {
			if (other.consId != null)
				return false;
		} else if (!consId.equals(other.consId))
			return false;
		if (consName == null) {
			if (other.consName != null)
				return false;
		} else if (!consName.equals(other.consName))
			return false;
		if (consWardName == null) {
			if (other.consWardName != null)
				return false;
		} else if (!consWardName.equals(other.consWardName))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (countyName == null) {
			if (other.countyName != null)
				return false;
		} else if (!countyName.equals(other.countyName))
			return false;
		if (givenByUserName == null) {
			if (other.givenByUserName != null)
				return false;
		} else if (!givenByUserName.equals(other.givenByUserName))
			return false;
		if (receiverContact == null) {
			if (other.receiverContact != null)
				return false;
		} else if (!receiverContact.equals(other.receiverContact))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "HunterRawReceiverJson [rawReceiverId=" + rawReceiverId
				+ ", receiverContact=" + receiverContact + ", receiverType="
				+ receiverType + ", firstName=" + firstName + ", lastName="
				+ lastName + ", countryName=" + countryName + ", countyName="
				+ countyName + ", consName=" + consName + ", consWardName="
				+ consWardName + ", village=" + village + ", countryId="
				+ countryId + ", countyId=" + countyId + ", consId=" + consId
				+ ", consWardId=" + consWardId + ", villageId=" + villageId
				+ ", givenByUserName=" + givenByUserName + ", verified="
				+ verified + ", verifiedBy=" + verifiedBy
				+ ", receiverVersion=" + receiverVersion + ", errorMessage="
				+ errorMessage + ", cretDate=" + cretDate + ", createdBy="
				+ createdBy + ", lastUpdate=" + lastUpdate + ", lastUpdatedBy="
				+ lastUpdatedBy + "]";
	}	
	
	
	
	
}
