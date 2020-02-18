package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.vo.Room;

@Repository
public class RoomDAOImpl implements RoomDAO {
	
	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public List<Room> findAllRoom() {
		return sqlSession.selectList("RoomMapper.findAllRoom");
	}

	@Override
	public List<Room> findRoom(Room room) {
		return sqlSession.selectList("RoomMapper.findRoom",room);
	}

	@Override
	public int addRoom(Room room) {
		return sqlSession.insert("RoomMapper.addRoom",room);
	}

	@Override
	public int updateRoom(Room room) {
		return sqlSession.update("RoomMapper.updateRoom",room);
	}

	@Override
	public int deleteRoom(Room room) {
		return sqlSession.delete("RoomMapper.deleteRoom",room);
	}

}
