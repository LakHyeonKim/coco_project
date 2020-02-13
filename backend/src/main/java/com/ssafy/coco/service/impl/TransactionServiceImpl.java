package com.ssafy.coco.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.dao.MemberTagDao;
import com.ssafy.coco.dao.MyPageDao;
import com.ssafy.coco.dao.PostDao;
import com.ssafy.coco.dao.PostTagDao;
import com.ssafy.coco.dao.TagDao;
import com.ssafy.coco.relationvo.BoardDetail;
import com.ssafy.coco.relationvo.BoardWrite;
import com.ssafy.coco.relationvo.SignUpMember;
import com.ssafy.coco.service.JwtService;
import com.ssafy.coco.service.MailService;
import com.ssafy.coco.service.TransactionService;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostTag;
import com.ssafy.coco.vo.Tag;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired 
	private MemberDao memberDao;
	@Autowired
	private MyPageDao myPageDao;
	@Autowired
	private CommentDao commentDao;
	@Autowired
	private AlarmDao alarmDao;
	@Autowired
	private PostDao postDao;
	@Autowired
	private BabyPostDao babyPostDao;
	@Autowired
	private PostTagDao postTagDao;
	@Autowired
	private MemberTagDao memberTagDao;
	@Autowired
	private FollowDao followDao;
	@Autowired
	private LikeDao likeDao;
	@Autowired
	private TagDao tagDao;
	@Autowired
	private JwtService jwtService;
	@Autowired
	private MailService mailService;
	
	/**
	 * 
	 * @param member 회원정보
	 * 
	 * 사용자가 회원 가입 했을 때 마이페이지 까지 자동으로 만들어지는 트랜잭션
	 * @throws Exception 
	 */
	@Transactional
	public long signUp(SignUpMember signUpMember) throws Exception{
		Member member = new Member(signUpMember.getIdmember(), 
				signUpMember.getRankId(), 
				signUpMember.getIsManager(), 
				signUpMember.getIsDelete(), 
				signUpMember.getNickname(), 
				signUpMember.getId(), 
				signUpMember.getPassword(), 
				signUpMember.getEmail(), 
				signUpMember.getGitUrl(), 
				signUpMember.getKakaoUrl(), 
				signUpMember.getInstagramUrl(), 
				signUpMember.getDateCreated(), 
				signUpMember.getUpdateCreated(), 
				signUpMember.getGrade());
		member.setRankId(1L);
		
		
		if(signUpMember.getFile() != null) {
			MultipartFile file = signUpMember.getFile();
			String path = System.getProperty("user.dir") + "/src/main/webapp/userprofile/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", member.getId(), originFileName);
			String imageFilePath = "http://localhost:8888/userprofile/" + saveFileName + "";
			file.transferTo(new File(path, saveFileName));
			member.setImageUrl(imageFilePath);
		}
		memberDao.addMember(member);
		Mypage mypage= new Mypage();
		mypage.setMemberId(member.getIdmember());
		myPageDao.addMypage(mypage);
		
		String key = jwtService.makeJwt("" + member.getIdmember(), "!@323213214214324", 1);
		mailService.sendMail(member.getId(), "[SEE-SAW] 인증 메일입니다.", "<a href=http://192.168.100.95:8888/jwt/certificationByEmail/"+key+">인증하기</a>");
		return member.getIdmember();
	}
	
	/**
	 * 
	 * @param commentData 댓글 정보
	 * @param receiver 알람 받는 사람 
	 * 
	 * 포스트에 댓글을 작성 하였을 때 알람 까지 트랜잭션
	 */
	
	@Transactional
	public void makeComment(Comment commentData, long receiver){
		commentDao.addComment(commentData);
		Alarm alarm = new Alarm(0,commentData.getMemberId(),receiver,commentData.getPostId(),1,1,0,0);
		alarmDao.addAlarm(alarm);
	}
	
	/**
	 * 
	 * @param baby 포스트 내용
	 * @param parentPostId 포스트 부모의 아이디
	 * @param receiver 알람 받는 사람
	 * 
	 * 포스트에 포스트 형식 댓글을 작성 하였을 때 알람 까지 트랜잭션
	 */
	
	@Transactional
	public void makeBabyPost(Post baby,Post parent){
		long parentPostId;
		long receiver;
		postDao.addPost(baby);
		BabyPost babyPost = new BabyPost(0,parent.getIdpost(),baby.getIdpost());
		babyPostDao.addBabyPost(babyPost);
		Alarm alarm = new Alarm(0,baby.getMemberId(),parent.getMemberId(),parent.getIdpost(),0,0,0,0);
		alarmDao.addAlarm(alarm);
	}
	
	/**
	 * 
	 * @param post
	 * @param idMember
	 * 
	 * 포스트 클릭시 일어나는 트랜잭션
	 * 1. 포스트 view 카운트 증가
	 * 2. 해당 포스트의 모든 tag들을 가지고 온다.
	 * 3. 모든 tag 검색해서 있으면 frequency++ 없으면 member_tag 테이블에 삽입
	 */
	
	@Transactional
	public void postClick(Post post, long idMember){
		postDao.updatePostViewCount(post.getIdpost());
		List<PostTag> postTagList = postTagDao.findPostTag(new PostTag(0, post.getIdpost(), 0));
		for(PostTag postTag : postTagList) {
			if(memberTagDao.findMemberTag(new MemberTag(0, idMember, postTag.getTagId(), 0, 0)).size() == 0){
				memberTagDao.addMemberTag(new MemberTag(0, idMember, postTag.getTagId(), 0, 1));
				
			}else {
				Map<String,Long> hashMap = new HashMap<>();
				hashMap.put("idMember", idMember);
				hashMap.put("idTag", postTag.getTagId());
				memberTagDao.updateMemberTagFrequencyCount(hashMap);
			}
		}
	}
	
	/**
	 * 
	 * @param idMemberFollower 팔로우 한 주체 아이디
	 * @param idMemberFollowing 팔로우 한 대상 아이디
	 * 
	 * 팔로우 하고 알림까지 트랜젝션
	 */
	
	@Transactional
	public void makeFollow(long idMemberFollower, long idMemberFollowing) {
		followDao.addFollow(new Follow(0, idMemberFollower, idMemberFollowing, 0));
		long followId = followDao.findFollow(new Follow(0, idMemberFollower, idMemberFollowing, 0)).get(0).getIdfollow();
		alarmDao.addAlarm(new Alarm(0, idMemberFollower, idMemberFollowing, 0, 0, followId, 0, 0));
	}
	
	/**
	 * 
	 * @param idMemberFollower 팔로우 한 주체 아이디
	 * @param idMemberFollowing 팔로우 한 대상 아이디
	 * 
	 * 팔로우 취소
	 */
	
	@Transactional
	public void makeUnFollow(long idMemberFollower, long idMemberFollowing) {
		followDao.deleteFollow(new Follow(0, idMemberFollower, idMemberFollowing, 0));
	}
	
	/**
	 * 
	 * @param idPost 좋아요 누른 포스트 아이디
	 * @param idMember 누른 사람의 사용자 아이디
	 * 
	 * 좋아요 테이블에 좋아요 정보 저장 후 알람 까지 트랜젝션
	 * 
	 */
	
	@Transactional
	public void pushLike(long idPost, long idMember) {
		likeDao.addLike(new Like(0, idPost, idMember, 0));
		postDao.updatePostlikeCount(idPost);
		long memberId = postDao.findPost(new Post(idPost, 0, null, null, null, null, null, 0, 0, null, 0)).get(0).getMemberId();
		long likeId = likeDao.findLike(new Like(0, idPost, idMember, 0)).get(0).getIdlike();
		alarmDao.addAlarm(new Alarm(0, idMember, memberId, idPost, likeId, 0, 0, 0));
	}
	
	/**
	 * @param idPost 좋아요 누른 포스트 아이디
	 * @param idMember 누른 사람의 사용자 아이디
	 * 
	 * 좋아요 취소 알람 취소 까지 트랜잭션
	 */
	
	@Transactional
	public void unLike(long idPost, long idMember) {
		//long likeId = likeDao.findLike(new Like(0, idPost, idMember, 0)).get(0).getIdlike();
		likeDao.deleteLike(new Like(0, idPost, idMember, 0));
		postDao.updatePostUnlikeCount(idPost);
//		long memberId = postDao.findPost(new Post(idPost, 0, null, null, null, null, null, 0, 0, null, 0)).get(0).getMemberId();
//		alarmDao.deleteAlarm(new Alarm(0, idMember, memberId, idPost, likeId, 0, 0, 0));
	}
	
	/**
	 * 
	 * @param idPost 작성한 post 아이디
	 * @param tagName 태그 
	 * 
	 * 포스트 작성 시 트랜잭션
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */
	
	@Transactional
	public void makePost(BoardWrite board) throws IllegalStateException, IOException {
		Post post = new Post();
		post.setCode(board.getCode());
		post.setMemberId(board.getMemberId());
		post.setPostTitle(board.getPostTitle());
		post.setPostWriter(board.getPostWriter());
		
		String[] splitTag = board.getTags().split(",");
		
		if(board.getAttachments() != null) {
			MultipartFile file = board.getAttachments();
			String path = System.getProperty("user.dir") + "/src/main/webapp/userfile/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", post.getIdpost()+"", originFileName);
			String filePath = "http://localhost:8888/userfile/" + saveFileName + "";
			file.transferTo(new File(path, saveFileName));
			post.setFilePath(filePath);
		}
		postDao.addPost(post);
		for(String splitedTag : splitTag) {
			if(splitedTag.equals("")) break;
			int size = tagDao.findTag(new Tag(0, splitedTag , 0, 0, null, null, null)).size();
			if(size == 0) {
				tagDao.addTag(new Tag(0, splitedTag, 0, 1, null, null, null));
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			}else {
				tagDao.updateTagIncludedCount(splitedTag);
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			}
		}
	}


}
