package com.ssafy.coco.dao.impl;

import java.util.List;

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
import com.ssafy.coco.dao.PostTagDao;
import com.ssafy.coco.dao.TagDao;
import com.ssafy.coco.vo.Alarm;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostTag;
import com.ssafy.coco.vo.Tag;

@Repository
public class PostTagDaoImpl implements PostTagDao{
	private String ns = "PostTag_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<PostTag> findAllPostTag() {
		return sqlSession.selectList(ns+"findAllPostTag");
	}

	@Override
	public List<PostTag> findPostTag(PostTag postTag) {
		return sqlSession.selectList(ns+"findPostTag",postTag);
	}

	@Override
	public int addPostTag(PostTag postTag) {
		return sqlSession.insert(ns+"addPostTag",postTag);
	}

	@Override
	public int deletePostTag(PostTag postTag) {
		return sqlSession.delete(ns+"deletePostTag",postTag);
	}
 


}
