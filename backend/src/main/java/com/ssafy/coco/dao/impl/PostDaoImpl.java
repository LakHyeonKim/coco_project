package com.ssafy.coco.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.dao.BabyPostDao;
import com.ssafy.coco.dao.CommentDao;
import com.ssafy.coco.dao.FollowDao;
import com.ssafy.coco.dao.LikeDao;
import com.ssafy.coco.dao.MemberDao;
import com.ssafy.coco.dao.MemberTagDao;
import com.ssafy.coco.dao.MyPageDao;
import com.ssafy.coco.dao.PostDao;
import com.ssafy.coco.relationvo.OrderSearchKeyword;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.Post;

@Repository
public class PostDaoImpl implements PostDao {
	private String ns = "Post_Mapper.";

	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Post> findAllPost() {
		return sqlSession.selectList(ns + "findAllPost");
	}

	@Override
	public List<Post> findPost(Post post) {
		return sqlSession.selectList(ns + "findPost", post);
	}

	@Override
	public int addPost(Post post) {
		return sqlSession.insert(ns + "addPost", post);
	}

	@Override
	public int updatePost(Post post) {
		return sqlSession.update(ns + "updatePost", post);
	}

	@Override
	public int deletePost(Post post) {
		return sqlSession.delete(ns + "deletePost", post);
	}

	@Override
	public List<Post> findPostByNewsfeed(long idMemberFollower) {
		return sqlSession.selectList(ns + "findPostByNewsfeed", idMemberFollower);
	}

	@Override
	public List<Post> findPostByAll(String keyWord) {
		return sqlSession.selectList(ns + "findPostByAll", keyWord);
	}

	@Override
	public List<Post> findPostByTag(String keyWord) {
		return sqlSession.selectList(ns + "findPostByTag", keyWord);
	}

	@Override
	public List<Post> findPostByTitle(String keyWord) {
		return sqlSession.selectList(ns + "findPostByTitle", keyWord);
	}

	@Override
	public List<Post> findPostByCode(String keyWord) {
		return sqlSession.selectList(ns + "findPostByCode", keyWord);
	}

	@Override
	public List<Post> findPostByPostWriter(String keyWord) {
		return sqlSession.selectList(ns + "findPostByPostWriter", keyWord);
	}

	@Override
	public List<Post> findPostByFrequency(long idMember) {
		return sqlSession.selectList(ns + "findPostByFrequency", idMember);
	}

	@Override
	public List<Post> findPostByPostComment(long idPostParents) {
		return sqlSession.selectList(ns + "findPostByPostComment", idPostParents);
	}

	@Override
	public int updatePostViewCount(long idPost) {
		return sqlSession.update(ns + "updatePostViewCount", idPost);
	}

	@Override
	public List<Post> findPostByNewsfeedOrderByLike(long idMemberFollower) {
		return sqlSession.selectList(ns + "findPostByNewsfeedOrderByLike", idMemberFollower);
	}

	@Override
	public int updatePostUnlikeCount(long idPost) {
		return sqlSession.update(ns + "updatePostUnlikeCount",idPost);
	}

	@Override
	public int updatePostlikeCount(long idPost) {
		return sqlSession.update(ns + "updatePostlikeCount",idPost);
	}

	@Override
	public List<Post> findByAllNewsfeedScrollDown(Map<String, Long> hashMap) {
		return sqlSession.selectList(ns + "findByAllNewsfeedScrollDown", hashMap);
	}

	@Override
	public List<Post> findByAllKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		return sqlSession.selectList(ns + "findByAllKeywordMyPosts", orderSearchKeyword);
	}

	@Override
	public List<Post> findByTagKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		return sqlSession.selectList(ns + "findByTagKeywordMyPosts", orderSearchKeyword);
	}

	@Override
	public List<Post> findByPostTitleKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		return sqlSession.selectList(ns + "findByPostTitleKeywordMyPosts", orderSearchKeyword);
	}

	@Override
	public List<Post> findByPostCodeKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		return sqlSession.selectList(ns + "findByPostCodeKeywordMyPosts", orderSearchKeyword);
	}

	@Override
	public List<Post> findPostByFrequencyScrollDown(Map<String, Long> hashMap) {
		return sqlSession.selectList(ns + "findPostByFrequencyScrollDown", hashMap);
	}

	@Override
	public int updatePostWriter(Post post) {
		return sqlSession.update(ns + "updatePostWriter", post);
	}

}
