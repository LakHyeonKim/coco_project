package com.ssafy.vo;


public class Message{

	// 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK
    }
    
    private long idmessage;
    private long roomId; // 방번호
    private long memberId; // 메시지 보낸사람 id
    private String nickName; // nickName
    private String dateCreated;
    private String context; // 메시지
    private MessageType type; // 메시지 타입
    
	public Message() {
		super();
	}

	public Message(long idmessage, long roomId, long memberId, String nickName, String dateCreated, String context,
			MessageType type) {
		super();
		this.idmessage = idmessage;
		this.roomId = roomId;
		this.memberId = memberId;
		this.nickName = nickName;
		this.dateCreated = dateCreated;
		this.context = context;
		this.type = type;
	}


	public long getIdmessage() {
		return idmessage;
	}

	public void setIdmessage(long idmessage) {
		this.idmessage = idmessage;
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

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getContext() {
		return context;
	}

	public void setContext(String context) {
		this.context = context;
	}

	public MessageType getType() {
		return type;
	}

	public void setType(MessageType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Message [idmessage=" + idmessage + ", roomId=" + roomId + ", memberId=" + memberId + ", nickName="
				+ nickName + ", dateCreated=" + dateCreated + ", context=" + context + ", type=" + type + "]";
	}
}
