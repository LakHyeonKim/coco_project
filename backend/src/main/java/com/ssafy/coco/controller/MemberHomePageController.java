package com.ssafy.coco.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.relationvo.BoardDetail;
import com.ssafy.coco.relationvo.BoardDetailSearch;
import com.ssafy.coco.relationvo.MemberHomePage;
import com.ssafy.coco.service.BoardDetailSerivce;
import com.ssafy.coco.service.MemberHomePageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "MemberHomePage Controller" }, description = "SSAFY HRM resource API (Test)")
public class MemberHomePageController {
	@Autowired
	private MemberHomePageService memberHomePageService;
	
	@ApiOperation(value = "멤버의 id(String)을 가지고 mypage정보를 가지고 옴", response = List.class)
	@RequestMapping(value = "/findByMemberHomePageUserID", method = RequestMethod.POST)
	public ResponseEntity<MemberHomePage> findByMemberHomePageUserID(@RequestBody String id) {
		MemberHomePage answer = memberHomePageService.findByMemberHomePageUserID(id);
		if (answer == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<MemberHomePage>(answer, HttpStatus.OK);
	}
}
