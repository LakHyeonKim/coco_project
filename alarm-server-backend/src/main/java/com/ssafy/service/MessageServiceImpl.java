package com.ssafy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.dao.MessageDAO;
import com.ssafy.vo.Message;
import com.ssafy.vo.Message.MessageType;

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
	
	@Transactional
	@Override
	public Message addMessage(Message message) {
		messageDAO.addMessage(message);
		List<Message> result = messageDAO.findMessage(message);
		return result.get(0);
	}

}
