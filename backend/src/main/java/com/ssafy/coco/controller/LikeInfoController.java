package com.ssafy.coco.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.coco.relationvo.LikeInfo;
import com.ssafy.coco.relationvo.PostAndMember;
import com.ssafy.coco.service.LikeInfoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "LikeInfo Controller" }, description = "SSAFY HRM resource API (Test)")
public class LikeInfoController {
	@Autowired
	private LikeInfoService likeInfoService;
	
	@ApiOperation(value = "포스트의 좋아요누른 사람들 (나와 팔로우 유무 같이)", response = List.class)
	@RequestMapping(value = "/findWhoPressedTheLikeButton", method = RequestMethod.POST)
	public ResponseEntity<List<LikeInfo>> findWhoPressedTheLikeButton(@RequestHeader(value="Authorization")String jwt,@RequestBody PostAndMember postAndMember) {
		List<LikeInfo> answer = likeInfoService.findWhoPressedTheLikeButton(postAndMember);
		if (answer == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<LikeInfo>>(answer, HttpStatus.OK);
	}
}
