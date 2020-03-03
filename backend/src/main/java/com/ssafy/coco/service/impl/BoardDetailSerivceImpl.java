package com.ssafy.coco.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.ssafy.coco.relationvo.CommentInfo;
import com.ssafy.coco.relationvo.LikeInfo;
import com.ssafy.coco.service.BoardDetailSerivce;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

@Service
public class BoardDetailSerivceImpl implements BoardDetailSerivce{

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
	
	
	@Override
	public BoardDetail findByBoardDetailPostId(long idPost, long idMember) {
		List<Post> posts = postDao.findPost(new Post(idPost, 0, null, null, null, null, null, 0, 0, null, 0, 0, 0));
		return makeBoardList(idMember,posts);
	}

	private BoardDetail makeBoardList(long idMember, List<Post> posts){
		Post post = posts.get(0);
//		List<LikeInfo> likeInfos = new ArrayList<LikeInfo>();
		List<CommentInfo> commentInfos = new ArrayList<CommentInfo>();
		
		if(likeDao.findLike(new Like(0, post.getIdpost(), idMember, 0)).size() > 0) post.setLikeCheck(1);
		
		List<Tag> tags = tagDao.findAllTagIncludedPost(post.getIdpost());
		
		List<Comment> comments = commentDao.findComment(new Comment(0, 0, post.getIdpost(), null, null, null, null, 0));
		for(Comment comment : comments) {
			int isFollow = 1;
			String postWriterProfileImage = "";
			Member tempMember = new Member();
			tempMember.setIdmember(comment.getMemberId());
			List<Member> findMember = memberDao.findMember(tempMember);
			postWriterProfileImage = findMember.get(0).getImageUrl();
//			if(followDao.findFollow(new Follow(0, idMember, comment.getMemberId(), 0)).size() == 0) {
//				isFollow = 0;
//			}
			commentInfos.add(new CommentInfo(comment, isFollow, postWriterProfileImage));
		}
		
		List<Member> likedPeople = memberDao.findWhoPressedTheLikeButton(post.getIdpost());
//		for(Member member : likedPeople) {
//			int isFollow = 1;
//			if(followDao.findFollow(new Follow(0, idMember, member.getIdmember(), 0)).size() == 0) {
//				isFollow = 0;
//			}
//			likeInfos.add(new LikeInfo(member, isFollow));
//		}
		post.setLikeCount(likeDao.findLike(new Like(0, post.getIdpost(), 0, 0)).size());
		List<Post> babyPosts = postDao.findPostByPostComment(post.getIdpost());
		int commentCount = comments.size();
		Member tempMember = new Member();
		tempMember.setIdmember(post.getMemberId());
		List<Member> findMember = memberDao.findMember(tempMember);
		String postWriterProfileImage = findMember.get(0).getImageUrl();
		int isFollowPost = 1;
		if(followDao.findFollow(new Follow(0, idMember, post.getMemberId(), 0)).size() == 0) {
			isFollowPost = 0;
		}
		
		return new BoardDetail(post, tags, commentInfos, likedPeople, babyPosts, commentCount, postWriterProfileImage, isFollowPost);
	}

	@Override
	public int deleteFile(long idPost) {
		Post post = new Post();
		post.setIdpost(idPost);
		post.setFilePath("");
		return postDao.updatePost(post);
	}

}
