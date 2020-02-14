package com.ssafy.service;

import java.util.List;

import com.ssafy.vo.Message;

public interface MessageService {
	public List<Message> findAllMessage();
	public List<Message> findMessage(Message message);
	public Message addMessage(Message message);
}
