package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.relationvo.Board;
import com.ssafy.coco.vo.Post;

public interface BoardService {
	public List<Board> findByAllNewsfeed(long idMember);
	public List<Board> findByAllDefaultSearch(long idMember);
	public List<Board> findByMyPosts(long idMember);
	public List<Board> findByAllKeyword(String keyWord);
	public List<Board> findByTagKeyword(String keyWord);
	public List<Board> findByPostTitleKeyword(String keyWord);
	public List<Board> findByPostCodeKeyword(String keyWord);
	public List<Board> findByPostWriterKeyword(String keyWord);
	public List<Board> findPostByNewsfeedOrderByLike(long idMemberFollower);
	public List<Board> findByMyPostsOrderByLike(long idMember);
	
}
