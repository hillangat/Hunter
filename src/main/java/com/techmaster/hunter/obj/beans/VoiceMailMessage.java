package com.techmaster.hunter.obj.beans;

import java.util.Arrays;


public class VoiceMailMessage extends Message {
	
	private Long voiceMailMsgId;
	private String textContent;
	private byte[] voiceMailContents;
	
	public VoiceMailMessage() {
		super();
	}

	public Long getVoiceMailMsgId() {
		return voiceMailMsgId;
	}

	public void setVoiceMailMsgId(Long voiceMailMsgId) {
		this.voiceMailMsgId = voiceMailMsgId;
	}

	public String getTextContent() {
		return textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public byte[] getVoiceMailContents() {
		return voiceMailContents;
	}

	public void setVoiceMailContents(byte[] voiceMailContents) {
		this.voiceMailContents = voiceMailContents;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((textContent == null) ? 0 : textContent.hashCode());
		result = prime * result + Arrays.hashCode(voiceMailContents);
		result = prime * result
				+ ((voiceMailMsgId == null) ? 0 : voiceMailMsgId.hashCode());
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
		VoiceMailMessage other = (VoiceMailMessage) obj;
		if (textContent == null) {
			if (other.textContent != null)
				return false;
		} else if (!textContent.equals(other.textContent))
			return false;
		if (!Arrays.equals(voiceMailContents, other.voiceMailContents))
			return false;
		if (voiceMailMsgId == null) {
			if (other.voiceMailMsgId != null)
				return false;
		} else if (!voiceMailMsgId.equals(other.voiceMailMsgId))
			return false;
		return true;
	}

	
	@Override
	public String toString() {
		return "VoiceMailMessage [voiceMailMsgId=" + voiceMailMsgId
				+ ", textContent=" + textContent + ", voiceMailContents="
				+ Arrays.toString(voiceMailContents) + "]";
	}

	
	
	
	
	
}
