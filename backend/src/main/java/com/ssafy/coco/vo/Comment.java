package com.ssafy.coco.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Comment {
	long idcomment;
	long memberId;
	long postId;
	String commentWriter;
	String contents;
	String dateCreated;
	String updateCreated;
	int access;
	
	public Comment() {
		super();
	}
	public Comment(long idcomment, long memberId, long postId, String commentWriter, String contents,
			String dateCreated, String updateCreated, int access) {
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
	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Timestamp d) {
		java.sql.Timestamp timestamp_1 = java.sql.Timestamp.valueOf(LocalDateTime.now());
		long l = (timestamp_1.getTime() - d.getTime());
		long hour = (l / 1000) / 3600;
		long minute = (l / 1000) / 60 % 60; // minute
		long second = (l / 1000) % 60; // second
		if (hour < 24) {
			if (hour == 0) {
				if (minute < 60) {
					if (minute == 0)
						this.dateCreated = second + "초전";
					else
						this.dateCreated = minute + "분전";
				}
			} else {
				this.dateCreated = hour + "시간전";
			}
		} else
			this.dateCreated = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(d);
	}
	public String getUpdateCreated() {
		return updateCreated;
	}
	public void setUpdateCreated(Timestamp d) {
		java.sql.Timestamp timestamp_1 = java.sql.Timestamp.valueOf(LocalDateTime.now());
		long l = (timestamp_1.getTime() - d.getTime());
		long hour = (l / 1000) / 3600;
		long minute = (l / 1000) / 60 % 60; // minute
		long second = (l / 1000) % 60; // second
		if (hour < 24) {
			if (hour == 0) {
				if (minute < 60) {
					if (minute == 0)
						this.updateCreated = second + "초전";
					else
						this.updateCreated = minute + "분전";
				}
			} else {
				this.updateCreated = hour + "시간전";
			}
		} else
			this.updateCreated = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(d);
	}
	public int getAccess() {
		return access;
	}
	public void setAccess(int access) {
		this.access = access;
	}
	
	
}
