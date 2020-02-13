package com.ssafy.service;

import java.util.List;

import com.ssafy.vo.Room;

public interface RoomService {
	public List<Room> findAllRoom();
	public List<Room> findRoom(Room room);
	public Room addRoom(Room room);
	public int updateRoom(Room room);
	public int deleteRoom(Room room);
}
