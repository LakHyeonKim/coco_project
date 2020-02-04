package com.ssafy.coco.controller;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.relationvo.DoublePost;
import com.ssafy.coco.relationvo.PostAndMember;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.TransactionService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostWithTag;
import com.ssafy.coco.vo.Tag;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/trc")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "Transaction Controller" }, description = "SSAFY HRM resource API (Test)")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;

	@ApiOperation(value = "가입 하기", response = List.class)
	@RequestMapping(value = "/signUp", method = RequestMethod.POST)
	public ResponseEntity<Integer> signUp(@RequestBody Member member) throws Exception {
		int answer = transactionService.signUp(member);
		if (answer<=0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}
	
	@ApiOperation(value = "코멘트 달기 (Transaction)", response = List.class)
	@RequestMapping(value = "/makeComment", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeComment(@RequestBody Board board) throws Exception {
		transactionService.makeComment(board.getComments().get(0),board.getPost().getMemberId());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 달기 (Transaction) ", response = List.class)
	@RequestMapping(value = "/makeTagsFromPost", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeTagsFromPost(@RequestBody Board board) throws Exception {
		transactionService.makeTagsFromPost(board.getPost(),board.getTags());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "하위 포스트 달기 (Transaction) ", response = List.class)
	@RequestMapping(value = "/makeBabyPost", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeBabyPost(@RequestBody DoublePost doublePost) throws Exception {
		transactionService.makeBabyPost(doublePost.getSon(),doublePost.getParent());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 클릭시 작업 (Transaction) ", response = List.class)
	@RequestMapping(value = "/postClick", method = RequestMethod.POST)
	public ResponseEntity<Integer> postClick(@RequestBody PostAndMember pam) throws Exception {
		transactionService.postClick(pam.getPost(),pam.getMember().getIdmember());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "팔로우 하기(Transaction) ", response = List.class)
	@RequestMapping(value = "/makeFollow", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeFollow(@RequestBody Follow follow) throws Exception {
		transactionService.makeFollow(follow.getMemberFollower(),follow.getMemberFollowing());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아요 누르기 (Transaction) ", response = List.class)
	@RequestMapping(value = "/pushLike", method = RequestMethod.POST)
	public ResponseEntity<Integer> pushLike(@RequestBody PostAndMember pam) throws Exception {
		transactionService.pushLike(pam.getPost().getIdpost(),pam.getMember().getIdmember());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	
	
	
	
	@ApiOperation(value = "테스트2", response = List.class)
	@RequestMapping(value = "/makeTest2", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeComment(@RequestBody PostWithTag postWithTag) throws Exception {
		System.out.println(123);
		Post post = postWithTag.getPost();
		String tag = postWithTag.getTags();
		
		Map<String, Object> map = null;
		//map = new ObjectMapper().readValue(jSONObject.toJSONString(), Map.class);
        
		//Comment comment = (Comment) jSONObject.get("comment");
		//getJsonStringFromMap(map.get("comment"));
		//long receiver = (long) jSONObject.get("receiver");
//		System.out.println(comment);
//		System.out.println(receiver);
		return new ResponseEntity<Integer>(1, HttpStatus.OK);
	}
	public static JSONObject getJsonStringFromMap( Map<String, Object> map )
    {
        JSONObject jsonObject = new JSONObject();
        for( Map.Entry<String, Object> entry : map.entrySet() ) {
            String key = entry.getKey();
            Object value = entry.getValue();
            jsonObject.put(key, value);
        }
        
        return jsonObject;
    }
}
