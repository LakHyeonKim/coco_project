package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.coco.dao.TagDao;
import com.ssafy.coco.dao.WordDictionaryDao;
import com.ssafy.coco.service.TagService;
import com.ssafy.coco.service.WordDictionaryService;
import com.ssafy.coco.vo.Tag;
import com.ssafy.coco.vo.WordDictionary;
@Service
public class WordDictionaryServiceImpl implements WordDictionaryService{

	@Autowired
	private WordDictionaryDao wordDictionaryDao;

	@Override
	public List<WordDictionary> findAllWordDictionary() {
		return wordDictionaryDao.findAllWordDictionary();
	}

	@Override
	public List<WordDictionary> findWordDictionary(WordDictionary wd) {
		return wordDictionaryDao.findWordDictionary(wd);
	}

	@Override
	public int addWordDictionary(WordDictionary wd) {
		return wordDictionaryDao.addWordDictionary(wd);
	}

	@Override
	public int updateWordDictionary(WordDictionary wd) {
		return wordDictionaryDao.updateWordDictionary(wd);
	}

	@Override
	public int deleteWordDictionary(WordDictionary wd) {
		return wordDictionaryDao.deleteWordDictionary(wd);
	}


}
