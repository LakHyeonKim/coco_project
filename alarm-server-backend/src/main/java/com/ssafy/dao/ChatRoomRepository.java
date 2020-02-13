//package com.ssafy.dao;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.annotation.PostConstruct;
//
//import org.springframework.stereotype.Repository;
//
//import com.ssafy.vo.Room;
//
//@Repository
//public class ChatRoomRepository {
// 
//    private Map<String, Room> chatRoomMap;
// 
//    @PostConstruct
//    private void init() {
//        chatRoomMap = new LinkedHashMap<>();
//    }
// 
//    public List<Room> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//    	List<Room> chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }
// 
//    public Room findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }
// 
//    public Room createChatRoom(String roomName) {
//        Room chatRoom = Room.create(roomName);
//        chatRoomMap.put(chatRoom.getRoomNumber(), chatRoom);
//        return chatRoom;
//    }
//}
