package com.ssafy.coco.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
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
import com.ssafy.coco.dao.MyPageTagDao;
import com.ssafy.coco.dao.PostDao;
import com.ssafy.coco.dao.PostTagDao;
import com.ssafy.coco.dao.TagDao;
import com.ssafy.coco.relationvo.BabyBoardWrite;
import com.ssafy.coco.relationvo.BoardDetail;
import com.ssafy.coco.relationvo.BoardUpdate;
import com.ssafy.coco.relationvo.BoardWrite;
import com.ssafy.coco.relationvo.MemberInfoModify;
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
import com.ssafy.coco.vo.MypageTag;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostTag;
import com.ssafy.coco.vo.Tag;

@Service
public class TransactionServiceImpl implements TransactionService {

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
	@Autowired
	private MyPageTagDao myPageTagDao;
	// 글쓴사람이랑 보낸사람이랑 포스트아이디
	@Transactional
	public void deleteComment(long postId,long receiver,long caller,long commentId ) {
		Comment comment = new Comment();
		comment.setIdcomment(commentId);
		commentDao.deleteComment(comment);
		Alarm alarm = new Alarm();
		alarm.setMemberReceiver(receiver);
		alarm.setMemberCaller(caller);
		alarm.setPostId(postId);
		alarmDao.deleteAlarm(alarm);
	}

	private static final String IP = "52.79.118.55:8888";
	
	private static final String USER_PROFILE_PATH = "/home/ubuntu/userprofile/";
	private static final String USER_FILE_PATH = "/home/ubuntu/userfile/";
	private static final String USER_BANNER_PATH = "/home/ubuntu/userbanner/";

	/**
	 * 
	 * @param member 회원정보
	 * 
	 *               사용자가 회원 가입 했을 때 마이페이지 까지 자동으로 만들어지는 트랜잭션
	 * @throws Exception
	 */
	@Transactional
	public long signUp(SignUpMember signUpMember) throws Exception {
		Member member = new Member(signUpMember.getIdmember(), signUpMember.getRankId(), signUpMember.getIsManager(),
				signUpMember.getIsDelete(), signUpMember.getNickname(), signUpMember.getId(),
				Member.encryptSHA256Iter(signUpMember.getPassword(), signUpMember.getPassword().length()), signUpMember.getEmail(), signUpMember.getGitUrl(),
				signUpMember.getKakaoUrl(), signUpMember.getInstagramUrl(), signUpMember.getDateCreated(),
				signUpMember.getUpdateCreated(), signUpMember.getGrade());
		member.setRankId(1L);

		System.out.println(signUpMember.getFile() + "겟파일 ");
		if (!signUpMember.getFile().getOriginalFilename().equals("")) {
			MultipartFile file = signUpMember.getFile();
			//String path = System.getProperty("user.dir") + "/src/main/webapp/userprofile/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", member.getId(), originFileName);
			String imageFilePath = "http://" + IP + "/userprofile/"+ saveFileName + "";
			file.transferTo(new File(USER_PROFILE_PATH, saveFileName));
			member.setImageUrl(imageFilePath);
		}else {
			member.setImageUrl(signUpMember.getImageUrl());
		}
//		member.setIsManager(1);
		memberDao.addMember(member);
		Mypage mypage = new Mypage();
		mypage.setMemberId(member.getIdmember());
		myPageDao.addMypage(mypage);

		String key = jwtService.makeJwt("" + member.getIdmember(), "!@323213214214324", 1);
		mailService.sendMail(member.getId(), "[SEE-SAW] 인증 메일입니다.",
				"<a href=http://"+ IP +"/email/"+ key +">인증하기</a>");
		return member.getIdmember();
	}

	/**
	 * 
	 * @param commentData 댓글 정보
	 * @param receiver    알람 받는 사람
	 * 
	 *                    포스트에 댓글을 작성 하였을 때 알람 까지 트랜잭션
	 */

	@Transactional
	public long makeComment(Comment commentData, long receiver) {
		System.out.println(commentData);
		System.out.println(receiver);
		commentDao.addComment(commentData);
		long idComment = commentData.getIdcomment();
		Alarm alarm = new Alarm(0, commentData.getMemberId(), receiver, commentData.getPostId(), 0, 0, 0, 0);
		alarmDao.addAlarm(alarm);
		return idComment;
	}

	/**
	 * 
	 * @param baby         포스트 내용
	 * @param parentPostId 포스트 부모의 아이디
	 * @param receiver     알람 받는 사람
	 * 
	 *                     포스트에 포스트 형식 댓글을 작성 하였을 때 알람 까지 트랜잭션
	 * @throws IOException 
	 * @throws IllegalStateException 
	 */

	@Transactional
	public void makeBabyPost(BabyBoardWrite babyBoardWrite) throws IllegalStateException, IOException {
		Post post = new Post();
		post.setCode(babyBoardWrite.getCode());
		post.setMemberId(babyBoardWrite.getMemberId());
		post.setPostTitle(babyBoardWrite.getPostTitle());
		post.setPostWriter(babyBoardWrite.getPostWriter());

		String[] splitTag = babyBoardWrite.getTags().split(",");

		if (!babyBoardWrite.getAttachments().getOriginalFilename().equals("")) {
			MultipartFile file = babyBoardWrite.getAttachments();
			//String path = System.getProperty("user.dir") + "/src/main/webapp/userfile/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", post.getIdpost()+"", originFileName);
			String filePath = "http://" + IP + "/userfile/" + saveFileName + "";
			file.transferTo(new File(USER_FILE_PATH, saveFileName));
			post.setFilePath(filePath);
		}else {
			post.setFilePath("");
		}

		long idPost = postDao.addPost(post);
		idPost = post.getIdpost();

		for (String splitedTag : splitTag) {
			if (splitedTag.equals(""))
				break;
			int size = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).size();
			if (size == 0) {
				tagDao.addTag(new Tag(0, splitedTag, 0, 1, null, null, null));
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			} else {
				tagDao.updateTagIncludedCount(splitedTag);
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			}
		}

		babyPostDao.addBabyPost(new BabyPost(0, babyBoardWrite.getParentIdPost(), idPost));
		Alarm alarm = new Alarm(0, babyBoardWrite.getMemberId(), babyBoardWrite.getParentIdMember(), babyBoardWrite.getParentIdPost(), 0, 0, 0, 0);
		alarmDao.addAlarm(alarm);
	}

	/**
	 * 
	 * @param post
	 * @param idMember
	 * 
	 *                 포스트 클릭시 일어나는 트랜잭션 1. 포스트 view 카운트 증가 2. 해당 포스트의 모든 tag들을 가지고
	 *                 온다. 3. 모든 tag 검색해서 있으면 frequency++ 없으면 member_tag 테이블에 삽입
	 */

	@Transactional
	public void postClick(Post post, long idMember) {
		postDao.updatePostViewCount(post.getIdpost());
		List<PostTag> postTagList = postTagDao.findPostTag(new PostTag(0, post.getIdpost(), 0));
		for (PostTag postTag : postTagList) {
			if (memberTagDao.findMemberTag(new MemberTag(0, idMember, postTag.getTagId(), 0, 0)).size() == 0) {
				memberTagDao.addMemberTag(new MemberTag(0, idMember, postTag.getTagId(), 0, 1));

			} else {
				Map<String, Long> hashMap = new HashMap<>();
				hashMap.put("idMember", idMember);
				hashMap.put("idTag", postTag.getTagId());
				memberTagDao.updateMemberTagFrequencyCount(hashMap);
			}
		}
	}

	/**
	 * 
	 * @param idMemberFollower  팔로우 한 주체 아이디
	 * @param idMemberFollowing 팔로우 한 대상 아이디
	 * 
	 *                          팔로우 하고 알림까지 트랜젝션
	 */

	@Transactional
	public void makeFollow(long idMemberFollower, long idMemberFollowing) {
		followDao.addFollow(new Follow(0, idMemberFollower, idMemberFollowing, 0));
		long followId = followDao.findFollow(new Follow(0, idMemberFollower, idMemberFollowing, 0)).get(0)
				.getIdfollow();
		alarmDao.addAlarm(new Alarm(0, idMemberFollower, idMemberFollowing, 0, 0, followId, 0, 0));
	}

	/**
	 * 
	 * @param idMemberFollower  팔로우 한 주체 아이디
	 * @param idMemberFollowing 팔로우 한 대상 아이디
	 * 
	 *                          팔로우 취소
	 */

	@Transactional
	public void makeUnFollow(long idMemberFollower, long idMemberFollowing) {
		followDao.deleteFollow(new Follow(0, idMemberFollower, idMemberFollowing, 0));
	}

	/**
	 * 
	 * @param idPost   좋아요 누른 포스트 아이디
	 * @param idMember 누른 사람의 사용자 아이디
	 * 
	 *                 좋아요 테이블에 좋아요 정보 저장 후 알람 까지 트랜젝션
	 * 
	 */

	@Transactional
	public void pushLike(long idPost, long idMember) {
		//if(likeDao.findLike(new Like(0, idPost, idMember,0)).size() == 0) {
			likeDao.addLike(new Like(0, idPost, idMember, 0));
			//postDao.updatePostlikeCount(idPost);
			long memberId = postDao.findPost(new Post(idPost, 0, null, null, null, null, null, 0, 0, null, 0)).get(0)
					.getMemberId();
			long likeId = likeDao.findLike(new Like(0, idPost, idMember, 0)).get(0).getIdlike();
			if(memberId != idMember)
			alarmDao.addAlarm(new Alarm(0, idMember, memberId, idPost, likeId, 0, 0, 0));
		//}
	}

	/**
	 * @param idPost   좋아요 누른 포스트 아이디
	 * @param idMember 누른 사람의 사용자 아이디
	 * 
	 *                 좋아요 취소 알람 취소 까지 트랜잭션
	 */

	@Transactional
	public void unLike(long idPost, long idMember) {
		//if(likeDao.findLike(new Like(0, idPost, idMember,0)).size() != 0) {
			likeDao.deleteLike(new Like(0, idPost, idMember, 0));
			//postDao.updatePostUnlikeCount(idPost);			
		//}
		//		long memberId = postDao.findPost(new Post(idPost, 0, null, null, null, null, null, 0, 0, null, 0)).get(0).getMemberId();
		//		alarmDao.deleteAlarm(new Alarm(0, idMember, memberId, idPost, likeId, 0, 0, 0));
	}

	/**
	 * 
	 * @param idPost  작성한 post 아이디
	 * @param tagName 태그
	 * 
	 *                포스트 작성 시 트랜잭션
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

		if (!board.getAttachments().getOriginalFilename().equals("")) {
			MultipartFile file = board.getAttachments();
			//String path = System.getProperty("user.dir") + "/src/main/webapp/userfile/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", post.getIdpost()+"", originFileName);
			String filePath = "http://" + IP + "/userfile/" + saveFileName + "";
			file.transferTo(new File(USER_FILE_PATH, saveFileName));
			post.setFilePath(filePath);
		}else {
			post.setFilePath("");
		}
		postDao.addPost(post);
		for (String splitedTag : splitTag) {
			if (splitedTag.equals(""))
				break;
			int size = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).size();
			if (size == 0) {
				tagDao.addTag(new Tag(0, splitedTag, 0, 1, null, null, null));
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			} else {
				tagDao.updateTagIncludedCount(splitedTag);
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			}
		}
	}
	
	/**
	 * 
	 * @param board
	 * @throws IllegalStateException
	 * @throws IOException
	 */

	@Transactional
	public void updatePost(BoardUpdate board) throws IllegalStateException, IOException {
		
		System.out.println(board);
		Post findPost = new Post();
		findPost.setIdpost(board.getIdpost());
		Post post = postDao.findPost(findPost).get(0);
		post.setCode(board.getCode());
		post.setMemberId(board.getMemberId());
		post.setPostTitle(board.getPostTitle());
		post.setPostWriter(board.getPostWriter());

		String[] splitTag = board.getTags().split(",");

		if (!board.getAttachments().getOriginalFilename().equals("")) {
			MultipartFile file = board.getAttachments();
			//String path = System.getProperty("user.dir") + "/src/main/webapp/userfile/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", post.getIdpost()+"", originFileName);
			String filePath = "http://" + IP + "/userfile/" + saveFileName + "";
			file.transferTo(new File(USER_FILE_PATH, saveFileName));
			post.setFilePath(filePath);
		}else {
			post.setFilePath("");
		}
		postDao.updatePost(post);
		postTagDao.deletePostTag(new PostTag(0, post.getIdpost(), 0));
		
		for (String splitedTag : splitTag) {
			if (splitedTag.equals(""))
				break;
			int size = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).size();
			if (size == 0) {
				tagDao.addTag(new Tag(0, splitedTag, 0, 1, null, null, null));
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			} else {
				tagDao.updateTagIncludedCount(splitedTag);
				long tagId = tagDao.findTag(new Tag(0, splitedTag, 0, 0, null, null, null)).get(0).getIdtag();
				postTagDao.addPostTag(new PostTag(0, post.getIdpost(), tagId));
			}
		}
	}
	
	/**
	 * 
	 */

	@Transactional
	@Override
	public void updateMemeberInfo(MemberInfoModify memberInfoModify) throws Exception {
		Member tempMember = new Member();
		tempMember.setIdmember(memberInfoModify.getIdmember());
		List<Member> member = memberDao.findMember(tempMember);

		Mypage tempMypage = new Mypage();
		tempMypage.setMemberId(memberInfoModify.getIdmember());
		List<Mypage> mypage = myPageDao.findMypage(tempMypage);

		Member modifyMember = member.get(0);
		Mypage modifyMypage = mypage.get(0);

		if(!memberInfoModify.getBannerImage().getOriginalFilename().equals("")) {
			MultipartFile file = memberInfoModify.getBannerImage();
			//String path = System.getProperty("user.dir") + "/src/main/webapp/userbanner/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", memberInfoModify.getIdmember()+"", originFileName);
			String filePath = "http://" + IP + "/userbanner/" + saveFileName + "";
			file.transferTo(new File(USER_BANNER_PATH, saveFileName));
			modifyMypage.setBannerImagePath(filePath);
		}
		if(!memberInfoModify.getProfileImage().getOriginalFilename().equals("")) {
			MultipartFile file = memberInfoModify.getProfileImage();
			//String path = System.getProperty("user.dir") + "/src/main/webapp/userprofile/";
			String originFileName = file.getOriginalFilename();
			String saveFileName = String.format("%s_%s", memberInfoModify.getIdmember()+"", originFileName);
			String filePath = "http://" + IP + "/userprofile/" + saveFileName + "";
			file.transferTo(new File(USER_PROFILE_PATH, saveFileName));
			modifyMember.setImageUrl(filePath);
		}
		if(!memberInfoModify.getTags().equals("")) {
			MypageTag myPageTag = new MypageTag();
			myPageTag.setMypageId(modifyMypage.getIdmypage());
			myPageTagDao.deleteMypageTag(myPageTag);

			String[] splitedTags = memberInfoModify.getTags().split(",");
			for(String tag : splitedTags) {
				int size = tagDao.findTag(new Tag(0, tag, 0, 0, null, null, null)).size();
				if (size == 0) {
					tagDao.addTag(new Tag(0, tag, 0, 1, null, null, null));
					long tagId = tagDao.findTag(new Tag(0, tag, 0, 0, null, null, null)).get(0).getIdtag();
					myPageTagDao.addMypageTag(new MypageTag(0, modifyMypage.getIdmypage(), tagId));
				} else {
					tagDao.updateTagIncludedCount(tag);
					long tagId = tagDao.findTag(new Tag(0, tag, 0, 0, null, null, null)).get(0).getIdtag();
					myPageTagDao.addMypageTag(new MypageTag(0, modifyMypage.getIdmypage(), tagId));
				}
			}
		}
		if(!memberInfoModify.getNickName().equals("")) {
			modifyMember.setNickname(memberInfoModify.getNickName());
			Post updatePost = new Post();
			updatePost.setPostWriter(memberInfoModify.getNickName());
			updatePost.setMemberId(memberInfoModify.getIdmember());
			postDao.updatePostWriter(updatePost);
		}
		if(!memberInfoModify.getGitUrl().equals(""))
			modifyMember.setGitUrl(memberInfoModify.getGitUrl());
		if(!memberInfoModify.getInstagramUrl().equals(""))
			modifyMember.setInstagramUrl(memberInfoModify.getInstagramUrl());
		if(!memberInfoModify.getKakaoUrl().equals(""))
			modifyMember.setKakaoUrl(memberInfoModify.getKakaoUrl());
		if(!memberInfoModify.getPassword().equals(""))
			modifyMember.setPassword(Member.encryptSHA256Iter(memberInfoModify.getPassword(), memberInfoModify.getPassword().length()));
		if(!memberInfoModify.getBannerText().equals(""))
			modifyMypage.setBannerText(memberInfoModify.getBannerText());

		memberDao.updateMember(modifyMember);
		myPageDao.updateMypage(modifyMypage);
	}

	@Transactional
	@Override
	public void deleteMemberProfile(long idMember) {
		Member member = new Member();
		member.setIdmember(idMember);
		Member findedMember = memberDao.findMember(member).get(0);
		findedMember.setImageUrl("");
		memberDao.updateMember(findedMember);
	}

	@Transactional
	@Override
	public void deleteMemberBannerImage(long idMember) {
		Mypage mypage = new Mypage();
		mypage.setMemberId(idMember);
		Mypage findedMypage = myPageDao.findMypage(mypage).get(0);
		findedMypage.setBannerImagePath("");
		myPageDao.updateMypage(findedMypage);
	}
}
