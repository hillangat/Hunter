package com.techmaster.hunter.obj.beans;

public class ConstituencyWard {
	
	private Long wardId;
	private String wardName;
	private int wardPopulation;
	private int hunterPopulation;
	private String mapDots;
	private AuditInfo auditInfo;
	private String constituencyWardCode;
	private Long constituencyId;
	
	public ConstituencyWard() {
		super();
	}

	public Long getWardId() {
		return wardId;
	}

	public void setWardId(Long wardId) {
		this.wardId = wardId;
	}

	public String getWardName() {
		return wardName;
	}

	public void setWardName(String wardName) {
		this.wardName = wardName;
	}

	public int getWardPopulation() {
		return wardPopulation;
	}

	public void setWardPopulation(int wardPopulation) {
		this.wardPopulation = wardPopulation;
	}

	public int getHunterPopulation() {
		return hunterPopulation;
	}

	public void setHunterPopulation(int hunterPopulation) {
		this.hunterPopulation = hunterPopulation;
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

	public String getConstituencyWardCode() {
		return constituencyWardCode;
	}

	public void setConstituencyWardCode(String constituencyWardCode) {
		this.constituencyWardCode = constituencyWardCode;
	}

	public Long getConstituencyId() {
		return constituencyId;
	}

	public void setConstituencyId(Long constituencyId) {
		this.constituencyId = constituencyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((constituencyId == null) ? 0 : constituencyId.hashCode());
		result = prime
				* result
				+ ((constituencyWardCode == null) ? 0 : constituencyWardCode
						.hashCode());
		result = prime * result + hunterPopulation;
		result = prime * result + ((mapDots == null) ? 0 : mapDots.hashCode());
		result = prime * result + ((wardId == null) ? 0 : wardId.hashCode());
		result = prime * result
				+ ((wardName == null) ? 0 : wardName.hashCode());
		result = prime * result + wardPopulation;
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
		ConstituencyWard other = (ConstituencyWard) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (constituencyId == null) {
			if (other.constituencyId != null)
				return false;
		} else if (!constituencyId.equals(other.constituencyId))
			return false;
		if (constituencyWardCode == null) {
			if (other.constituencyWardCode != null)
				return false;
		} else if (!constituencyWardCode.equals(other.constituencyWardCode))
			return false;
		if (hunterPopulation != other.hunterPopulation)
			return false;
		if (mapDots == null) {
			if (other.mapDots != null)
				return false;
		} else if (!mapDots.equals(other.mapDots))
			return false;
		if (wardId == null) {
			if (other.wardId != null)
				return false;
		} else if (!wardId.equals(other.wardId))
			return false;
		if (wardName == null) {
			if (other.wardName != null)
				return false;
		} else if (!wardName.equals(other.wardName))
			return false;
		if (wardPopulation != other.wardPopulation)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConstituencyWard [wardId=" + wardId + ", wardName=" + wardName
				+ ", wardPopulation=" + wardPopulation + ", hunterPopulation="
				+ hunterPopulation + ", mapDots=" + mapDots + ", auditInfo="
				+ auditInfo + ", constituencyWardCode=" + constituencyWardCode
				+ ", constituencyId=" + constituencyId + "]";
	}

	

	
	

	

	

	
		
	

}
