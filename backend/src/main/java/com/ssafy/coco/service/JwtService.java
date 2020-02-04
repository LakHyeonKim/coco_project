package com.ssafy.coco.service;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;

import com.ssafy.coco.vo.Tokens;

public interface JwtService {
	public String makeJwt(HttpServletRequest res) throws Exception;
	public String makeJwt(String id, String pwd) throws Exception;
	public String makeJwt(String idmember, int time) throws Exception;
	public boolean checkJwt(String jwt) throws Exception;
	public HttpStatus checkJwt2(String jwt) throws Exception;
	public boolean getAccessTokenByRefreshToken(@PathVariable String tt) throws Exception;
	public Tokens login(JSONObject input) throws Exception;
}
