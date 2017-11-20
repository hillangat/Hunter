package com.techmaster.hunter.chats;

import java.util.ArrayList;
import java.util.List;

import com.techmaster.hunter.obj.beans.AuditInfo;

public class ChatRoom {
	
	private Long chatRoomId;
	private String chatRoomSubject;
	private List<Chat> chatRoomChats = new ArrayList<>();
	private AuditInfo auditInfo;
	
	public Long getChatRoomId() {
		return chatRoomId;
	}
	public void setChatRoomId(Long chatRoomId) {
		this.chatRoomId = chatRoomId;
	}
	public List<Chat> getChatRoomChats() {
		return chatRoomChats;
	}
	public void setChatRoomChats(List<Chat> chatRoomChats) {
		this.chatRoomChats = chatRoomChats;
	}
	public AuditInfo getAuditInfo() {
		return auditInfo;
	}
	public void setAuditInfo(AuditInfo auditInfo) {
		this.auditInfo = auditInfo;
	}
	public String getChatRoomSubject() {
		return chatRoomSubject;
	}
	public void setChatRoomSubject(String chatRoomSubject) {
		this.chatRoomSubject = chatRoomSubject;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((auditInfo == null) ? 0 : auditInfo.hashCode());
		result = prime * result
				+ ((chatRoomId == null) ? 0 : chatRoomId.hashCode());
		result = prime * result
				+ ((chatRoomSubject == null) ? 0 : chatRoomSubject.hashCode());
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
		ChatRoom other = (ChatRoom) obj;
		if (auditInfo == null) {
			if (other.auditInfo != null)
				return false;
		} else if (!auditInfo.equals(other.auditInfo))
			return false;
		if (chatRoomId == null) {
			if (other.chatRoomId != null)
				return false;
		} else if (!chatRoomId.equals(other.chatRoomId))
			return false;
		if (chatRoomSubject == null) {
			if (other.chatRoomSubject != null)
				return false;
		} else if (!chatRoomSubject.equals(other.chatRoomSubject))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ChatRoom [chatRoomId=" + chatRoomId + ", chatRoomSubject="
				+ chatRoomSubject + ", chatRoomChats=" + chatRoomChats
				+ ", auditInfo=" + auditInfo + "]";
	}
	
	

}
