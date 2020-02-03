package com.ssafy.coco.vo;

import java.sql.Timestamp;

public class Comment {
	long idcomment;
	long memberId;
	long postId;
	String commentWriter;
	String contents;
	Timestamp dateCreated;
	Timestamp updateCreated;
	int access;
	
	public Comment() {
		super();
	}
	public Comment(long idcomment, long memberId, long postId, String commentWriter, String contents,
			Timestamp dateCreated, Timestamp updateCreated, int access) {
		super();
		this.idcomment = idcomment;
		this.memberId = memberId;
		this.postId = postId;
		this.commentWriter = commentWriter;
		this.contents = contents;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.access = access;
	}
	@Override
	public String toString() {
		return "Comment [idcomment=" + idcomment + ", memberId=" + memberId + ", postId=" + postId + ", commentWriter="
				+ commentWriter + ", contents=" + contents + ", dateCreated=" + dateCreated + ", updateCreated="
				+ updateCreated + ", access=" + access + "]";
	}
	public long getIdcomment() {
		return idcomment;
	}
	public void setIdcomment(long idcomment) {
		this.idcomment = idcomment;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public long getPostId() {
		return postId;
	}
	public void setPostId(long postId) {
		this.postId = postId;
	}
	public String getCommentWriter() {
		return commentWriter;
	}
	public void setCommentWriter(String commentWriter) {
		this.commentWriter = commentWriter;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public Timestamp getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Timestamp getUpdateCreated() {
		return updateCreated;
	}
	public void setUpdateCreated(Timestamp updateCreated) {
		this.updateCreated = updateCreated;
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	
	
}
