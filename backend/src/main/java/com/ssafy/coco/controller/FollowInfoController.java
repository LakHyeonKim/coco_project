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

import com.ssafy.coco.relationvo.FollowInfo;
import com.ssafy.coco.relationvo.MemberHomePageSearch;
import com.ssafy.coco.service.FollowInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "FollowInfo Controller" }, description = "SSAFY HRM resource API (Test)")
public class FollowInfoController {
	@Autowired
	private FollowInfoService followInfoService;
	
	@ApiOperation(value = "사용자의 팔로우 리스트를 가지고옴 (나와 팔로우 유무 같이)", response = List.class)
	@RequestMapping(value = "/findByFollowingListMember", method = RequestMethod.POST)
	public ResponseEntity<List<FollowInfo>> findByFollowingListMember(@RequestBody MemberHomePageSearch memberHomePageSearch) {
		List<FollowInfo> answer = followInfoService.findByFollowingListMember(memberHomePageSearch.getMyIdMemeber(), memberHomePageSearch.getYouIdMember());
		if (answer == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<FollowInfo>>(answer, HttpStatus.OK);
	}
	
	@ApiOperation(value = "사용자의 팔로워 리스트를 가지고옴 (나와 팔로우 유무 같이)", response = List.class)
	@RequestMapping(value = "/findByFollowerListMember", method = RequestMethod.POST)
	public ResponseEntity<List<FollowInfo>> findByFollowerListMember(@RequestBody MemberHomePageSearch memberHomePageSearch) {
		List<FollowInfo> answer = followInfoService.findByFollowerListMember(memberHomePageSearch.getMyIdMemeber(), memberHomePageSearch.getYouIdMember());
		if (answer == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<FollowInfo>>(answer, HttpStatus.OK);
	}
}
