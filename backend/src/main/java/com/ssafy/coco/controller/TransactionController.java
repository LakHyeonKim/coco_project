package com.ssafy.coco.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.relationvo.BabyBoardWrite;
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.relationvo.BoardDetail;
import com.ssafy.coco.relationvo.BoardWrite;
import com.ssafy.coco.relationvo.DoublePost;
import com.ssafy.coco.relationvo.MemberInfoModify;
import com.ssafy.coco.relationvo.PostAndMember;
import com.ssafy.coco.relationvo.SignUpMember;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.TransactionService;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostWithTag;
import com.ssafy.coco.vo.Tokens;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
@RestController
@RequestMapping("/trc")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "Transaction Controller" }, description = "트랜잭션 함수 컨트롤러")
public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private JwtService jwtService;
	
	@ApiOperation(value = "코멘트 지우기 (Transaction)", response = List.class)
	@RequestMapping(value = "/deleteComment", method = RequestMethod.POST)
	public ResponseEntity<Integer> deleteComment(@RequestHeader(value="Authorization")String jwt,@RequestBody JSONObject input) throws Exception {
		Map<String, Object> map = jwtService.getMapFromJsonObject(input);
		Integer postId = (Integer) map.get("postId");
		Integer receiver = (Integer) map.get("receiver");
		Integer caller = (Integer) map.get("caller");
		Integer commentId = (Integer) map.get("commentId");
		
		transactionService.deleteComment(postId, receiver, caller, commentId);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}

	@ApiOperation(value = "코멘트 달기 (Transaction)", response = List.class)
	@RequestMapping(value = "/makeComment", method = RequestMethod.POST)
	public ResponseEntity<Long> makeComment(@RequestHeader(value="Authorization")String jwt,@RequestBody BoardDetail board) throws Exception {
		long idComment = transactionService.makeComment(board.getComments().get(0),board.getPost().getMemberId());
		return new ResponseEntity<Long>(idComment, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 달기 (Transaction) ", response = List.class)
	@PostMapping("/makePost")
	public ResponseEntity<Integer> makePost(@RequestHeader(value="Authorization")String jwt,BoardWrite board) throws Exception {
		System.out.println("메이크 포스터안 jwt:"+jwt);
		transactionService.makePost(board);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "하위 포스트 달기 (Transaction) ", response = List.class)
	@RequestMapping(value = "/makeBabyPost", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeBabyPost(@RequestHeader(value="Authorization")String jwt, BabyBoardWrite babyBoardWrite) throws Exception {
		transactionService.makeBabyPost(babyBoardWrite);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 클릭시 작업 (Transaction) ", response = List.class)
	@RequestMapping(value = "/postClick", method = RequestMethod.POST)
	public ResponseEntity<Integer> postClick(@RequestHeader(value="Authorization")String jwt,@RequestBody PostAndMember pam) throws Exception {
		transactionService.postClick(pam.getPost(),pam.getMember().getIdmember());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "팔로우 하기(Transaction) ", response = List.class)
	@RequestMapping(value = "/makeFollow", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeFollow(@RequestHeader(value="Authorization")String jwt,@RequestBody Follow follow) throws Exception {
		transactionService.makeFollow(follow.getMemberFollower(),follow.getMemberFollowing());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "팔로우 취소(Transaction) ", response = List.class)
	@RequestMapping(value = "/makeUnFollow", method = RequestMethod.POST)
	public ResponseEntity<Integer> makeUnFollow(@RequestHeader(value="Authorization")String jwt,@RequestBody Follow follow) throws Exception {
		transactionService.makeUnFollow(follow.getMemberFollower(),follow.getMemberFollowing());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아요 누르기 (Transaction) ", response = List.class)
	@RequestMapping(value = "/pushLike", method = RequestMethod.POST)
	public ResponseEntity<Integer> pushLike(@RequestHeader(value="Authorization")String jwt,@RequestBody PostAndMember pam) throws Exception {
		transactionService.pushLike(pam.getPost().getIdpost(),pam.getMember().getIdmember());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "좋아요 취소 (Transaction) ", response = List.class)
	@RequestMapping(value = "/unLike", method = RequestMethod.POST)
	public ResponseEntity<Integer> unLike(@RequestHeader(value="Authorization")String jwt,@RequestBody PostAndMember pam) throws Exception {
		transactionService.unLike(pam.getPost().getIdpost(),pam.getMember().getIdmember());
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "member 정보 수정 (Transaction) ", response = List.class)
	@RequestMapping(value = "/updateMemeberInfo", method = RequestMethod.POST)
	public ResponseEntity<Integer> updateMemeberInfo(@RequestHeader(value="Authorization")String jwt, MemberInfoModify memberInfoModify) throws Exception {
		transactionService.updateMemeberInfo(memberInfoModify);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "member 정보 수정 이미지 삭제 (Transaction) ", response = List.class)
	@RequestMapping(value = "/deleteMemberProfile", method = RequestMethod.POST)
	public ResponseEntity<Integer> deleteMemberProfile(@RequestHeader(value="Authorization")String jwt, @RequestBody long idMember) throws Exception {
		System.out.println("dkdkdkd");
		transactionService.deleteMemberProfile(idMember);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "member 정보 수정 배너 이미지 삭제 (Transaction) ", response = List.class)
	@RequestMapping(value = "/deleteMemberBannerImage", method = RequestMethod.POST)
	public ResponseEntity<Integer> deleteMemberBannerImage(@RequestHeader(value="Authorization")String jwt, @RequestBody long idMember) throws Exception {
		transactionService.deleteMemberBannerImage(idMember);
		return new ResponseEntity<Integer>(HttpStatus.OK);
	} 
	
	/* 알아보고 삭제 해야 할 것*/
	
//	@ApiOperation(value = "테스트2", response = List.class)
//	@RequestMapping(value = "/makeTest2", method = RequestMethod.POST)
//	public ResponseEntity<Integer> makeComment(@RequestBody PostWithTag postWithTag) throws Exception {
//		System.out.println(123);
//		Post post = postWithTag.getPost();
//		String tag = postWithTag.getTags();
//		
//		Map<String, Object> map = null;
//		//map = new ObjectMapper().readValue(jSONObject.toJSONString(), Map.class);
//        
//		//Comment comment = (Comment) jSONObject.get("comment");
//		//getJsonStringFromMap(map.get("comment"));
//		//long receiver = (long) jSONObject.get("receiver");
////		System.out.println(comment);
////		System.out.println(receiver);
//		return new ResponseEntity<Integer>(1, HttpStatus.OK);
//	}
//	public static JSONObject getJsonStringFromMap( Map<String, Object> map )
//    {
//        JSONObject jsonObject = new JSONObject();
//        for( Map.Entry<String, Object> entry : map.entrySet() ) {
//            String key = entry.getKey();
//            Object value = entry.getValue();
//            jsonObject.put(key, value);
//        }
//        
//        return jsonObject;
//    }
}
