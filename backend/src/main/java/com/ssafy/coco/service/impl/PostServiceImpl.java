package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.dao.MemberTagDao;
import com.ssafy.coco.dao.MyPageDao;
import com.ssafy.coco.dao.MyPageTagDao;
import com.ssafy.coco.dao.PostDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.service.LikeService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.MemberTagService;
import com.ssafy.coco.service.MyPageService;
import com.ssafy.coco.service.MyPageTagService;
import com.ssafy.coco.service.PostService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.MypageTag;
import com.ssafy.coco.vo.Post;
@Service
public class PostServiceImpl implements PostService{

	@Autowired
	private PostDao postDao;

	@Override
	public List<Post> findAllPost() {
		return postDao.findAllPost();
	}

	@Override
	public List<Post> findPost(Post post) {
		return postDao.findPost( post);
	}

	@Override
	public int addPost(Post post) {
		return postDao.addPost( post);
	}

	@Override
	public int updatePost(Post post) {
		return postDao.updatePost( post);
	}

	@Override
	public int deletePost(Post post) {
		return postDao.deletePost( post);
	}

	@Override
	public List<Post> findPostByNewsfeed(long idMemberFollower) {
		return postDao.findPostByNewsfeed(idMemberFollower);
	}

	@Override
	public List<Post> findPostByAll(String keyWord) {
		return postDao.findPostByAll(keyWord);
	}

	@Override
	public List<Post> findPostByTag(String keyWord) {
		return postDao.findPostByTag(keyWord);
	}

	@Override
	public List<Post> findPostByTitle(String keyWord) {
		return postDao.findPostByTitle(keyWord);
	}

	@Override
	public List<Post> findPostByCode(String keyWord) {
		return postDao.findPostByCode(keyWord);
	}

	@Override
	public List<Post> findPostByPostWriter(String keyWord) {
		return postDao.findPostByPostWriter(keyWord);
	}

	@Override
	public List<Post> findPostByFrequency(long idMember) {
		return postDao.findPostByFrequency(idMember);
	}

	@Override
	public List<Post> findPostByPostComment(long idPostParents) {
		return postDao.findPostByPostComment(idPostParents);
	}

	@Override
	public int updatePostViewCount(long idPost) {
		return postDao.updatePostViewCount(idPost);
	}

	@Override
	public List<Post> findPostByNewsfeedOrderByLike(long idMemberFollower) {
		return postDao.findPostByNewsfeedOrderByLike(idMemberFollower);
	}
}
