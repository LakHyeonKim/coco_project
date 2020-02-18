package com.ssafy.coco.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Post {
	long idpost;
	long memberId;
	String postTitle;
	String postWriter;
	String dateCreated;
	String updateCreated;
	String code;
	int likeCount;
	int views;
	String imagePath;
	String filePath;

	public String getFilePath() {
		return filePath;
	}
	
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Post(long idpost, long memberId, String postTitle, String postWriter, String dateCreated,
			String updateCreated, String code, int likeCount, int views, String imagePath, String filePath, int access,
			int likeCheck, int order) {
		super();
		this.idpost = idpost;
		this.memberId = memberId;
		this.postTitle = postTitle;
		this.postWriter = postWriter;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.code = code;
		this.likeCount = likeCount;
		this.views = views;
		this.imagePath = imagePath;
		this.filePath = filePath;
		this.access = access;
		this.likeCheck = likeCheck;
		this.order = order;
	}

	int access;
	int likeCheck;
	int order;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public int getLikeCheck() {
		return likeCheck;
	}

	public void setLikeCheck(int likeCheck) {
		this.likeCheck = likeCheck;
	}

	public Post(long idpost, long memberId, String postTitle, String postWriter, String dateCreated,
			String updateCreated, String code, int likeCount, int views, String imagePath, int access, int likeCheck,
			int order) {
		super();
		this.idpost = idpost;
		this.memberId = memberId;
		this.postTitle = postTitle;
		this.postWriter = postWriter;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.code = code;
		this.likeCount = likeCount;
		this.views = views;
		this.imagePath = imagePath;
		this.access = access;
		this.likeCheck = likeCheck;
		this.order = order;
	}

	public Post(long idpost, long memberId, String postTitle, String postWriter, String dateCreated,
			String updateCreated, String code, int likeCount, int views, String imagePath, int access, int likeCheck) {
		super();
		this.idpost = idpost;
		this.memberId = memberId;
		this.postTitle = postTitle;
		this.postWriter = postWriter;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.code = code;
		this.likeCount = likeCount;
		this.views = views;
		this.imagePath = imagePath;
		this.access = access;
		this.likeCheck = likeCheck;
	}

	public Post() {
		super();
	}

	public long getIdpost() {
		return idpost;
	}

	public void setIdpost(long idpost) {
		this.idpost = idpost;
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

	public String getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(String s){
		this.dateCreated = s;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getAccess() {
		return access;
	}

	public void setAccess(int access) {
		this.access = access;
	}

	@Override
	public String toString() {
		return "Post [idpost=" + idpost + ", memberId=" + memberId + ", postTitle=" + postTitle + ", postWriter="
				+ postWriter + ", dateCreated=" + dateCreated + ", updateCreated=" + updateCreated + ", code=" + code
				+ ", likeCount=" + likeCount + ", views=" + views + ", imagePath=" + imagePath + ", filePath="
				+ filePath + ", access=" + access + ", likeCheck=" + likeCheck + ", order=" + order + "]";
	}

	public Post(long idpost, long memberId, String postTitle, String postWriter, String dateCreated,
			String updateCreated, String code, int likeCount, int views, String imagePath, int access) {
		super();
		this.idpost = idpost;
		this.memberId = memberId;
		this.postTitle = postTitle;
		this.postWriter = postWriter;
		this.dateCreated = dateCreated;
		this.updateCreated = updateCreated;
		this.code = code;
		this.likeCount = likeCount;
		this.views = views;
		this.imagePath = imagePath;
		this.access = access;
	}

}
