package com.ssafy.vo;

import java.sql.Timestamp;
import java.util.UUID;

public class Room {
	private long idroom;
	private long memberId;
	private String roomNumber;
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

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
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

	public Room(long idroom, long member_id, String roomNumber, String roomName, Timestamp dateCreated) {
		super();
		this.idroom = idroom;
		this.memberId = member_id;
		this.roomNumber = roomNumber;
		this.roomName = roomName;
		this.dateCreated = dateCreated;
	}

	public static Room create(Room room) {
		Room chatRoom = new Room();
		chatRoom.roomNumber = UUID.randomUUID().toString();
		chatRoom.roomName = room.getRoomName();
		chatRoom.memberId = room.getMemberId();
		return chatRoom;
	}
}
