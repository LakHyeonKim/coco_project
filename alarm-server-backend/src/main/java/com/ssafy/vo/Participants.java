package com.ssafy.vo;

public class Participants {
	private long idparticipants;
	private long roomId;
	private long memberId;
	private String nickName;
	public Participants(long idparticipants, long roomId, long memberId, String nickName) {
		super();
		this.idparticipants = idparticipants;
		this.roomId = roomId;
		this.memberId = memberId;
		this.nickName = nickName;
	}
	public Participants() {
		super();
	}
	public long getIdparticipants() {
		return idparticipants;
	}
	public void setIdparticipants(long idparticipants) {
		this.idparticipants = idparticipants;
	}
	public long getRoomId() {
		return roomId;
	}
	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}
	public long getMemberId() {
		return memberId;
	}
	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	@Override
	public String toString() {
		return "Participants [idparticipants=" + idparticipants + ", roomId=" + roomId + ", memberId=" + memberId
				+ ", nickName=" + nickName + "]";
	}
	
	
}
