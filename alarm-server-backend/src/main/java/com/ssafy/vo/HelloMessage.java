package com.ssafy.vo;

public class HelloMessage {
	private long memberId;
	
	public HelloMessage() {
		super();
	}

	public HelloMessage(long memberId) {
		super();
		this.memberId = memberId;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	@Override
	public String toString() {
		return "HelloMessage [memberId=" + memberId + "]";
	}
}
