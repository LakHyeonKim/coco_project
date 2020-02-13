package com.ssafy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.ssafy.dao.ChatRoomRepository;
import com.ssafy.dao.RoomDAO;
import com.ssafy.service.MessageService;
import com.ssafy.service.RoomService;
import com.ssafy.vo.Message;
import com.ssafy.vo.Room;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/chat")
@CrossOrigin(origins = { "*" }, maxAge = 6000)
public class ChatRoomController {
	
	@Autowired
    private RoomService roomService;
	@Autowired
    private MessageService messageService;

    @GetMapping("/room")
    public String rooms(Model model) {
        return "/chat/room";
    }

    @GetMapping("/rooms")
    @ResponseBody
    public List<Room> room() {
        return roomService.findAllRoom();
    }

    @PostMapping("/room")
    @ResponseBody
    public Room createRoom(@RequestParam Room room) {
        return roomService.addRoom(room);
    }

//    @GetMapping("/room/enter/{roomId}")
//    public String roomDetail(Model model, @PathVariable String roomId) {
//        model.addAttribute("roomId", roomId);
//        return "/chat/roomdetail";
//    }

    @GetMapping("/room/{roomId}")
    @ResponseBody
    public Room roomInfo(@PathVariable long idroom) {
    	Room searchRoom = new Room();
    	searchRoom.setIdroom(idroom);
        return roomService.findRoom(searchRoom).get(0);
    }
    
    @GetMapping("/messages/{roomId}")
    @ResponseBody
    public Message roomMessage(@PathVariable long roomId) {
    	Message message = new Message();
    	message.setRoomId(roomId);
        return messageService.findMessage(message).get(0);
    }
}
