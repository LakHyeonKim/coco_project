package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.vo.Message;

@Repository
public class MessageDAOImpl implements MessageDAO {

	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public List<Message> findAllMessage() {
		return sqlSession.selectList("MessageMapper.findAllMessage");
	}

	@Override
	public List<Message> findMessage(Message message) {
		return sqlSession.selectList("MessageMapper.findMessage",message);
	}

	@Override
	public int addMessage(Message message) {
		return sqlSession.insert("MessageMapper.addMessage",message);
	}

}
