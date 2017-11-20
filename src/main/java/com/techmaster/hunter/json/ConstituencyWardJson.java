package com.techmaster.hunter.json;

public class ConstituencyWardJson {
	
	private String constituencyWardName;
	private Long constituencyWardId;
	private Long constituencyId;
	
	public ConstituencyWardJson() {
		super();
	}
	
	public ConstituencyWardJson(String constituencyWardName,Long constituencyWardId, Long constituencyId) {
		super();
		this.constituencyWardName = constituencyWardName;
		this.constituencyWardId = constituencyWardId;
		this.constituencyId = constituencyId;
	}

	public String getConstituencyWardName() {
		return constituencyWardName;
	}

	public void setConstituencyWardName(String constituencyWardName) {
		this.constituencyWardName = constituencyWardName;
	}

	public Long getConstituencyWardId() {
		return constituencyWardId;
	}

	public void setConstituencyWardId(Long constituencyWardId) {
		this.constituencyWardId = constituencyWardId;
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
				+ ((constituencyId == null) ? 0 : constituencyId.hashCode());
		result = prime
				* result
				+ ((constituencyWardId == null) ? 0 : constituencyWardId
						.hashCode());
		result = prime
				* result
				+ ((constituencyWardName == null) ? 0 : constituencyWardName
						.hashCode());
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
		ConstituencyWardJson other = (ConstituencyWardJson) obj;
		if (constituencyId == null) {
			if (other.constituencyId != null)
				return false;
		} else if (!constituencyId.equals(other.constituencyId))
			return false;
		if (constituencyWardId == null) {
			if (other.constituencyWardId != null)
				return false;
		} else if (!constituencyWardId.equals(other.constituencyWardId))
			return false;
		if (constituencyWardName == null) {
			if (other.constituencyWardName != null)
				return false;
		} else if (!constituencyWardName.equals(other.constituencyWardName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ConstituencyWardJson [constituencyWardName="
				+ constituencyWardName + ", constituencyWardId="
				+ constituencyWardId + ", constituencyId=" + constituencyId
				+ "]";
	}

	
	
	

}
