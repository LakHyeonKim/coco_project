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
import com.ssafy.coco.relationvo.OrderSearch;
import com.ssafy.coco.relationvo.OrderSearchKeyword;
import com.ssafy.coco.relationvo.PostAndMember;
import com.ssafy.coco.service.BoardService;
import com.ssafy.coco.service.JwtService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "Board Controller" }, description = "Board(게시글 종합) 컨트롤러")

public class BoardController {
	@Autowired
	private BoardService boardService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "사용자가 팔로우 한 사람들의 뉴스피드 (뉴스피드 페이지용)", response = List.class)
	@RequestMapping(value = "/findByAllNewsfeed", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByAllNewsfeed(@RequestHeader(value="Authorization")String jwt,@RequestBody long idMember) throws Exception {
		List<Board> answers = boardService.findByAllNewsfeed(idMember);
			if (answers.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "사용자가 팔로우 한 사람들의 뉴스피드 다운 스크롤 (뉴스피드 페이지용)", response = List.class)
	@RequestMapping(value = "/findByAllNewsfeedScrollDown", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByAllNewsfeedScrollDown(@RequestHeader(value="Authorization")String jwt, @RequestBody PostAndMember postAndMember) throws Exception {
		boolean isAble = jwtService.checkJwt(jwt);
		if(isAble)
		{
			List<Board> answers = boardService.findByAllNewsfeedScrollDown(postAndMember.getMember().getIdmember(),postAndMember.getPost().getIdpost());
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
	public ResponseEntity<List<Board>> findByAllDefaultSearch(@RequestHeader(value="Authorization")String jwt,@RequestBody long idMember) throws Exception {
		System.out.println(jwt);
		List<Board> answers = boardService.findByAllDefaultSearch(idMember);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	

	@ApiOperation(value = "사용자가 작성한 포스트를 가져옴([order param] 1: 좋아요 오름차순, 2: 좋아요 내림차순, 3: 날짜 오래된 순서, 4: 날짜 최신 순서)", response = List.class)
	@RequestMapping(value = "/findByMyPosts", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByMyPosts(@RequestHeader(value="Authorization")String jwt,@RequestBody OrderSearch orderSearch) throws Exception {
		System.out.println("토큰 in findbymypost :"+jwt);
		List<Board> answers = boardService.findByMyPosts(orderSearch.getMyIdMember(), orderSearch.getYouIdMember(), orderSearch.getOrder());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 전체 검색 (태그, 포스트 제목, 포스트 내용, 작성자 구분 없이 가져옴)", response = List.class)
	@RequestMapping(value = "/findByAllKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByAllKeyword(@RequestHeader(value="Authorization")String jwt,@RequestBody KeywordSearch keyWordSearch) throws Exception {
		System.out.println("int controller"+jwt);
		List<Board> answers = boardService.findByAllKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마이페이지 포스트 전체 검색 (태그, 포스트 제목, 포스트 내용, 작성자 구분 없이 가져옴)", response = List.class)
	@RequestMapping(value = "/findByAllKeywordMyPosts", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByAllKeywordMyPosts(@RequestHeader(value="Authorization")String jwt,@RequestBody OrderSearchKeyword orderSearchKeyword) throws Exception {
		System.out.println("int controller"+jwt);
		List<Board> answers = boardService.findByAllKeywordMyPosts(orderSearchKeyword);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 태그로 검색", response = List.class)
	@RequestMapping(value = "/findByTagKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByTagKeyword(@RequestHeader(value="Authorization")String jwt,@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByTagKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마이페이지 포스트 태그로 검색", response = List.class)
	@RequestMapping(value = "/findByTagKeywordMyPosts", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByTagKeywordMyPosts(@RequestHeader(value="Authorization")String jwt,@RequestBody OrderSearchKeyword orderSearchKeyword) throws Exception {
		List<Board> answers = boardService.findByTagKeywordMyPosts(orderSearchKeyword);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 타이틀로 검색", response = List.class)
	@RequestMapping(value = "/findByPostTitleKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostTitleKeyword(@RequestHeader(value="Authorization")String jwt,@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByPostTitleKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마이페이지 포스트 타이틀로 검색", response = List.class)
	@RequestMapping(value = "/findByPostTitleKeywordMyPosts", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostTitleKeywordMyPosts(@RequestHeader(value="Authorization")String jwt,@RequestBody OrderSearchKeyword orderSearchKeyword) throws Exception {
		List<Board> answers = boardService.findByPostTitleKeywordMyPosts(orderSearchKeyword);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 코드로 검색", response = List.class)
	@RequestMapping(value = "/findByPostCodeKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostCodeKeyword(@RequestHeader(value="Authorization")String jwt,@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByPostCodeKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마이페이지 포스트 코드로 검색", response = List.class)
	@RequestMapping(value = "/findByPostCodeKeywordMyPosts", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostCodeKeywordMyPosts(@RequestHeader(value="Authorization")String jwt,@RequestBody OrderSearchKeyword orderSearchKeyword) throws Exception {
		List<Board> answers = boardService.findByPostCodeKeywordMyPosts(orderSearchKeyword);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 작성자로 검색", response = List.class)
	@RequestMapping(value = "/findByPostWriterKeyword", method = RequestMethod.POST)
	public ResponseEntity<List<Board>> findByPostWriterKeyword(@RequestHeader(value="Authorization")String jwt,@RequestBody KeywordSearch keyWordSearch) throws Exception {
		List<Board> answers = boardService.findByPostWriterKeyword(keyWordSearch.getIdMember(), keyWordSearch.getKeyWord());
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Board>>(answers, HttpStatus.OK);
	}

	
}
