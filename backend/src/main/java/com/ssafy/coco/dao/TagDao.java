package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.Tag;

public interface TagDao {
	public List<Tag> findAllTag();
	public List<Tag> findTag(Tag tag);
	public List<Tag> findAllTagIncludedPost(long idPost);
	public List<Tag> findAllTagIncludedMypage(long idMypage);
	public int addTag(Tag tag);
	public int updateTag(Tag tag);
	public int updateTagIncludedCount(String tag);
	public int deleteTag(Tag tag);
}
