package com.techmaster.hunter.dao.types;

import java.util.List;

import com.techmaster.hunter.obj.beans.MessageAttachmentMetadata;

public interface EmailAttachmentMetadataDao {

	public void insertEmailAttachmentMetadata(MessageAttachmentMetadata attachmentMetadata);
	public void insertEmailAttachmentMetadatas(List<MessageAttachmentMetadata> attachmentMetadatas);
	public void deleteEmailAttachmentMetadata(MessageAttachmentMetadata attachmentMetadata);
	public void deleteEmailAttachmentMetadataById(Long attachmentMetadataId);
	public void updateEmailAttachmentMetadata(MessageAttachmentMetadata attachmentMetadata);
	public MessageAttachmentMetadata getEmailAttachmentMetadataById(Long attachmentMetadataId);
	public List<MessageAttachmentMetadata> getAllEmailAttachmentMetadata();
	public List<MessageAttachmentMetadata> getAllEmailAttachmentMetadataForMsg(Long msgId);
	
}
