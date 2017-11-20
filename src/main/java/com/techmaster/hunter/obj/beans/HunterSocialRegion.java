package com.techmaster.hunter.obj.beans;

import java.sql.Blob;

public class HunterSocialRegion {
	
	private Long regionId;
	private String regionName;
	private int population;
	private String regionDesc;
	private Long countryId;
	private Long countyId;
	private Long consId;
	private Long wardId;
	private Blob coordinates;
	private String assignedTo;
	private AuditInfo auditInfo;
	
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
	public Blob getCoordinates() {
		return coordinates;
	}
	public void setCoordinates(Blob coordinates) {
		this.coordinates = coordinates;
	}
	public String getRegionDesc() {
		return regionDesc;
	}
	public void setRegionDesc(String regionDesc) {
		this.regionDesc = regionDesc;
	}
	public String getAssignedTo() {
		return assignedTo;
	}
	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((consId == null) ? 0 : consId.hashCode());
		result = prime * result
				+ ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result
				+ ((countyId == null) ? 0 : countyId.hashCode());
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
		HunterSocialRegion other = (HunterSocialRegion) obj;
		if (consId == null) {
			if (other.consId != null)
				return false;
		} else if (!consId.equals(other.consId))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (countyId == null) {
			if (other.countyId != null)
				return false;
		} else if (!countyId.equals(other.countyId))
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
		return "HunterSocialRegion [regionId=" + regionId + ", regionName="
				+ regionName + ", population=" + population + ", regionDesc="
				+ regionDesc + ", countryId=" + countryId + ", countyId="
				+ countyId + ", consId=" + consId + ", wardId=" + wardId
				+ ", coordinates=" + coordinates + ", assignedTo=" + assignedTo
				+ ", auditInfo=" + auditInfo + "]";
	}
	
	
	
	
}
