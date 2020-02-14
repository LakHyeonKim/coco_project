package com.ssafy.vo;

import java.sql.Timestamp;
import java.util.UUID;

public class Room {
	private long idroom;
	private long memberId;
	private String roomName;
	private Timestamp dateCreated;
	
	
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

	public Timestamp getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Timestamp dateCreated) {
		this.dateCreated = dateCreated;
	}


	public Room() {
		super();
	}

	public Room(long idroom, long memberId, String roomName, Timestamp dateCreated) {
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
