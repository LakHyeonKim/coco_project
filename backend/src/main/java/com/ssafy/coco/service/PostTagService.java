package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.vo.PostTag;

public interface PostTagService {
	public List<PostTag> findAllPostTag();
	public List<PostTag> findPostTag(PostTag postTag);
	public int addPostTag(PostTag postTag);
	public int deletePostTag(PostTag postTag);
}
