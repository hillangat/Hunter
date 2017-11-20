package com.techmaster.hunter.json;

public class PagedHunterMessageReceiverJson {
	
	private int index;
	private int count;
	private String contact;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + index;
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
		PagedHunterMessageReceiverJson other = (PagedHunterMessageReceiverJson) obj;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (index != other.index)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PageHunterMessageReceiverJson [index=" + index + ",count=" + count + ", contact="
				+ contact + "]";
	}
	
	
	

}
