package com.ssafy.coco.relationvo;

import com.ssafy.coco.vo.Member;

public class MemberWithToken {
	private Member member;
	private String accessToken;
	private String refreshToken;
	public MemberWithToken(Member member, String accessToken, String refreshToken) {
		super();
		this.member = member;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	public MemberWithToken() {
		super();
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "MemberWithToken [member=" + member + ", accessToken=" + accessToken + ", refreshToken=" + refreshToken
				+ "]";
	}
	
}
