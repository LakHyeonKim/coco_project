package com.ssafy.dao;

import java.util.List;

import com.ssafy.vo.Participants;

public interface ParticipantsDAO {
	public List<Participants> findAllParticipants(); 
	public List<Participants> findParticipants(Participants participants);
	public int addParticipants(Participants participants);
	public int updateParticipants(Participants participants);
	public int deleteParticipants(Participants participants);
}
