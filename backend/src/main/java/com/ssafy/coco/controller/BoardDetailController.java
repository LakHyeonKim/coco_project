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
import com.ssafy.coco.service.BoardDetailSerivce;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "BoardDetail Controller" }, description = "SSAFY HRM resource API (Test)")
public class BoardDetailController {
	@Autowired
	private BoardDetailSerivce boardDetailService;
	
	@ApiOperation(value = "포스트 아이디(pk)를 해당 포스트 상세 페이지 가지고 옴", response = List.class)
	@RequestMapping(value = "/findByBoardDetailPostId", method = RequestMethod.POST)
	public ResponseEntity<BoardDetail> findByBoardDetailPostId(@RequestBody BoardDetailSearch boardDetailSearch) {
		BoardDetail answer = boardDetailService.findByBoardDetailPostId(boardDetailSearch.getIdPost(), boardDetailSearch.getIdMember());
		if (answer == null) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<BoardDetail>(answer, HttpStatus.OK);
	}
}
