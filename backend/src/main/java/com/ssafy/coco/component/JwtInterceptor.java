package com.ssafy.coco.component;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.coco.service.JwtService;

@Component
public class JwtInterceptor implements HandlerInterceptor {

	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(HEADER_AUTH);
		System.out.println(request.getMethod());
		System.out.println("토큰 in 인터셉터:" + token);
		System.out.println("목적 주소:" + request.getServletPath());
		System.out.println(request.getMethod());
		// System.out.println(request.getPathInfo());
		if(request.getMethod().equals("OPTIONS")) return true;
		else
		{
			if(token.equals(""))
			{
				response.setStatus(203);
				return false;
			}
		}
		if (token == null) {
			System.out.println("토큰값이 null 입니다.");
			System.out.println(token);
			response.setStatus(203);
			return false;
		} else {
			HttpStatus status = jwtService.isUsable(token);
			System.out.println(status);
			System.out.println(token);
			if (status == HttpStatus.ACCEPTED) {
				response.setStatus(200);
				return true;
			}
			else 
				response.setStatus(203);
				return false;
		}
		// return true;

	}
}
