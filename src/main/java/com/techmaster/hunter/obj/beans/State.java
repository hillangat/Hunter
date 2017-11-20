package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;

public class State {
	
	private Long stateId;
	private String stateName;
	private int statePopulation;
	private int hunterPopulation;
	private String mapDots;
	private AuditInfo auditInfo;
	private Long countryId;
	private String stateCode;
	
	private Set<County> counties = new HashSet<>();
	
	public Long getStateId() {
		return stateId;
	}
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public int getStatePopulation() {
		return statePopulation;
	}
	public void setStatePopulation(int statePopulation) {
		this.statePopulation = statePopulation;
	}
	public int getHunterPopulation() {
		return hunterPopulation;
	}
	public void setHunterPopulation(int hunterPopulation) {
		this.hunterPopulation = hunterPopulation;
	}
	public Set<County> getCounties() {
		return counties;
	}
	public void setCounties(Set<County> counties) {
		this.counties = counties;
	}
	public String getMapDots() {
		return mapDots;
	}
	public void setMapDots(String mapDots) {
		this.mapDots = mapDots;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public Long getCountryId() {
		return countryId;
	}
	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
	public String getStateCode() {
		return stateCode;
	}
	public void setStateCode(String stateCode) {
		this.stateCode = stateCode;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((counties == null) ? 0 : counties.hashCode());
		result = prime * result
				+ ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result + hunterPopulation;
		result = prime * result + ((mapDots == null) ? 0 : mapDots.hashCode());
		result = prime * result
				+ ((stateCode == null) ? 0 : stateCode.hashCode());
		result = prime * result + ((stateId == null) ? 0 : stateId.hashCode());
		result = prime * result
				+ ((stateName == null) ? 0 : stateName.hashCode());
		result = prime * result + statePopulation;
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
		State other = (State) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (counties == null) {
			if (other.counties != null)
				return false;
		} else if (!counties.equals(other.counties))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (hunterPopulation != other.hunterPopulation)
			return false;
		if (mapDots == null) {
			if (other.mapDots != null)
				return false;
		} else if (!mapDots.equals(other.mapDots))
			return false;
		if (stateCode == null) {
			if (other.stateCode != null)
				return false;
		} else if (!stateCode.equals(other.stateCode))
			return false;
		if (stateId == null) {
			if (other.stateId != null)
				return false;
		} else if (!stateId.equals(other.stateId))
			return false;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		if (statePopulation != other.statePopulation)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "State [stateId=" + stateId + ", stateName=" + stateName
				+ ", statePopulation=" + statePopulation
				+ ", hunterPopulation=" + hunterPopulation + ", mapDots="
				+ mapDots + ", auditInfo=" + auditInfo + ", countryId="
				+ countryId + ", stateCode=" + stateCode + ", counties="
				+ counties + "]";
	}
	
	
	
	
	
	
	
	
	

}
