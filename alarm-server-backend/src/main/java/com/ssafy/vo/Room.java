package com.ssafy.vo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.UUID;

public class Room {
	private long idroom;
	private long memberId;
	private String roomName;
	private String dateCreated;
	
	public long getIdroom() {
		return idroom;
	}

	public void setIdroom(long idroom) {
		this.idroom = idroom;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long member_id) {
		this.memberId = member_id;
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	@SuppressWarnings("deprecation")
	public void setDateCreated(Timestamp dateCreated) {
		java.sql.Timestamp timestamp_1 = java.sql.Timestamp.valueOf(LocalDateTime.now());
		long l = (timestamp_1.getTime() - dateCreated.getTime());
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
			this.dateCreated = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분").format(dateCreated);
	}


	public Room() {
		super();
	}

	public Room(long idroom, long memberId, String roomName, String dateCreated) {
		super();
		this.idroom = idroom;
		this.memberId = memberId;
		this.roomName = roomName;
		this.dateCreated = dateCreated;
	}

	@Override
	public String toString() {
		return "Room [idroom=" + idroom + ", memberId=" + memberId + ", roomName=" + roomName + ", dateCreated="
				+ dateCreated + "]";
	}
}
