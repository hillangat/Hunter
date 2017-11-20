package com.techmaster.hunter.obj.beans;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;


@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class ReceiverRegion  {
	
	private Long regionId;
	private String country;
	private String state;
	private boolean hasState;
	private String county;
	private String constituency;
	private String city;
	private String ward;
	private String village;
	private double longitude;
	private double latitude;
	private String currentLevel;
	private String borderLongLats;
	private AuditInfo auditInfo;
	
	public ReceiverRegion() {
		super();
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean isHasState() {
		return hasState;
	}

	public void setHasState(boolean hasState) {
		this.hasState = hasState;
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getWard() {
		return ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(String currentLevel) {
		this.currentLevel = currentLevel;
	}

	public String getBorderLongLats() {
		return borderLongLats;
	}

	public void setBorderLongLats(String borderLongLats) {
		this.borderLongLats = borderLongLats;
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
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((borderLongLats == null) ? 0 : borderLongLats.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((constituency == null) ? 0 : constituency.hashCode());
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((county == null) ? 0 : county.hashCode());
		result = prime * result
				+ ((currentLevel == null) ? 0 : currentLevel.hashCode());
		result = prime * result + (hasState ? 1231 : 1237);
		long temp;
		temp = Double.doubleToLongBits(latitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(longitude);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((regionId == null) ? 0 : regionId.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + ((village == null) ? 0 : village.hashCode());
		result = prime * result + ((ward == null) ? 0 : ward.hashCode());
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
		ReceiverRegion other = (ReceiverRegion) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (borderLongLats == null) {
			if (other.borderLongLats != null)
				return false;
		} else if (!borderLongLats.equals(other.borderLongLats))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (constituency == null) {
			if (other.constituency != null)
				return false;
		} else if (!constituency.equals(other.constituency))
			return false;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (county == null) {
			if (other.county != null)
				return false;
		} else if (!county.equals(other.county))
			return false;
		if (currentLevel == null) {
			if (other.currentLevel != null)
				return false;
		} else if (!currentLevel.equals(other.currentLevel))
			return false;
		if (hasState != other.hasState)
			return false;
		if (Double.doubleToLongBits(latitude) != Double
				.doubleToLongBits(other.latitude))
			return false;
		if (Double.doubleToLongBits(longitude) != Double
				.doubleToLongBits(other.longitude))
			return false;
		if (regionId == null) {
			if (other.regionId != null)
				return false;
		} else if (!regionId.equals(other.regionId))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (village == null) {
			if (other.village != null)
				return false;
		} else if (!village.equals(other.village))
			return false;
		if (ward == null) {
			if (other.ward != null)
				return false;
		} else if (!ward.equals(other.ward))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReceiverRegion [regionId=" + regionId + ", country=" + country
				+ ", state=" + state + ", hasState=" + hasState + ", county="
				+ county + ", constituency=" + constituency + ", city=" + city
				+ ", ward=" + ward + ", village=" + village + ", longitude="
				+ longitude + ", latitude=" + latitude + ", currentLevel="
				+ currentLevel + ", borderLongLats=" + borderLongLats
				+ ", auditInfo=" + auditInfo + "]";
	}

	

	
		




}
