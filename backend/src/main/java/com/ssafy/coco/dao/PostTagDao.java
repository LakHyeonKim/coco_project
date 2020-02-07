package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.PostTag;

public interface PostTagDao {
	public List<PostTag> findAllPostTag();
	public List<PostTag> findPostTag(PostTag postTag);
	public int addPostTag(PostTag postTag);
	public int deletePostTag(PostTag postTag);
}
