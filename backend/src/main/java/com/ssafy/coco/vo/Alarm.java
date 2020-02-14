package com.ssafy.coco.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;

public class Alarm {
	long idalarm;
	long memberCaller;
	long memberReceiver;
	long postId;
	long likeId;
	long followId;
	int isRead;//1 : 확인 //  0 : 확인 안함
	int access;//0:기본값  // 1:비공개 //  2:삭제
	String dateCreated;
	
	public Alarm() {
		super();
	}


	public Alarm(long idalarm, long memberCaller, long memberReceiver, long postId, long likeId, long followId,
			int isRead, int access, String dateCreated) {
		super();
		this.idalarm = idalarm;
		this.memberCaller = memberCaller;
		this.memberReceiver = memberReceiver;
		this.postId = postId;
		this.likeId = likeId;
		this.followId = followId;
		this.isRead = isRead;
		this.access = access;
		this.dateCreated = dateCreated;
	}
	public Alarm(long idalarm, long memberCaller, long memberReceiver, long postId, long likeId, long followId,
			int isRead, int access) {
		super();
		this.idalarm = idalarm;
		this.memberCaller = memberCaller;
		this.memberReceiver = memberReceiver;
		this.postId = postId;
		this.likeId = likeId;
		this.followId = followId;
		this.isRead = isRead;
		this.access = access;
	}

	@Override
	public String toString() {
		return "Alarm [idalarm=" + idalarm + ", memberCaller=" + memberCaller + ", memberReceiver=" + memberReceiver
				+ ", postId=" + postId + ", likeId=" + likeId + ", followId=" + followId + ", isRead=" + isRead
				+ ", access=" + access + ", dateCreated=" + dateCreated + "]";
	}


	public long getIdalarm() {
		return idalarm;
	}


	public void setIdalarm(long idalarm) {
		this.idalarm = idalarm;
	}


	public long getMemberCaller() {
		return memberCaller;
	}


	public void setMemberCaller(long memberCaller) {
		this.memberCaller = memberCaller;
	}


	public long getMemberReceiver() {
		return memberReceiver;
	}


	public void setMemberReceiver(long memberReceiver) {
		this.memberReceiver = memberReceiver;
	}


	public long getPostId() {
		return postId;
	}


	public void setPostId(long postId) {
		this.postId = postId;
	}


	public long getLikeId() {
		return likeId;
	}


	public void setLikeId(long likeId) {
		this.likeId = likeId;
	}


	public long getFollowId() {
		return followId;
	}


	public void setFollowId(long followId) {
		this.followId = followId;
	}


	public int getIsRead() {
		return isRead;
	}


	public void setIsRead(int isRead) {
		this.isRead = isRead;
	}


	public int getAccess() {
		return access;
	}


	public void setAccess(int access) {
		this.access = access;
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

	
	
}
