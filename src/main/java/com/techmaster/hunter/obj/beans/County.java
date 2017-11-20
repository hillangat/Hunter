package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;


public class County {
	
	private long countyId;
	private String countyName;
	private int countyPopulation;
	private int hunterPopulation;
	private boolean hasState;
	private String mapDots;
	private Long stateId;
	private Long countryId;
	private String countyCode;
	private AuditInfo auditInfo;
	
	private Set<Constituency> constituencies = new HashSet<Constituency>();
	
	public County() {
		super();
	}
	public long getCountyId() {
		return countyId;
	}
	public String getCountyName() {
		return countyName;
	}
	public void setCountyName(String countyName) {
		this.countyName = countyName;
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
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getCountyCode() {
		return countyCode;
	}
	public void setCountyCode(String countyCode) {
		this.countyCode = countyCode;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public Set<Constituency> getConstituencies() {
		return constituencies;
	}
	public void setConstituencies(Set<Constituency> constituencies) {
		this.constituencies = constituencies;
	}
	public void setCountyId(long countyId) {
		this.countyId = countyId;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((constituencies == null) ? 0 : constituencies.hashCode());
		result = prime * result
				+ ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result
				+ ((countyCode == null) ? 0 : countyCode.hashCode());
		result = prime * result + (int) (countyId ^ (countyId >>> 32));
		result = prime * result
				+ ((countyName == null) ? 0 : countyName.hashCode());
		result = prime * result + countyPopulation;
		result = prime * result + (hasState ? 1231 : 1237);
		result = prime * result + hunterPopulation;
		result = prime * result + ((mapDots == null) ? 0 : mapDots.hashCode());
		result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
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
		County other = (County) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (constituencies == null) {
			if (other.constituencies != null)
				return false;
		} else if (!constituencies.equals(other.constituencies))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (countyCode == null) {
			if (other.countyCode != null)
				return false;
		} else if (!countyCode.equals(other.countyCode))
			return false;
		if (countyId != other.countyId)
			return false;
		if (countyName == null) {
			if (other.countyName != null)
				return false;
		} else if (!countyName.equals(other.countyName))
			return false;
		if (countyPopulation != other.countyPopulation)
			return false;
		if (hasState != other.hasState)
			return false;
		if (hunterPopulation != other.hunterPopulation)
			return false;
		if (mapDots == null) {
			if (other.mapDots != null)
				return false;
		} else if (!mapDots.equals(other.mapDots))
			return false;
		if (stateId == null) {
			if (other.stateId != null)
				return false;
		} else if (!stateId.equals(other.stateId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "County [countyId=" + countyId + ", countyName=" + countyName
				+ ", countyPopulation=" + countyPopulation
				+ ", hunterPopulation=" + hunterPopulation + ", hasState="
				+ hasState + ", mapDots=" + mapDots + ", stateId=" + stateId
				+ ", countryId=" + countryId + ", countyCode=" + countyCode
				+ ", auditInfo=" + auditInfo + ", constituencies="
				+ constituencies + "]";
	}

	
	
	
		
	
	
}
