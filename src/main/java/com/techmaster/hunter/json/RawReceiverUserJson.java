package com.techmaster.hunter.json;

public class RawReceiverUserJson {
	
	private Long userId;
	private String rawUserName;
	private String firstName;
	private String lastName;
	private String middleName;
	private String email;
	private String phoneNumber;
	private String userName;
	private float compensation;
	private int allContctNo;
	private int vrfdContctNo;
	private String country;
	private String county;
	private String constituency;
	private String consWard;
	private String countryId;
	private String countyId;
	private String constituencyId;
	private String consWardId;
	private String village;
	private java.util.Date cretDate;
	private java.util.Date lastUpdate;
	private String createdBy;
	private String lastUpdatedBy;
	
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getCompensation() {
		return compensation;
	}
	public void setCompensation(float compensation) {
		this.compensation = compensation;
	}
	public int getAllContctNo() {
		return allContctNo;
	}
	public void setAllContctNo(int allContctNo) {
		this.allContctNo = allContctNo;
	}
	public int getVrfdContctNo() {
		return vrfdContctNo;
	}
	public void setVrfdContctNo(int vrfdContctNo) {
		this.vrfdContctNo = vrfdContctNo;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getConstituency() {
		return constituency;
	}
	public void setConstituency(String constituency) {
		this.constituency = constituency;
	}
	public String getConsWard() {
		return consWard;
	}
	public void setConsWard(String consWard) {
		this.consWard = consWard;
	}
	public String getVillage() {
		return village;
	}
	public void setVillage(String village) {
		this.village = village;
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
	public String getRawUserName() {
		return rawUserName;
	}
	public void setRawUserName(String rawUserName) {
		this.rawUserName = rawUserName;
	}
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCountyId() {
		return countyId;
	}
	public void setCountyId(String countyId) {
		this.countyId = countyId;
	}
	public String getConstituencyId() {
		return constituencyId;
	}
	public void setConstituencyId(String constituencyId) {
		this.constituencyId = constituencyId;
	}
	public String getConsWardId() {
		return consWardId;
	}
	public void setConsWardId(String consWardId) {
		this.consWardId = consWardId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
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
		RawReceiverUserJson other = (RawReceiverUserJson) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "RawReceiverUserJson [userId=" + userId + ", rawUserName="
				+ rawUserName + ", firstName=" + firstName + ", lastName="
				+ lastName + ", middleName=" + middleName + ", email=" + email
				+ ", phoneNumber=" + phoneNumber + ", userName=" + userName
				+ ", compensation=" + compensation + ", allContctNo="
				+ allContctNo + ", vrfdContctNo=" + vrfdContctNo + ", country="
				+ country + ", county=" + county + ", constituency="
				+ constituency + ", consWard=" + consWard + ", village="
				+ village + ", cretDate=" + cretDate + ", lastUpdate="
				+ lastUpdate + ", createdBy=" + createdBy + ", lastUpdatedBy="
				+ lastUpdatedBy + "]";
	}
	
	
	
	
	

}
