package com.ssafy.coco.dao;

import java.util.List;
import java.util.Map;

import com.ssafy.coco.vo.Post;

public interface PostDao {
	public List<Post> findAllPost();
	public List<Post> findPost(Post post);
	public List<Post> findPostByNewsfeed(long idMemberFollower);
	public List<Post> findPostByAll(String keyWord);
	public List<Post> findPostByTag(String keyWord);
	public List<Post> findPostByTitle(String keyWord);
	public List<Post> findPostByCode(String keyWord);
	public List<Post> findPostByPostWriter(String keyWord);
	public List<Post> findPostByFrequency(long idMember);
	public List<Post> findPostByPostComment(long idPostParents);
	public List<Post> findPostByNewsfeedOrderByLike(long idMemberFollower);
	public List<Post> findByAllNewsfeedScrollDown(Map<String,Long> hashMap);
	public int addPost(Post post);
	public int updatePost(Post post);
	public int updatePostViewCount(long idPost);
	public int updatePostUnlikeCount(long idPost);
	public int updatePostlikeCount(long idPost);
	public int deletePost(Post post);
}
