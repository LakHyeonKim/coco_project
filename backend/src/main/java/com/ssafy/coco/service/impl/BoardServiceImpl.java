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
import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.service.BoardService;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.Tag;

@Service
public class BoardServiceImpl implements BoardService{

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
	public List<Board> findByAllNewsfeed(long idMember) {
		List<Post> newsPosts = postDao.findPostByNewsfeed(idMember);
		return makeBoardList(newsPosts);
	}

	@Override
	public List<Board> findByAllDefaultSearch(long idMember) {
		List<Post> defaultPosts = postDao.findPostByFrequency(idMember);
		return makeBoardList(defaultPosts);
	}
	
	@Override
	public List<Board> findByMyPosts(long idMember) {
		List<Post> myPosts = postDao.findPost(new Post(0, idMember, null, null, null, null, null, 0, 0, null, 0));
		return makeBoardList(myPosts);
	}

	@Override
	public List<Board> findByAllKeyword(String keyWord) {
		List<Post> searchedPosts = postDao.findPostByAll(keyWord);
		return makeBoardList(searchedPosts);
	}
	
	@Override
	public List<Board> findByTagKeyword(String keyWord) {
		List<Post> searchedPosts = postDao.findPostByTag(keyWord);
		return makeBoardList(searchedPosts);
	}

	@Override
	public List<Board> findByPostTitleKeyword(String keyWord) {
		List<Post> searchedPosts = postDao.findPostByTitle(keyWord);
		return makeBoardList(searchedPosts);
	}

	@Override
	public List<Board> findByPostCodeKeyword(String keyWord) {
		List<Post> searchedPosts = postDao.findPostByCode(keyWord);
		return makeBoardList(searchedPosts);
	}

	@Override
	public List<Board> findByPostWriterKeyword(String keyWord) {
		List<Post> searchedPosts = postDao.findPostByPostWriter(keyWord);
		return makeBoardList(searchedPosts);
	}
	
	private List<Board> makeBoardList(List<Post> posts){
		List<Board> boards = new ArrayList<>();
		for(Post post : posts) {
			List<Post> babyPosts = postDao.findPostByPostComment(post.getIdpost());
			List<Tag> tags = tagDao.findAllTagIncludedPost(post.getIdpost());
			List<Comment> comments = commentDao.findComment(new Comment(0, 0, post.getIdpost(), null, null, null, null, 0));
			List<Member> likes = memberDao.findWhoPressedTheLikeButton(post.getIdpost());
			long commentCount = comments.size();
			long likeCount = likes.size();
			boards.add(new Board(post, tags, comments, likes, babyPosts, commentCount, likeCount));
		}
		return boards;
	}

}
