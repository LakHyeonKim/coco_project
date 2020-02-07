package com.ssafy.coco.service;

import java.util.List;

import com.ssafy.coco.vo.WordDictionary;

public interface WordDictionaryService {
	public List<WordDictionary> findAllWordDictionary();
	public List<WordDictionary> findWordDictionary(WordDictionary wd);
	public int addWordDictionary(WordDictionary wd);
	public int updateWordDictionary(WordDictionary wd);
	public int deleteWordDictionary(WordDictionary wd);
}
