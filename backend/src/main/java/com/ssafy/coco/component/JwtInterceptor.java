package com.ssafy.coco.component;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

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
		System.out.println("---------------------------------");
		System.out.println("토큰 in 인터셉터:" + token);
		System.out.println("목적 주소:" + request.getServletPath());
		// System.out.println(request.getPathInfo());

//		if (token == null) {
//			System.out.println("토큰값이 null 입니다.");
//			System.out.println(token);
//			return false;
//		} else {
//			HttpStatus status = jwtService.isUsable(token);
//			System.out.println(status);
//			System.out.println(token);
//			if (status == HttpStatus.ACCEPTED) {
//				return true;
//			}
//			else return false;
//		}
		
		 return true;

	}
}
