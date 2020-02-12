package com.ssafy.coco.component;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.vo.MemberTag;

@Component
public class JwtInterceptor implements HandlerInterceptor{
	
	private static final String HEADER_AUTH = "Authorization";

	@Autowired
	private JwtService jwtService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		final String token = request.getHeader(HEADER_AUTH);
		
		System.out.println("토ㅡㄴ"+token);
		System.out.println(request.getPathInfo());
		Enumeration params = request.getParameterNames();
		System.out.println("----------------------------");
		while (params.hasMoreElements()){
		    String name = (String)params.nextElement();
		    System.out.println(name + " : " +request.getParameter(name));
		}
		System.out.println("----------------------------");
		
		if(token != null && jwtService.isUsable(token)){
			System.out.println("토큰가능");
			System.out.println(token);
			return true;
		}else{
			System.out.println("토큰불가능");
			System.out.println(token);
			return false;
		}
	}
}
