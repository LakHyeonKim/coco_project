package com.ssafy.coco;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.coco.vo.WordDictionary;

@Repository
public class testdao {
	@Autowired
	SqlSession sqlsession;
	
	public void insertWD(WordDictionary wd)
	{
		sqlsession.insert("WordDictionary_Mapper.addWordDictionary", wd);
	}
}
