package com.ssafy.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.vo.Participants;

@Repository
public class ParticipantsDAOImpl implements ParticipantsDAO {
	
	@Autowired
	private SqlSession sqlSession; 

	@Override
	public List<Participants> findAllParticipants() {
		return sqlSession.selectList("ParticipantsMapper.findAllParticipants");
	}

	@Override
	public List<Participants> findParticipants(Participants participants) {
		return sqlSession.selectList("ParticipantsMapper.findParticipants", participants);
	}

	@Override
	public int addParticipants(Participants participants) {
		return sqlSession.insert("ParticipantsMapper.addParticipants", participants);
	}

	@Override
	public int updateParticipants(Participants participants) {
		return sqlSession.update("ParticipantsMapper.updateParticipants", participants);
	}

	@Override
	public int deleteParticipants(Participants participants) {
		return sqlSession.delete("ParticipantsMapper.deleteParticipants", participants);
	}

}
