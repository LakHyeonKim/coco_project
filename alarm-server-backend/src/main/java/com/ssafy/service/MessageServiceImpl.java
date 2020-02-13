package com.ssafy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dao.MessageDAO;
import com.ssafy.vo.Message;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageDAO messageDAO;
	
	@Override
	public List<Message> findAllMessage() {
		return messageDAO.findAllMessage();
	}

	@Override
	public List<Message> findMessage(Message message) {
		return messageDAO.findMessage(message);
	}

	@Override
	public int addMessage(Message message) {
		return messageDAO.addMessage(message);
	}

}
