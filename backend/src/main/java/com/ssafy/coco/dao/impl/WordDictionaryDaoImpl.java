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
import com.ssafy.coco.dao.WordDictionaryDao;
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
import com.ssafy.coco.vo.WordDictionary;

@Repository
public class WordDictionaryDaoImpl implements WordDictionaryDao{
	private String ns = "WordDictionary_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<WordDictionary> findAllWordDictionary() {
		return sqlSession.selectList(ns+"findAllWordDictionary");
	}

	@Override
	public List<WordDictionary> findWordDictionary(WordDictionary wd) {
		return sqlSession.selectList(ns+"findWordDictionary",wd);
	}

	@Override
	public int addWordDictionary(WordDictionary wd) {
		return sqlSession.insert(ns+"addWordDictionary",wd);
	}

	@Override
	public int updateWordDictionary(WordDictionary wd) {
		return sqlSession.update(ns+"updateWordDictionary",wd);
	}

	@Override
	public int deleteWordDictionary(WordDictionary wd) {
		return sqlSession.delete(ns+"deleteWordDictionary",wd);
	}




}
