package com.ssafy.coco.service;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;

import com.fasterxml.jackson.databind.JsonNode;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Tokens;

public interface JwtService {
	public String makeJwt(HttpServletRequest res) throws Exception;
	public String makeJwt(String id, String pwd) throws Exception;
	public String makeJwt(String idmember, int time) throws Exception;
	public String makeJwt(String idmember,String nickname, int time) throws Exception;
	public String makeJwt(String idmember,String nickname, int access, int time) throws Exception;
	public boolean checkJwt(String jwt) throws Exception;
	public HttpStatus checkJwt2(String jwt) throws Exception;
	public boolean getAccessTokenByRefreshToken(@PathVariable String tt) throws Exception;
	public Tokens login(String id, String password) throws Exception;
	public HttpStatus isUsable(String token)throws Exception;
	public Map<String, Object> getMapFromJsonObject( JSONObject jsonObj );
	public Member changeData(JsonNode userInfo);
	public JsonNode getKakaoUserInfo(String autorize_code);
	public JsonNode getAccessToken(String autorize_code);
	public int getIdmemberByToken(String jwt) throws Exception;
}
