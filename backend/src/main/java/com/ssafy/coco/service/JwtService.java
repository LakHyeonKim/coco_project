package com.ssafy.coco.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;

public interface JwtService {
	public String makeJwt(HttpServletRequest res) throws Exception;
	public String makeJwt(String id, String pwd) throws Exception;
	public String makeJwt(String idmember) throws Exception;
	public boolean checkJwt(String jwt) throws Exception;
	public HttpStatus checkJwt2(String jwt) throws Exception;
	public boolean getAccessTokenByRefreshToken(@PathVariable String tt) throws Exception;
}
