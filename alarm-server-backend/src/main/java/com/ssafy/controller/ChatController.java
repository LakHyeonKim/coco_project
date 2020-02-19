package com.ssafy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.ssafy.service.MessageService;
import com.ssafy.service.ParticipantsService;
import com.ssafy.vo.Message;

import lombok.RequiredArgsConstructor;



@RequiredArgsConstructor
@Controller
public class ChatController {
	
    private final SimpMessageSendingOperations messagingTemplate;
    @Autowired
    private MessageService messageService;
    
    @Autowired
    private ParticipantsService participantsSerivce;

    @MessageMapping("/chat/message")
    public void message(Message message) {
        if (Message.MessageType.ENTER.equals(message.getType()))
            message.setContext(message.getNickName() + "님이 입장하셨습니다.");
        if (Message.MessageType.OUT.equals(message.getType()))
            message.setContext(message.getNickName() + "님이 퇴장하셨습니다.");
        int memberCount = participantsSerivce.countRoomMember(message.getRoomId());
        Message sendMessage = messageService.addMessage(message);
        sendMessage.setMemberCount(memberCount);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), sendMessage);
    }
}