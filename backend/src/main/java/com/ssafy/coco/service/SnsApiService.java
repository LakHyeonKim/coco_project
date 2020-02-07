package com.ssafy.coco.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface SnsApiService {
	public String getAccessToken(String authorize_code);
	public JsonNode getKakaoAccessToken(String code);
	public JsonNode getKakaoUserInfo(JsonNode accessToken);
}
