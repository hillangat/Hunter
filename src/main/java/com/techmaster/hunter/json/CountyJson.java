package com.techmaster.hunter.json;

public class CountyJson {
	
	private Long countyId;
	private String countyName;
	private int countyPopulation;
	private int hunterPopulation;
	private boolean hasState;
	private String mapDots;
	private Long stateId;
	private Long countryId;
	private String countyCode;
	private String cretDate;
	private String createdBy;
	private String lastUpdate;
	private String lastUpdatedBy;
	
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
	
	
	public int getCountyPopulation() {
		return countyPopulation;
	}


	public void setCountyPopulation(int countyPopulation) {
		this.countyPopulation = countyPopulation;
	}


	public int getHunterPopulation() {
		return hunterPopulation;
	}


	public void setHunterPopulation(int hunterPopulation) {
		this.hunterPopulation = hunterPopulation;
	}


	public boolean isHasState() {
		return hasState;
	}


	public void setHasState(boolean hasState) {
		this.hasState = hasState;
	}


	public String getMapDots() {
		return mapDots;
	}


	public void setMapDots(String mapDots) {
		this.mapDots = mapDots;
	}


	public Long getStateId() {
		return stateId;
	}


	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}


	public String getCountyCode() {
		return countyCode;
	}


	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
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
