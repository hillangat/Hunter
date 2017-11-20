package com.techmaster.hunter.obj.beans;

import java.util.Arrays;



public class MediaMessage extends Message{

	private String msgFileName;
	private String mediaTye;
	private String msgDesc;
	private String fileOriginalName;
	private byte[] contents;
	private int size;
	private String fromPhone;
	private String toPhone;
	
	public MediaMessage() {
		super();
	}

	public String getMsgFileName() {
		return msgFileName;
	}

	public void setMsgFileName(String msgFileName) {
		this.msgFileName = msgFileName;
	}

	public String getMediaTye() {
		return mediaTye;
	}

	public void setMediaTye(String mediaTye) {
		this.mediaTye = mediaTye;
	}

	public String getMsgDesc() {
		return msgDesc;
	}

	public void setMsgDesc(String msgDesc) {
		this.msgDesc = msgDesc;
	}

	public String getFileOriginalName() {
		return fileOriginalName;
	}

	public void setFileOriginalName(String fileOriginalName) {
		this.fileOriginalName = fileOriginalName;
	}

	public byte[] getContents() {
		return contents;
	}

	public void setContents(byte[] contents) {
		this.contents = contents;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getFromPhone() {
		return fromPhone;
	}

	public void setFromPhone(String fromPhone) {
		this.fromPhone = fromPhone;
	}

	public String getToPhone() {
		return toPhone;
	}

	public void setToPhone(String toPhone) {
		this.toPhone = toPhone;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Arrays.hashCode(contents);
		result = prime
				* result
				+ ((fileOriginalName == null) ? 0 : fileOriginalName.hashCode());
		result = prime * result
				+ ((fromPhone == null) ? 0 : fromPhone.hashCode());
		result = prime * result
				+ ((mediaTye == null) ? 0 : mediaTye.hashCode());
		result = prime * result + ((msgDesc == null) ? 0 : msgDesc.hashCode());
		result = prime * result
				+ ((msgFileName == null) ? 0 : msgFileName.hashCode());
		result = prime * result + size;
		result = prime * result + ((toPhone == null) ? 0 : toPhone.hashCode());
		return result;
	}

	
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		MediaMessage other = (MediaMessage) obj;
		if (!Arrays.equals(contents, other.contents))
			return false;
		if (fileOriginalName == null) {
			if (other.fileOriginalName != null)
				return false;
		} else if (!fileOriginalName.equals(other.fileOriginalName))
			return false;
		if (fromPhone == null) {
			if (other.fromPhone != null)
				return false;
		} else if (!fromPhone.equals(other.fromPhone))
			return false;
		if (mediaTye == null) {
			if (other.mediaTye != null)
				return false;
		} else if (!mediaTye.equals(other.mediaTye))
			return false;
		if (msgDesc == null) {
			if (other.msgDesc != null)
				return false;
		} else if (!msgDesc.equals(other.msgDesc))
			return false;
		if (msgFileName == null) {
			if (other.msgFileName != null)
				return false;
		} else if (!msgFileName.equals(other.msgFileName))
			return false;
		if (size != other.size)
			return false;
		if (toPhone == null) {
			if (other.toPhone != null)
				return false;
		} else if (!toPhone.equals(other.toPhone))
			return false;
		return true;
	}

	
	
	@Override
	public String toString() {
		return "MediaMessage [msgFileName=" + msgFileName + ", mediaTye="
				+ mediaTye + ", msgDesc=" + msgDesc + ", fileOriginalName="
				+ fileOriginalName + ", contents=" + Arrays.toString(contents)
				+ ", size=" + size + ", fromPhone=" + fromPhone + ", toPhone="
				+ toPhone + "]";
	}

		
	
	
	
	
}
