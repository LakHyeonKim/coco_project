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
public class TagDaoImpl implements TagDao{
	private String ns = "Tag_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Tag> findAllTag() {
		return sqlSession.selectList(ns+"findAllTag");
	}

	@Override
	public List<Tag> findTag(Tag tag) {
		return sqlSession.selectList(ns+"findTag",tag);
	}

	@Override
	public int addTag(Tag tag) {
		return sqlSession.insert(ns+"addTag",tag);
	}

	@Override
	public int updateTag(Tag tag) {
		return sqlSession.update(ns+"updateTag",tag);
	}

	@Override
	public int deleteTag(Tag tag) {
		return sqlSession.delete(ns+"deleteTag",tag);
	}

	@Override
	public int updateTagIncludedCount(String tag) {
		return sqlSession.update(ns+"updateTagIncludedCount",tag);
	}

	@Override
	public List<Tag> findAllTagIncludedPost(long idPost) {
		return sqlSession.selectList(ns+"findAllTagIncludedPost", idPost);
	}

	@Override
	public List<Tag> findAllTagIncludedMypage(long idMypage) {
		
		return sqlSession.selectList(ns+"findAllTagIncludedMypage",idMypage);
	}



}
