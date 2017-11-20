package com.techmaster.hunter.obj.beans;

import java.util.HashSet;
import java.util.Set;

public class Constituency {
	
	private Long cnsttncyId;
	private String cnsttncyName;
	private Integer cnsttncyPopulation;
	private Integer hunterPopulation;
	private String cnsttncyCity;
	private String constituencyCode;
	private Long countyId;
	private String mapDots;
	
	private AuditInfo auditInfo;
	private Set<ConstituencyWard> constituencyWards = new HashSet<ConstituencyWard>();
	
	public Constituency() {
		super();
	}

	public Long getCnsttncyId() {
		return cnsttncyId;
	}

	public void setCnsttncyId(Long cnsttncyId) {
		this.cnsttncyId = cnsttncyId;
	}

	public String getCnsttncyName() {
		return cnsttncyName;
	}

	public void setCnsttncyName(String cnsttncyName) {
		this.cnsttncyName = cnsttncyName;
	}

	public Integer getCnsttncyPopulation() {
		return cnsttncyPopulation;
	}

	public void setCnsttncyPopulation(Integer cnsttncyPopulation) {
		this.cnsttncyPopulation = cnsttncyPopulation;
	}

	public Integer getHunterPopulation() {
		return hunterPopulation;
	}

	public void setHunterPopulation(Integer hunterPopulation) {
		this.hunterPopulation = hunterPopulation;
	}

	public String getCnsttncyCity() {
		return cnsttncyCity;
	}

	public void setCnsttncyCity(String cnsttncyCity) {
		this.cnsttncyCity = cnsttncyCity;
	}

	public String getConstituencyCode() {
		return constituencyCode;
	}

	public void setConstituencyCode(String constituencyCode) {
		this.constituencyCode = constituencyCode;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
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

	public Set<ConstituencyWard> getConstituencyWards() {
		return constituencyWards;
	}

	public void setConstituencyWards(Set<ConstituencyWard> constituencyWards) {
		this.constituencyWards = constituencyWards;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((cnsttncyCity == null) ? 0 : cnsttncyCity.hashCode());
		result = prime * result
				+ ((cnsttncyId == null) ? 0 : cnsttncyId.hashCode());
		result = prime * result
				+ ((cnsttncyName == null) ? 0 : cnsttncyName.hashCode());
		result = prime
				* result
				+ ((cnsttncyPopulation == null) ? 0 : cnsttncyPopulation
						.hashCode());
		result = prime
				* result
				+ ((constituencyCode == null) ? 0 : constituencyCode.hashCode());
		result = prime
				* result
				+ ((constituencyWards == null) ? 0 : constituencyWards
						.hashCode());
		result = prime * result
				+ ((countyId == null) ? 0 : countyId.hashCode());
		result = prime
				* result
				+ ((hunterPopulation == null) ? 0 : hunterPopulation.hashCode());
		result = prime * result + ((mapDots == null) ? 0 : mapDots.hashCode());
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
		Constituency other = (Constituency) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (cnsttncyCity == null) {
			if (other.cnsttncyCity != null)
				return false;
		} else if (!cnsttncyCity.equals(other.cnsttncyCity))
			return false;
		if (cnsttncyId == null) {
			if (other.cnsttncyId != null)
				return false;
		} else if (!cnsttncyId.equals(other.cnsttncyId))
			return false;
		if (cnsttncyName == null) {
			if (other.cnsttncyName != null)
				return false;
		} else if (!cnsttncyName.equals(other.cnsttncyName))
			return false;
		if (cnsttncyPopulation == null) {
			if (other.cnsttncyPopulation != null)
				return false;
		} else if (!cnsttncyPopulation.equals(other.cnsttncyPopulation))
			return false;
		if (constituencyCode == null) {
			if (other.constituencyCode != null)
				return false;
		} else if (!constituencyCode.equals(other.constituencyCode))
			return false;
		if (constituencyWards == null) {
			if (other.constituencyWards != null)
				return false;
		} else if (!constituencyWards.equals(other.constituencyWards))
			return false;
		if (countyId == null) {
			if (other.countyId != null)
				return false;
		} else if (!countyId.equals(other.countyId))
			return false;
		if (hunterPopulation == null) {
			if (other.hunterPopulation != null)
				return false;
		} else if (!hunterPopulation.equals(other.hunterPopulation))
			return false;
		if (mapDots == null) {
			if (other.mapDots != null)
				return false;
		} else if (!mapDots.equals(other.mapDots))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Constituency [cnsttncyId=" + cnsttncyId + ", cnsttncyName="
				+ cnsttncyName + ", cnsttncyPopulation=" + cnsttncyPopulation
				+ ", hunterPopulation=" + hunterPopulation + ", cnsttncyCity="
				+ cnsttncyCity + ", constituencyCode=" + constituencyCode
				+ ", countyId=" + countyId + ", mapDots=" + mapDots
				+ ", auditInfo=" + auditInfo + ", constituencyWards="
				+ constituencyWards + "]";
	}

	
	
	
	

	

	
	
	

}
