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
import com.ssafy.coco.dao.PostTagDao;
import com.ssafy.coco.service.CommentService;
import com.ssafy.coco.service.FollowService;
import com.ssafy.coco.service.LikeService;
import com.ssafy.coco.service.MemberService;
import com.ssafy.coco.service.MemberTagService;
import com.ssafy.coco.service.MyPageService;
import com.ssafy.coco.service.MyPageTagService;
import com.ssafy.coco.service.PostService;
import com.ssafy.coco.service.PostTagService;
import com.ssafy.coco.vo.BabyPost;
import com.ssafy.coco.vo.Follow;
import com.ssafy.coco.vo.Like;
import com.ssafy.coco.vo.Member;
import com.ssafy.coco.vo.MemberTag;
import com.ssafy.coco.vo.Mypage;
import com.ssafy.coco.vo.MypageTag;
import com.ssafy.coco.vo.Post;
import com.ssafy.coco.vo.PostTag;
@Service
public class PostTagServiceImpl implements PostTagService{

	@Autowired
	private PostTagDao postTagDao;

	@Override
	public List<PostTag> findAllPostTag() {
		return postTagDao.findAllPostTag();
	}

	@Override
	public List<PostTag> findPostTag(PostTag postTag) {
		return postTagDao.findPostTag(postTag);
	}

	@Override
	public int addPostTag(PostTag postTag) {
		return postTagDao.addPostTag(postTag);
	}

	@Override
	public int deletePostTag(PostTag postTag) {
		return postTagDao.deletePostTag(postTag);
	}
	
	

}
