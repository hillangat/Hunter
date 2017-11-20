package com.techmaster.hunter.json;

public class CountyJson {
	
	private String countyName;
	private Long countyId;
	private Long countryId;
	
	public CountyJson() {
		super();
	}


	public CountyJson(String countyName, Long countyId, Long countryId) {
		super();
		this.countyName = countyName;
		this.countyId = countyId;
		this.countryId = countryId;
	}


	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result
				+ ((countyId == null) ? 0 : countyId.hashCode());
		result = prime * result
				+ ((countyName == null) ? 0 : countyName.hashCode());
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
		CountyJson other = (CountyJson) obj;
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
		if (countyName == null) {
			if (other.countyName != null)
				return false;
		} else if (!countyName.equals(other.countyName))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "CountyJson [countyName=" + countyName + ", countyId="
				+ countyId + ", countryId=" + countryId + "]";
	}

	

	


}
