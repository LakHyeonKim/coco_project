//package com.ssafy.coco.controller;
//
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//
//@Controller
//public class RootController implements ErrorController{
//	
//	@GetMapping("/")
//	public String Root() {
//		return "index.html";
//	}
//	
//	@GetMapping("/error")
//	public String redirectRoot() {
//		return "index.html";
//	}
//
//	@Override
//	public String getErrorPath() {
//		return "/error";
//	}
//	
//}
