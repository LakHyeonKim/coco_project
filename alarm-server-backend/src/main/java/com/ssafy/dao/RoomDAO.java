package com.ssafy.dao;

import java.util.List;

import com.ssafy.vo.Room;

public interface RoomDAO {
	public List<Room> findAllRoom();
	public List<Room> findRoom(Room room);
	public int addRoom(Room room);
	public int updateRoom(Room room);
	public int deleteRoom(Room room);
}
