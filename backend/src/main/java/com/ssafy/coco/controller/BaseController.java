package com.ssafy.coco.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.coco.service.AlarmService;
import com.ssafy.coco.service.BabyPostService;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.service.LikeService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.MemberTagService;
import com.ssafy.coco.service.MyPageService;
import com.ssafy.coco.service.MyPageTagService;
import com.ssafy.coco.service.PostService;
import com.ssafy.coco.service.PostTagService;
import com.ssafy.coco.service.TagService;
import com.ssafy.coco.service.WordDictionaryService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.MypageTag;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostTag;
import com.ssafy.coco.vo.Tag;
import com.ssafy.coco.vo.WordDictionary;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@Api(tags = { "Base Controller" }, description = "기본적인 DB 접근 CRUD")
public class BaseController {

	@Autowired
	private AlarmService alarmService;
	@Autowired
	private BabyPostService babyPostService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private FollowService followService;
	@Autowired
	private LikeService likeService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberTagService memberTagService;
	@Autowired
	private MyPageService myPageService;
	@Autowired
	private MyPageTagService myPageTagService;
	@Autowired
	private PostService postService;
	@Autowired
	private PostTagService postTagService;
	@Autowired
	private TagService tagService;
	@Autowired
	private WordDictionaryService wordDictionaryService;

	@SuppressWarnings("unchecked")
    public static Map<String, Object> getMapFromJsonObject( JSONObject jsonObj ) {
        Map<String, Object> map = null;
        try {
            map = new ObjectMapper().readValue(jsonObj.toJSONString(), Map.class) ;
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
	
	
	// 알람
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved list"),
			@ApiResponse(code = 401, message = "인증 오류 입니다"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "URL not found error") })
	@ApiOperation(value = "모든 알람 반환", response = List.class)
	@RequestMapping(value = "/findAllAlarm", method = RequestMethod.GET)
	public ResponseEntity<List<Alarm>> findAllAnswer() throws Exception {
		List<Alarm> answers = alarmService.findAllAlarm();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Alarm>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "비밀 번호 체크", response = List.class)
	@RequestMapping(value = "/checkPwd", method = RequestMethod.POST)
	public ResponseEntity<List<Alarm>> checkPwd(@RequestBody Member member) throws Exception {
		int size = memberService.findMember(member).size();
		if (size ==0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Alarm>>(HttpStatus.OK);
	}
	
	@ApiOperation(value = "알람 선택 반환", response = List.class)
	@RequestMapping(value = "/findAlarm", method = RequestMethod.POST)
	public ResponseEntity<List<Alarm>> findAlarm(@RequestBody Alarm alarm) throws Exception {
		List<Alarm> answers = alarmService.findAlarm(alarm);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Alarm>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "중복확인 ", response = List.class)
	@RequestMapping(value = "/check", method = RequestMethod.POST)
	public ResponseEntity<Boolean> check(@RequestBody JSONObject json) throws Exception {
		
		String id = (String)json.get("id");
		System.out.println("? " + json.get("id"));
		boolean answer = memberService.check(id);
		System.out.println(id);
		System.out.println(answer);
		return new ResponseEntity<Boolean>(answer, HttpStatus.OK);
	}

	@ApiOperation(value = "알람 입력", response = List.class)
	@RequestMapping(value = "/addAlarm", method = RequestMethod.POST)
	public ResponseEntity<Integer> addAlarm(@RequestBody Alarm alarm) throws Exception {
		int answer = alarmService.addAlarm(alarm);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	@ApiOperation(value = "알람 수정", response = List.class)
	@RequestMapping(value = "/updateAlarm", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateAlarm(@RequestBody Alarm alarm
			) throws Exception {
		int answer = alarmService.updateAlarm(alarm);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	@ApiOperation(value = "알람 삭제", response = List.class)
	@RequestMapping(value = "/deleteAlarm", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteAlarm(@RequestBody Alarm alarm) throws Exception {
		int answer = alarmService.deleteAlarm(alarm);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	// 베이비 포스트

	@ApiOperation(value = "모든 포스트 관계 반환", response = List.class)
	@RequestMapping(value = "/findAllBabyPost", method = RequestMethod.GET)
	public ResponseEntity<List<BabyPost>> findAllBabyPost() throws Exception {
		List<BabyPost> answers = babyPostService.findAllBabyPost();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BabyPost>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 포스트 관계 반환", response = List.class)
	@RequestMapping(value = "/findBabyPost", method = RequestMethod.POST)
	public ResponseEntity<List<BabyPost>> findBabyPost(@RequestBody BabyPost babyPost) throws Exception {
		List<BabyPost> answers = babyPostService.findBabyPost(babyPost);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<BabyPost>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "포스트 관계 등록", response = List.class)
	@RequestMapping(value = "/addBabyPost", method = RequestMethod.POST)
	public ResponseEntity<Integer> addBabyPost(@RequestBody BabyPost babyPost) throws Exception {
		Integer answer = babyPostService.addBabyPost(babyPost);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	@ApiOperation(value = "포스트 관계 수정", response = List.class)
	@RequestMapping(value = "/updateBabyPost", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateBabyPost(@RequestBody BabyPost babyPost) throws Exception {
		Integer answer = babyPostService.updateBabyPost(babyPost);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	@ApiOperation(value = " 포스트 관계 삭제", response = List.class)
	@RequestMapping(value = "/deleteBabyPost", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteBabyPost(@RequestBody BabyPost babyPost) throws Exception {
		Integer answer = babyPostService.deleteBabyPost(babyPost);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	// 댓글

	@ApiOperation(value = "모든 댓글 반환", response = List.class)
	@RequestMapping(value = "/findAllComment", method = RequestMethod.GET)
	public ResponseEntity<List<Comment>> findAllComment() throws Exception {
		List<Comment> answers = commentService.findAllComment();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Comment>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 댓글 반환", response = List.class)
	@RequestMapping(value = "/findComment", method = RequestMethod.POST)
	public ResponseEntity<List<Comment>> findComment(@RequestBody Comment comment) throws Exception {
		List<Comment> answers = commentService.findComment(comment);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Comment>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 등록", response = List.class)
	@RequestMapping(value = "/addComment", method = RequestMethod.POST)
	public ResponseEntity<Integer> addComment(@RequestBody Comment comment) throws Exception {
		Integer answer = commentService.addComment(comment);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 수정", response = List.class)
	@RequestMapping(value = "/updateComment", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateComment(@RequestBody Comment comment) throws Exception {
		Integer answer = commentService.updateComment(comment);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	@ApiOperation(value = "댓글 삭제", response = List.class)
	@RequestMapping(value = "/deleteComment", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteComment(@RequestBody Comment comment) throws Exception {
		Integer answer = commentService.deleteComment(comment);
		if (answer <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answer, HttpStatus.OK);
	}

	// 팔로우 관리

	@ApiOperation(value = "모든 팔로우 반환", response = List.class)
	@RequestMapping(value = "/findAllFollow", method = RequestMethod.GET)
	public ResponseEntity<List<Follow>> findAllFollow() throws Exception {
		List<Follow> answers = followService.findAllFollow();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Follow>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 팔로우 반환", response = List.class)
	@RequestMapping(value = "/findFollow", method = RequestMethod.POST)
	public ResponseEntity<List<Follow>> findFollow(@RequestBody Follow follow) throws Exception {
		List<Follow> answers = followService.findFollow(follow);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Follow>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 등록", response = List.class)
	@RequestMapping(value = "/addFollow", method = RequestMethod.POST)
	public ResponseEntity<Integer> addFollow(@RequestBody Follow follow) throws Exception {
		Integer answers = followService.addFollow(follow);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 수정", response = List.class)
	@RequestMapping(value = "/updateFollow", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateFollow(@RequestBody Follow follow) throws Exception {
		Integer answers = followService.updateFollow(follow);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "팔로우 삭제", response = List.class)
	@RequestMapping(value = "/deleteFollow", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteFollow(@RequestBody Follow follow) throws Exception {
		Integer answers = followService.deleteFollow(follow);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "모든 좋아요 반환", response = List.class)
	@RequestMapping(value = "/findAllLike", method = RequestMethod.GET)
	public ResponseEntity<List<Like>> findAllLike() throws Exception {
		List<Like> answers = likeService.findAllLike();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Like>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 좋아요 반환", response = List.class)
	@RequestMapping(value = "/findLike", method = RequestMethod.POST)
	public ResponseEntity<List<Like>> findLike(@RequestBody Like like) throws Exception {
		List<Like> answers = likeService.findLike(like);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Like>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 등록", response = List.class)
	@RequestMapping(value = "/addLike", method = RequestMethod.POST)
	public ResponseEntity<Integer> addLike(@RequestBody Like like) throws Exception {
		Integer answers = likeService.addLike(like);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 수정", response = List.class)
	@RequestMapping(value = "/updateLike", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateLike(@RequestBody Like like) throws Exception {
		Integer answers = likeService.updateLike(like);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "좋아요 삭제", response = List.class)
	@RequestMapping(value = "/deleteLike", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteLike(@RequestBody Like like) throws Exception {
		Integer answers = likeService.deleteLike(like);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "모든 멤버 반환", response = List.class)
	@RequestMapping(value = "/findAllMember", method = RequestMethod.GET)
	public ResponseEntity<List<Member>> findAllMember() throws Exception {
		List<Member> answers = memberService.findAllMember();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 멤버 반환", response = List.class)
	@RequestMapping(value = "/findMember", method = RequestMethod.POST)
	public ResponseEntity<List<Member>> findMember(@RequestBody Member member) throws Exception {
		List<Member> answers = memberService.findMember(member);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Member>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "멤버 등록", response = List.class)
	@RequestMapping(value = "/addMember", method = RequestMethod.POST)
	public ResponseEntity<Integer> addMember(@RequestBody Member member) throws Exception {
		System.out.println("before"+member);
		Integer answers = memberService.addMember(member);
		System.out.println("after"+member);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>((int) member.getIdmember(), HttpStatus.OK);
	}

	@ApiOperation(value = "멤버 수정", response = List.class)
	@RequestMapping(value = "/updateMember", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateMember(@RequestBody Member member) throws Exception {
		Integer answers = memberService.updateMember(member);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "멤버 삭제", response = List.class)
	@RequestMapping(value = "/deleteMember", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteMember(@RequestBody Member member) throws Exception {
		Integer answers = memberService.deleteMember(member);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "모든 멤버 태그 반환", response = List.class)
	@RequestMapping(value = "/findAllMemberTag", method = RequestMethod.GET)
	public ResponseEntity<List<MemberTag>> findAllMemberTag() throws Exception {
		List<MemberTag> answers = memberTagService.findAllMemberTag();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MemberTag>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 멤버 태그 반환", response = List.class)
	@RequestMapping(value = "/findMemberTag", method = RequestMethod.POST)
	public ResponseEntity<List<MemberTag>> findMemberTag(@RequestBody MemberTag memberTag) throws Exception {
		List<MemberTag> answers = memberTagService.findMemberTag(memberTag);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MemberTag>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "멤버 태그 등록", response = List.class)
	@RequestMapping(value = "/addMemberTag", method = RequestMethod.POST)
	public ResponseEntity<Integer> addMemberTag(@RequestBody MemberTag memberTag) throws Exception {
		Integer answers = memberTagService.addMemberTag(memberTag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "멤버 태그 수정", response = List.class)
	@RequestMapping(value = "/updateMemberTag", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateMemberTag(@RequestBody MemberTag memberTag) throws Exception {
		Integer answers = memberTagService.updateMemberTag(memberTag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "멤버 태그 삭제", response = List.class)
	@RequestMapping(value = "/deleteMemberTag", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteMemberTag(@RequestBody MemberTag memberTag) throws Exception {
		Integer answers = memberTagService.deleteMemberTag(memberTag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "모든 마이 페이지 반환", response = List.class)
	@RequestMapping(value = "/findAllMypage", method = RequestMethod.GET)
	public ResponseEntity<List<Mypage>> findAllMypage() throws Exception {
		List<Mypage> answers = myPageService.findAllMypage();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Mypage>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 마이 페이지 반환", response = List.class)
	@RequestMapping(value = "/findMypage", method = RequestMethod.POST)
	public ResponseEntity<List<Mypage>> findMypage(@RequestBody Mypage mypage) throws Exception {
		List<Mypage> answers = myPageService.findMypage(mypage);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Mypage>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "마이 페이지 등록", response = List.class)
	@RequestMapping(value = "/addMypage", method = RequestMethod.POST)
	public ResponseEntity<Integer> addMypage(@RequestBody Mypage mypage) throws Exception {
		Integer answers = myPageService.addMypage(mypage);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "마이 페이지 수정", response = List.class)
	@RequestMapping(value = "/updateMypage", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateMypage(@RequestBody Mypage mypage) throws Exception {
		Integer answers = myPageService.updateMypage(mypage);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "마이 페이지 삭제", response = List.class)
	@RequestMapping(value = "/deleteMypage", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteMypage(@RequestBody Mypage mypage) throws Exception {
		Integer answers = myPageService.deleteMypage(mypage);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "마이 페이지 태그 반환", response = List.class)
	@RequestMapping(value = "/findAllMypageTag", method = RequestMethod.GET)
	public ResponseEntity<List<MypageTag>> findAllMypageTag() throws Exception {
		List<MypageTag> answers = myPageTagService.findAllMypageTag();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MypageTag>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "선택 마이 페이지 페이지 반환", response = List.class)
	@RequestMapping(value = "/findMypageTag", method = RequestMethod.POST)
	public ResponseEntity<List<MypageTag>> findMypageTag(@RequestBody MypageTag mypageTag) throws Exception {
		List<MypageTag> answers = myPageTagService.findMypageTag(mypageTag);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<MypageTag>>(answers, HttpStatus.OK);
	}

	@ApiOperation(value = "마이 페이지 태그 등록", response = List.class)
	@RequestMapping(value = "/addMypageTag", method = RequestMethod.POST)
	public ResponseEntity<Integer> addMypageTag(@RequestBody MypageTag mypageTag) throws Exception {
		Integer answers = myPageTagService.addMypageTag(mypageTag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "마이 페이지 태그 삭제", response = List.class)
	@RequestMapping(value = "/deleteMypageTag", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteMypageTag(@RequestBody MypageTag mypageTag) throws Exception {
		Integer answers = myPageTagService.deleteMypageTag(mypageTag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모든 포스트 반환", response = List.class)
	@RequestMapping(value = "/findAllPost", method = RequestMethod.GET)
	public ResponseEntity<List<Post>> findAllPost() throws Exception {
		List<Post> answers = postService.findAllPost();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Post>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택 포스트 반환", response = List.class)
	@RequestMapping(value = "/findPost", method = RequestMethod.POST)
	public ResponseEntity<List<Post>> findPost(@RequestBody Post post) throws Exception {
		List<Post> answers = postService.findPost(post);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Post>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 등록", response = List.class)
	@RequestMapping(value = "/addPost", method = RequestMethod.POST)
	public ResponseEntity<Integer> addPost(@RequestBody Post post) throws Exception {
		Integer answers = postService.addPost(post);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 수정", response = List.class)
	@RequestMapping(value = "/updatePost", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updatePost(@RequestBody Post post) throws Exception {
		Integer answers = postService.updatePost(post);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 삭제", response = List.class)
	@RequestMapping(value = "/deletePost", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deletePost(@RequestBody Post post) throws Exception {
		Integer answers = postService.deletePost(post);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모든 포스트 태그 반환", response = List.class)
	@RequestMapping(value = "/findAllPostTag", method = RequestMethod.GET)
	public ResponseEntity<List<PostTag>> findAllPostTag() throws Exception {
		List<PostTag> answers = postTagService.findAllPostTag();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<PostTag>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택 포스트 태그 반환", response = List.class)
	@RequestMapping(value = "/findPostTag", method = RequestMethod.POST)
	public ResponseEntity<List<PostTag>> findPostTag(@RequestBody PostTag postTag) throws Exception {
		
		System.out.println("findposttag");
		List<PostTag> answers = postTagService.findPostTag(postTag);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<PostTag>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 태그 등록", response = List.class)
	@RequestMapping(value = "/addPostTag", method = RequestMethod.POST)
	public ResponseEntity<Integer> addPostTag(@RequestBody PostTag postTag) throws Exception {
		Integer answers = postTagService.addPostTag(postTag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 태그 삭제", response = List.class)
	@RequestMapping(value = "/deletePostTag", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deletePostTag(@RequestBody PostTag postTag) throws Exception {
		Integer answers = postTagService.deletePostTag(postTag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "모든 태그 반환", response = List.class)
	@RequestMapping(value = "/findAllTag", method = RequestMethod.GET)
	public ResponseEntity<List<Tag>> findAllTag() throws Exception {
		List<Tag> answers = tagService.findAllTag();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Tag>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택 태그 반환", response = List.class)
	@RequestMapping(value = "/findTag", method = RequestMethod.POST)
	public ResponseEntity<List<Tag>> findTag(@RequestBody Tag tag) throws Exception {
		List<Tag> answers = tagService.findTag(tag);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Tag>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 태그 등록", response = List.class)
	@RequestMapping(value = "/addTag", method = RequestMethod.POST)
	public ResponseEntity<Integer> addTag(@RequestBody Tag tag) throws Exception {
		Integer answers = tagService.addTag(tag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 태그 수정", response = List.class)
	@RequestMapping(value = "/updateTag", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateTag(@RequestBody Tag tag) throws Exception {
		Integer answers = tagService.updateTag(tag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "포스트 태그 삭제", response = List.class)
	@RequestMapping(value = "/deleteTag", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteTag(@RequestBody Tag tag) throws Exception {
		Integer answers = tagService.deleteTag(tag);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "모든 워드 사전 반환", response = List.class)
	@RequestMapping(value = "/findAllWordDictionary", method = RequestMethod.GET)
	public ResponseEntity<List<WordDictionary>> findAllWordDictionary() throws Exception {
		List<WordDictionary> answers = wordDictionaryService.findAllWordDictionary();
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<WordDictionary>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택 워드 사전 반환", response = List.class)
	@RequestMapping(value = "/findWordDictionary", method = RequestMethod.POST)
	public ResponseEntity<List<WordDictionary>> findWordDictionary(@RequestBody WordDictionary wordDictionary) throws Exception {
		List<WordDictionary> answers = wordDictionaryService.findWordDictionary(wordDictionary);
		if (answers.isEmpty()) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<WordDictionary>>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택 워드 사전 삭제", response = List.class)
	@RequestMapping(value = "/addWordDictionary", method = RequestMethod.POST)
	public ResponseEntity<Integer> addWordDictionary(@RequestBody WordDictionary wordDictionary) throws Exception {
		Integer answers = wordDictionaryService.addWordDictionary(wordDictionary);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택 워드 사전 수정", response = List.class)
	@RequestMapping(value = "/updateWordDictionary", method = RequestMethod.PUT)
	public ResponseEntity<Integer> updateWordDictionary(@RequestBody WordDictionary wordDictionary) throws Exception {
		Integer answers = wordDictionaryService.updateWordDictionary(wordDictionary);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
	
	@ApiOperation(value = "선택 워드 사전 삭제", response = List.class)
	@RequestMapping(value = "/deleteWordDictionary", method = RequestMethod.DELETE)
	public ResponseEntity<Integer> deleteWordDictionary(@RequestBody WordDictionary wordDictionary) throws Exception {
		Integer answers = wordDictionaryService.deleteWordDictionary(wordDictionary);
		if (answers <= 0) {
			return new ResponseEntity(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Integer>(answers, HttpStatus.OK);
	}
}
