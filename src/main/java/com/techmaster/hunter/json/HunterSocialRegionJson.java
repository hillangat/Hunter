package com.techmaster.hunter.json;


public class HunterSocialRegionJson {
	
	private Long regionId;
	private String regionName;
	private int population;
	private String regionDesc;
	private Long countryId;
	private Long countyId;
	private Long consId;
	private Long wardId;
	private String coordinates;
	private String assignedTo;
	private String countryName;
	private String countyName;
	private String consName;
	private String wardName;
	
	private String cretDate;
	private String createdBy;
	private String lastUpdate;
	private String lastUpdatedBy;
	
	public Long getRegionId() {
		return regionId;
	}
	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public int getPopulation() {
		return population;
	}
	public void setPopulation(int population) {
		this.population = population;
	}
	public String getRegionDesc() {
		return regionDesc;
	}
	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
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
	public Long getWardId() {
		return wardId;
	}
	public void setWardId(Long wardId) {
		this.wardId = wardId;
	}
	public String getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public String getCretDate() {
		return cretDate;
	}
	public void setCretDate(String cretDate) {
		this.cretDate = cretDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
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
	public String getWardName() {
		return wardName;
	}
	public void setWardName(String wardName) {
		this.wardName = wardName;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((assignedTo == null) ? 0 : assignedTo.hashCode());
		result = prime * result + ((consId == null) ? 0 : consId.hashCode());
		result = prime * result
				+ ((countyId == null) ? 0 : countyId.hashCode());
		result = prime * result
				+ ((countyName == null) ? 0 : countyName.hashCode());
		result = prime * result + population;
		result = prime * result
				+ ((regionDesc == null) ? 0 : regionDesc.hashCode());
		result = prime * result
				+ ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result
				+ ((regionName == null) ? 0 : regionName.hashCode());
		result = prime * result + ((wardId == null) ? 0 : wardId.hashCode());
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
		HunterSocialRegionJson other = (HunterSocialRegionJson) obj;
		if (assignedTo == null) {
			if (other.assignedTo != null)
				return false;
		} else if (!assignedTo.equals(other.assignedTo))
			return false;
		if (consId == null) {
			if (other.consId != null)
				return false;
		} else if (!consId.equals(other.consId))
			return false;
		if (countyId == null) {
			if (other.countyId != null)
				return false;
		} else if (!countyId.equals(other.countyId))
			return false;
		if (countyName == null) {
			if (other.countyName != null)
				return false;
		} else if (!countyName.equals(other.countyName))
			return false;
		if (population != other.population)
			return false;
		if (regionDesc == null) {
			if (other.regionDesc != null)
				return false;
		} else if (!regionDesc.equals(other.regionDesc))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (regionName == null) {
			if (other.regionName != null)
				return false;
		} else if (!regionName.equals(other.regionName))
			return false;
		if (wardId == null) {
			if (other.wardId != null)
				return false;
		} else if (!wardId.equals(other.wardId))
			return false;
		return true;
	}
	
	
	@Override
	public String toString() {
		return "HunterSocialRegionJson [regionId=" + regionId + ", regionName="
				+ regionName + ", population=" + population + ", regionDesc="
				+ regionDesc + ", countryId=" + countryId + ", countyId="
				+ countyId + ", consId=" + consId + ", wardId=" + wardId
				+ ", coordinates=" + coordinates + ", assignedTo=" + assignedTo
				+ ", countryName=" + countryName + ", countyName=" + countyName
				+ ", consName=" + consName + ", wardName=" + wardName
				+ ", cretDate=" + cretDate + ", createdBy=" + createdBy
				+ ", lastUpdate=" + lastUpdate + ", lastUpdatedBy="
				+ lastUpdatedBy + "]";
	}
	
	
	

}
