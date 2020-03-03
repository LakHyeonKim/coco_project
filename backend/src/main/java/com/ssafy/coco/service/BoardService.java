package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.relationvo.OrderSearchKeyword;
import com.ssafy.coco.vo.Post;

public interface BoardService {
	public List<Board> findByAllNewsfeed(long idMember);
	public List<Board> findByAllNewsfeedScrollDown(long idMember, long lastIdPost);
 	public List<Board> findByAllDefaultSearch(long idMember);
 	public List<Board> findByAllDefaultSearchScrollDown(long idMember, long lastIdPost);
	public List<Board> findByMyPosts(long myIdMember, long youIdMember, int order);
	public List<Board> findByAllKeywordMyPosts(OrderSearchKeyword orderSearchKeyword);
 	public List<Board> findByAllKeyword(long idMember, String keyWord);
	public List<Board> findByTagKeyword(long idMember, String keyWord);
	public List<Board> findByTagKeywordMyPosts(OrderSearchKeyword orderSearchKeyword);
	public List<Board> findByPostTitleKeyword(long idMember, String keyWord);
	public List<Board> findByPostTitleKeywordMyPosts(OrderSearchKeyword orderSearchKeyword);
	public List<Board> findByPostCodeKeyword(long idMember, String keyWord);
	public List<Board> findByPostCodeKeywordMyPosts(OrderSearchKeyword orderSearchKeyword);
	public List<Board> findByPostWriterKeyword(long idMember, String keyWord);
	public List<Board> findPostByNewsfeedOrderByLike(long idMember);
}
