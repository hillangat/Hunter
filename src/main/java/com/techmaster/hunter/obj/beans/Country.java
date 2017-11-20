package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;


public class Country {
	
	private Long countryId;
	private String countryName;
	private String capital;
	private boolean hasState;
	private int hunterPopulation;
	private int countryPopulation;
	private String countryCode;
	private AuditInfo auditInfo;
	
	private Set<County> counties = new HashSet<County>();
	private Set<State> states = new HashSet<State>();
	
	public Country() {
		super();
	}

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCapital() {
		return capital;
	}

	public void setCapital(String capital) {
		this.capital = capital;
	}

	public boolean isHasState() {
		return hasState;
	}

	public void setHasState(boolean hasState) {
		this.hasState = hasState;
	}

	public AuditInfo getAuditInfo() {
		return auditInfo;
	}

	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}

	public int getHunterPopulation() {
		return hunterPopulation;
	}

	public void setHunterPopulation(int hunterPopulation) {
		this.hunterPopulation = hunterPopulation;
	}

	public int getCountryPopulation() {
		return countryPopulation;
	}

	public void setCountryPopulation(int countryPopulation) {
		this.countryPopulation = countryPopulation;
	}

	public Set<County> getCounties() {
		return counties;
	}

	public void setCounties(Set<County> counties) {
		this.counties = counties;
	}

	public Set<State> getStates() {
		return states;
	}

	public void setStates(Set<State> states) {
		this.states = states;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result + ((capital == null) ? 0 : capital.hashCode());
		result = prime * result
				+ ((counties == null) ? 0 : counties.hashCode());
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result
				+ ((countryId == null) ? 0 : countryId.hashCode());
		result = prime * result
				+ ((countryName == null) ? 0 : countryName.hashCode());
		result = prime * result + countryPopulation;
		result = prime * result + (hasState ? 1231 : 1237);
		result = prime * result + hunterPopulation;
		result = prime * result + ((states == null) ? 0 : states.hashCode());
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
		Country other = (Country) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (capital == null) {
			if (other.capital != null)
				return false;
		} else if (!capital.equals(other.capital))
			return false;
		if (counties == null) {
			if (other.counties != null)
				return false;
		} else if (!counties.equals(other.counties))
			return false;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		if (countryPopulation != other.countryPopulation)
			return false;
		if (hasState != other.hasState)
			return false;
		if (hunterPopulation != other.hunterPopulation)
			return false;
		if (states == null) {
			if (other.states != null)
				return false;
		} else if (!states.equals(other.states))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Country [countryId=" + countryId + ", countryName="
				+ countryName + ", capital=" + capital + ", hasState="
				+ hasState + ", hunterPopulation=" + hunterPopulation
				+ ", countryPopulation=" + countryPopulation + ", countryCode="
				+ countryCode + ", auditInfo=" + auditInfo + ", counties="
				+ counties + ", states=" + states + "]";
	}

	
		
	

	
	
	
	
	

}
