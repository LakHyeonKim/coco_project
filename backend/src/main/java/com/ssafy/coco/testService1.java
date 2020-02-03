package com.ssafy.coco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.vo.WordDictionary;

@Service
public class testService1 {
	@Autowired
	private testdao testdao;
	
	public void insertWD(WordDictionary wd)
	{
		testdao.insertWD(wd);
	}
}
