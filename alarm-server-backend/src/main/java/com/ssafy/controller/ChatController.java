package com.ssafy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.ssafy.service.MessageService;
import com.ssafy.vo.Message;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Controller
public class ChatController {
	
    private final SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private MessageService messageService;

    @MessageMapping("/chat/message")
    public void message(Message message) {
        if (Message.MessageType.ENTER.equals(message.getType()))
            message.setContext(message.getNickName() + "님이 입장하셨습니다.");
        messageService.addMessage(message);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
    }
}