package com.ssafy.service;

import java.util.List;

import com.ssafy.vo.Message;
import com.ssafy.vo.Participants;

public interface MessageService {
	public List<Message> findAllMessage();
	public List<Message> findMessage(Participants participants);
	public Message addMessage(Message message);
}
