package com.ssafy.coco.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
import com.ssafy.coco.relationvo.OrderSearchKeyword;
import com.ssafy.coco.service.BoardService;
import com.ssafy.coco.vo.Comment;
import com.ssafy.coco.vo.Like;
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
	
	@Cacheable(value="Newsfeed", key="#idMember", cacheManager="cacheManager", sync=true)
	@Override
	public List<Board> findByAllNewsfeed(long idMember) {
		List<Post> newsPosts = postDao.findPostByNewsfeed(idMember);
		return makeBoardList(idMember, newsPosts);
	}
	
	@Override
	public List<Board> findByAllNewsfeedScrollDown(long idMember, long lastIdPost) {
		Map<String,Long> hashMap = new HashMap<>();
		hashMap.put("idMember", idMember);
		hashMap.put("idPost", lastIdPost);
		List<Post> newsPosts = postDao.findByAllNewsfeedScrollDown(hashMap);
		return makeBoardList(idMember, newsPosts);
	}

	@Override
	public List<Board> findByAllDefaultSearch(long idMember) {
		List<Post> defaultPosts = postDao.findPostByFrequency(idMember);
		if(defaultPosts.size() == 0) {
			defaultPosts.addAll(postDao.findAllPost());
		}
		return makeBoardList(idMember, defaultPosts);
	}

	@Override
	public List<Board> findByAllDefaultSearchScrollDown(long idMember, long lastIdPost) {
		Map<String,Long> hashMap = new HashMap<>();
		hashMap.put("idMember", idMember);
		hashMap.put("idPost", lastIdPost);
		List<Post> defaultPosts = postDao.findPostByFrequencyScrollDown(hashMap);
		return makeBoardList(idMember, defaultPosts);
	}
	
	@Override
	public List<Board> findByMyPosts(long myIdMember, long youIdMember, int order) {
		List<Post> myPosts = postDao.findPost(new Post(0, youIdMember, null, null, null, null, null, 0, 0, null, 0, 0, order));
		return makeBoardList(myIdMember, myPosts, order);
	}

	@Override
	public List<Board> findByAllKeyword(long idMember, String keyWord) {
		List<Post> searchedPosts = postDao.findPostByAll(keyWord);
		return makeBoardList(idMember, searchedPosts);
	}

	@Override
	public List<Board> findByTagKeyword(long idMember, String keyWord) {
		List<Post> searchedPosts = postDao.findPostByTag(keyWord);
		return makeBoardList(idMember, searchedPosts);
	}

	@Override
	public List<Board> findByPostTitleKeyword(long idMember, String keyWord) {
		List<Post> searchedPosts = postDao.findPostByTitle(keyWord);
		return makeBoardList(idMember, searchedPosts);
	}

	@Override
	public List<Board> findByPostCodeKeyword(long idMember, String keyWord) {
		List<Post> searchedPosts = postDao.findPostByCode(keyWord);
		return makeBoardList(idMember, searchedPosts);
	}

	@Override
	public List<Board> findByPostWriterKeyword(long idMember, String keyWord) {
		List<Post> searchedPosts = postDao.findPostByPostWriter(keyWord);
		return makeBoardList(idMember, searchedPosts);
	}


	@Override
	public List<Board> findPostByNewsfeedOrderByLike(long idMember) {
		List<Post> newsPosts = postDao.findPostByNewsfeedOrderByLike(idMember);
		return makeBoardList(idMember, newsPosts);
	}

	private List<Board> makeBoardList(long idMember, List<Post> posts){
		List<Board> boards = new ArrayList<>();
		Map<String,String> overlapCheck = new HashMap<>();
		for(Post post : posts) {
			if(overlapCheck.containsKey(post.getIdpost()+"")) continue;
			else {
				overlapCheck.put(post.getIdpost()+"", "check");
				List<Like> isPostLike = likeDao.findLike(new Like(0, post.getIdpost(), idMember, 0));
				if(isPostLike.size() != 0) {
					post.setLikeCheck(1);
				}
				List<Tag> tagList = tagDao.findAllTagIncludedPost(post.getIdpost());
				List<String> tags = new ArrayList<>();
				for(Tag tag : tagList) {
					tags.add(tag.getTagName());
				}
				//List<Member> likes = memberDao.findWhoPressedTheLikeButton(post.getIdpost());
				post.setLikeCount(likeDao.findLike(new Like(0, post.getIdpost(), 0, 0)).size());
				int commentCount = commentDao.findComment(new Comment(0, 0, post.getIdpost(), null, null, null, null, 0)).size();
				boards.add(new Board(post, tags, commentCount));
			}
		}
		return boards;
	}
	
	private List<Board> makeBoardList(long idMember, List<Post> posts, int order){
		List<Board> boards = new ArrayList<>();
		Map<String,String> overlapCheck = new HashMap<>();
		for(Post post : posts) {
			if(overlapCheck.containsKey(post.getIdpost()+"")) continue;
			else {
				overlapCheck.put(post.getIdpost()+"", "check");
				List<Like> isPostLike = likeDao.findLike(new Like(0, post.getIdpost(), idMember, 0));
				if(isPostLike.size() != 0) {
					post.setLikeCheck(1);
				}
				List<Tag> tagList = tagDao.findAllTagIncludedPost(post.getIdpost());
				List<String> tags = new ArrayList<>();
				for(Tag tag : tagList) {
					tags.add(tag.getTagName());
				}
				//List<Member> likes = memberDao.findWhoPressedTheLikeButton(post.getIdpost());
				post.setLikeCount(likeDao.findLike(new Like(0, post.getIdpost(), 0, 0)).size());
				int commentCount = commentDao.findComment(new Comment(0, 0, post.getIdpost(), null, null, null, null, 0)).size();
				boards.add(new Board(post, tags, commentCount));
			}
		}
		if(order == 1) {
			Collections.sort(boards, new Comparator<Board>() {
				public int compare(Board o1, Board o2) {
					return o1.getPost().getLikeCount() > o2.getPost().getLikeCount() ? 1 : -1;
				}
			});
		}else if(order == 2) {
			Collections.sort(boards, new Comparator<Board>() {
				@Override
				public int compare(Board o1, Board o2) {
					return o1.getPost().getLikeCount() > o2.getPost().getLikeCount() ? -1 : 1;
				}
			});
		}
		return boards;
	}

	@Override
	public List<Board> findByAllKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		List<Post> list = postDao.findByAllKeywordMyPosts(orderSearchKeyword);
		return makeBoardList(orderSearchKeyword.getMyIdMember(),list);
	}

	@Override
	public List<Board> findByTagKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		List<Post> list = postDao.findByTagKeywordMyPosts(orderSearchKeyword);
		return makeBoardList(orderSearchKeyword.getMyIdMember(),list);
	}

	@Override
	public List<Board> findByPostTitleKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		List<Post> list = postDao.findByPostTitleKeywordMyPosts(orderSearchKeyword);
		return makeBoardList(orderSearchKeyword.getMyIdMember(),list);
	}

	@Override
	public List<Board> findByPostCodeKeywordMyPosts(OrderSearchKeyword orderSearchKeyword) {
		List<Post> list = postDao.findByPostCodeKeywordMyPosts(orderSearchKeyword);
		return makeBoardList(orderSearchKeyword.getMyIdMember(),list);
	}

}
