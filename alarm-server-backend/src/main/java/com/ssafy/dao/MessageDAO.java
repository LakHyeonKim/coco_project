package com.ssafy.dao;

import java.util.List;

import com.ssafy.vo.Message;

public interface MessageDAO {
	public List<Message> findAllMessage();
	public List<Message> findMessage(Message message);
	public int addMessage(Message message);
}
