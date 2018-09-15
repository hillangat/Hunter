package com.techmaster.hunter.hackerrank;

public class Contact {
	
	private String name;
	private long nationalId; //will be unique across all contacts
	
	public Contact(String name, long nationalId) {
		this.name = name;
		this.nationalId = nationalId;
	}

	public String getName() {
		return name;
	}

	public long getNationalId() {
		return nationalId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (nationalId ^ (nationalId >>> 32));
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
		Contact other = (Contact) obj;
		if (nationalId != other.nationalId)
			return false;
		return true;
	}
	
}
