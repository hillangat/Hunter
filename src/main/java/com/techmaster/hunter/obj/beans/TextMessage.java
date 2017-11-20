package com.techmaster.hunter.obj.beans;


public class TextMessage extends Message{
	
	private String text;
	private String disclaimer;
	private String fromPhone;
	private String toPhone;
	private boolean pageable;
	private int pageWordCount;
	
	public TextMessage() {
		super();
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDisclaimer() {
		return disclaimer;
	}

	public void setDisclaimer(String disclaimer) {
		this.disclaimer = disclaimer;
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

	public boolean isPageable() {
		return pageable;
	}

	public void setPageable(boolean pageable) {
		this.pageable = pageable;
	}

	public int getPageWordCount() {
		return pageWordCount;
	}

	public void setPageWordCount(int pageWordCount) {
		this.pageWordCount = pageWordCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((disclaimer == null) ? 0 : disclaimer.hashCode());
		result = prime * result
				+ ((fromPhone == null) ? 0 : fromPhone.hashCode());
		result = prime * result + pageWordCount;
		result = prime * result + (pageable ? 1231 : 1237);
		result = prime * result + ((text == null) ? 0 : text.hashCode());
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
		TextMessage other = (TextMessage) obj;
		if (disclaimer == null) {
			if (other.disclaimer != null)
				return false;
		} else if (!disclaimer.equals(other.disclaimer))
			return false;
		if (fromPhone == null) {
			if (other.fromPhone != null)
				return false;
		} else if (!fromPhone.equals(other.fromPhone))
			return false;
		if (pageWordCount != other.pageWordCount)
			return false;
		if (pageable != other.pageable)
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
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
		String msgStr = super.toString();
		return msgStr + "TextMessage [text=" + text + ", disclaimer=" + disclaimer
				+ ", fromPhone=" + fromPhone + ", toPhone=" + toPhone
				+ ", pageable=" + pageable + ", pageWordCount=" + pageWordCount
				+ "]";
	}

	
	
	
		
}
