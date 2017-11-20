package com.techmaster.hunter.json;

public class ConstituencyJson {
	
	private String constituencyName;
	private Long constituencyId;
	private Long countyId;
	
	public ConstituencyJson() {
		super();
	}
	
	public ConstituencyJson(String constituencyName, Long constituencyId,
			Long countyId) {
		super();
		this.constituencyName = constituencyName;
		this.constituencyId = constituencyId;
		this.countyId = countyId;
	}

	public String getConstituencyName() {
		return constituencyName;
	}
	public void setConstituencyName(String constituencyName) {
		this.constituencyName = constituencyName;
	}
	public Long getConstituencyId() {
		return constituencyId;
	}
	public void setConstituencyId(Long constituencyId) {
		this.constituencyId = constituencyId;
	}

	public Long getCountyId() {
		return countyId;
	}

	public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((constituencyId == null) ? 0 : constituencyId.hashCode());
		result = prime
				* result
				+ ((constituencyName == null) ? 0 : constituencyName.hashCode());
		result = prime * result
				+ ((countyId == null) ? 0 : countyId.hashCode());
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
		ConstituencyJson other = (ConstituencyJson) obj;
		if (constituencyId == null) {
			if (other.constituencyId != null)
				return false;
		} else if (!constituencyId.equals(other.constituencyId))
			return false;
		if (constituencyName == null) {
			if (other.constituencyName != null)
				return false;
		} else if (!constituencyName.equals(other.constituencyName))
			return false;
		if (countyId == null) {
			if (other.countyId != null)
				return false;
		} else if (!countyId.equals(other.countyId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConstituencyJson [constituencyName=" + constituencyName
				+ ", constituencyId=" + constituencyId + ", countyId="
				+ countyId + "]";
	}

	

}
