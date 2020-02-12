package com.ssafy.coco.vo;

public class Tokens {
	private String accessToken;
	private String refreshToken;
	public Tokens(String accessToken, String refreshToken) {
		super();
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}
	@Override
	public String toString() {
		return "Tokens [accessToken=" + accessToken + ", refreshToken=" + refreshToken + "]";
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
	
}
