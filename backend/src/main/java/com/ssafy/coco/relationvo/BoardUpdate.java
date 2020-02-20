package com.ssafy.coco.relationvo;

import org.springframework.web.multipart.MultipartFile;

public class BoardUpdate {
	private long idpost;
	private String code;
	private long memberId;
	private String postTitle;
	private String postWriter;
	private String tags;
	private MultipartFile attachments;
	
	
	public BoardUpdate() {
		super();
	}


	public BoardUpdate(String code, long memberId, String postTitle, String postWriter, String tags,
			MultipartFile attachments) {
		super();
		this.code = code;
		this.memberId = memberId;
		this.postTitle = postTitle;
		this.postWriter = postWriter;
		this.tags = tags;
		this.attachments = attachments;
	}
	
	
	
	public BoardUpdate(long idpost, String code, long memberId, String postTitle, String postWriter, String tags,
			MultipartFile attachments) {
		super();
		this.idpost = idpost;
		this.code = code;
		this.memberId = memberId;
		this.postTitle = postTitle;
		this.postWriter = postWriter;
		this.tags = tags;
		this.attachments = attachments;
	}
	
	

	public long getIdpost() {
		return idpost;
	}


	public void setIdpost(long idpost) {
		this.idpost = idpost;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public long getMemberId() {
		return memberId;
	}


	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}


	public String getPostTitle() {
		return postTitle;
	}


	public void setPostTitle(String postTitle) {
		this.postTitle = postTitle;
	}


	public String getPostWriter() {
		return postWriter;
	}


	public void setPostWriter(String postWriter) {
		this.postWriter = postWriter;
	}


	public String getTags() {
		return tags;
	}


	public void setTags(String tags) {
		this.tags = tags;
	}


	public MultipartFile getAttachments() {
		return attachments;
	}


	public void setAttachments(MultipartFile attachments) {
		this.attachments = attachments;
	}


	@Override
	public String toString() {
		return "BoardUpdate [idpost=" + idpost + ", code=" + code + ", memberId=" + memberId + ", postTitle="
				+ postTitle + ", postWriter=" + postWriter + ", tags=" + tags + ", attachments=" + attachments + "]";
	} 
	
	
	
}
