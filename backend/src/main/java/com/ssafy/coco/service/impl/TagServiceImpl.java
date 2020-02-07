package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.coco.dao.TagDao;
import com.ssafy.coco.service.TagService;
import com.ssafy.coco.vo.Tag;
@Service
public class TagServiceImpl implements TagService{

	@Autowired
	private TagDao tagDao;

	@Override
	public List<Tag> findAllTag() {
		return tagDao.findAllTag();
	}

	@Override
	public List<Tag> findTag(Tag tag) {
		return tagDao.findTag(tag);
	}

	@Override
	public int addTag(Tag tag) {
		return tagDao.addTag(tag);
	}

	@Override
	public int updateTag(Tag tag) {
		return tagDao.updateTag(tag);
	}

	@Override
	public int deleteTag(Tag tag) {
		return tagDao.deleteTag(tag);
	}

	@Override
	public int updateTagIncludedCount(String tag) {
		return tagDao.updateTagIncludedCount(tag);
	}

	@Override
	public List<Tag> findAllTagIncludedPost(long idPost) {
		return tagDao.findAllTagIncludedPost(idPost);
	}

	@Override
	public List<Tag> findAllTagIncludedMypage(long idMypage) {
		return tagDao.findAllTagIncludedMypage(idMypage);
	}

}
