package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.WordDictionary;

public interface WordDictionaryDao {
	public List<WordDictionary> findAllWordDictionary();
	public List<WordDictionary> findWordDictionary(WordDictionary wd);
	public int addWordDictionary(WordDictionary wd);
	public int updateWordDictionary(WordDictionary wd);
	public int deleteWordDictionary(WordDictionary wd);
}
