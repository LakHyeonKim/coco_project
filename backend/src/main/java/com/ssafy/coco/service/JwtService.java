package com.ssafy.coco.service;

import javax.servlet.http.HttpServletRequest;

public interface JwtService {
	public String makeJwt(HttpServletRequest res) throws Exception;
	public String makeJwt(String id, String pwd) throws Exception;
	public String makeJwt(String idmember) throws Exception;
	public boolean checkJwt(String jwt) throws Exception;
	
}
