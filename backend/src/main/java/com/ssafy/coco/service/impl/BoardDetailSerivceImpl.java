package com.ssafy.coco.service.impl;

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
import com.ssafy.coco.service.BoardDetailSerivce;
import com.ssafy.coco.vo.Comment;
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
		BoardDetail boardDetail = null;
		for(Post post : posts) {
			List<Like> isPostLike = likeDao.findLike(new Like(0, post.getIdpost(), idMember, 0));
			if(isPostLike.size() != 0) {
				post.setLikeCheck(1);
			}
			List<Post> babyPosts = postDao.findPostByPostComment(post.getIdpost());
			List<Tag> tags = tagDao.findAllTagIncludedPost(post.getIdpost());
			List<Comment> comments = commentDao.findComment(new Comment(0, 0, post.getIdpost(), null, null, null, null, 0));
			List<Member> likes = memberDao.findWhoPressedTheLikeButton(post.getIdpost());
			post.setLikeCount(likes.size());
			long commentCount = comments.size();
			boardDetail = new BoardDetail(post, tags, comments, likes, babyPosts, commentCount);
		}
		return boardDetail;
	}

}
