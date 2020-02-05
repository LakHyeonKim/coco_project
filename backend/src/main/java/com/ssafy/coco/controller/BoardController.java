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

import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.relationvo.KeywordSearch;
import com.ssafy.coco.service.BoardService;
import com.ssafy.coco.service.JwtService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "Board Controller" }, description = "SSAFY HRM resource API (Test)")

public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "사용자가 팔로우 한 사람들의 뉴스피드 (뉴스피드 페이지용)", response = List.class)
	@RequestMapping(value = "/findByAllNewsfeed", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByAllNewsfeed(@RequestHeader(value="Authorization")String jwt, @RequestBody long idMember) throws Exception {
		boolean isAble = jwtService.checkJwt(jwt);
		if(isAble)
		{
			List<Board> answers = boardService.findByAllNewsfeed(idMember);
			if (answers.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
		}
		else return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "사용자가 팔로우 한 사람들의 뉴스피드 (뉴스피드 페이지용)", response = List.class)
	@RequestMapping(value = "/findPostByNewsfeedOrderByLike", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findPostByNewsfeedOrderByLike(@RequestHeader(value="Authorization")String jwt,@RequestBody long idMember) throws Exception {
		boolean isAble = jwtService.checkJwt(jwt);
		if(isAble)
		{
			List<Board> answers = boardService.findPostByNewsfeedOrderByLike(idMember);
			if (answers.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
		}
		else return new ResponseEntity(HttpStatus.BAD_REQUEST);
	}
	
	@ApiOperation(value = "사용자의 선호 태그 기반으로 모두 찾아줌 (검색 페이지용)", response = List.class)
	@RequestMapping(value = "/findByAllDefaultSearch", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByAllDefaultSearch(@RequestBody long idMember) throws Exception {
		List<Board> answers = boardService.findByAllDefaultSearch(idMember);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	/* 수정 해야 할 부분*/
//	@ApiOperation(value = "사용자가 작성한 포스트를 가져옴", response = List.class)
//	@RequestMapping(value = "/findByMyPosts", method = RequestMethod.POST)
//	public ResponseEntity<List<Board>> findByMyPosts(@RequestBody long idMember) throws Exception {
//		List<Board> answers = boardService.findByMyPosts();
//		if (answers.isEmpty()) {
//			return new ResponseEntity(HttpStatus.NO_CONTENT);
//		}
//		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
//	}
	

	@ApiOperation(value = "포스트 전체 검색 (태그, 포스트 제목, 포스트 내용, 작성자 구분 없이 가져옴)", response = List.class)
	@RequestMapping(value = "/findByAllKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByAllKeyword(@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByAllKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 태그로 검색", response = List.class)
	@RequestMapping(value = "/findByTagKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByTagKeyword(@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByTagKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 타이틀로 검색", response = List.class)
	@RequestMapping(value = "/findByPostTitleKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostTitleKeyword(@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByPostTitleKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 코드로 검색", response = List.class)
	@RequestMapping(value = "/findByPostCodeKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostCodeKeyword(@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByPostCodeKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 작성자로 검색", response = List.class)
	@RequestMapping(value = "/findByPostWriterKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostWriterKeyword(@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByPostWriterKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}

	
}
