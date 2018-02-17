package com.techmaster.hunter.angular.data;

import java.util.List;

public class AngularData {
	
	private int total;
	private String status;
	private String message;
	public Object data;
	public List<HunterTableConfig> headers;
	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public List<HunterTableConfig> getHeaders() {
		return headers;
	}
	public void setHeaders(List<HunterTableConfig> headers) {
		this.headers = headers;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((headers == null) ? 0 : headers.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		AngularData other = (AngularData) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (headers == null) {
			if (other.headers != null)
				return false;
		} else if (!headers.equals(other.headers))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		if (total != other.total)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "AngularData [total=" + total + ", status=" + status + ", message=" + message + ", data=" + data
				+ ", headers=" + headers + "]";
	}
	
	
	

}
