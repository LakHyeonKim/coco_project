package com.ssafy.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.dao.RoomDAO;
import com.ssafy.vo.Room;

@Service
public class RoomServiceImpl implements RoomService {

	
	@Autowired
	private RoomDAO roomDAO;
	
	@Override
	public List<Room> findAllRoom() {
		return roomDAO.findAllRoom();
	}

	@Override
	public List<Room> findRoom(Room room) {
		return roomDAO.findRoom(room);
	}
	
	@Transactional
	@Override
	public Room addRoom(Room room) {
		Room result = Room.create(room);
		roomDAO.addRoom(result);
		return result;
	}

	@Override
	public int updateRoom(Room room) {
		return roomDAO.updateRoom(room);
	}

	@Override
	public int deleteRoom(Room room) {
		return roomDAO.deleteRoom(room);
	}

}
