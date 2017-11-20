package com.techmaster.hunter.json;

import java.util.ArrayList;
import java.util.List;

public class PagedHunterMessageReceiverData {
	
	private List<PagedHunterMessageReceiverJson> data = new ArrayList<>();
	private int total = 0;
	
	public List<PagedHunterMessageReceiverJson> getData() {
		return data;
	}
	public void setData(List<PagedHunterMessageReceiverJson> data) {
		this.data = data;
	}
	public int getTotal() {
		return this.total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + total;
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
		PagedHunterMessageReceiverData other = (PagedHunterMessageReceiverData) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (total != other.total)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "PagedHunterMessageReceiverData [data=" + data + ", total="
				+ total + "]";
	}
	
	
	

}
