package com.ssafy.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.dao.ParticipantsDAO;
import com.ssafy.vo.Participants;

@Service
public class ParticipantsServiceImpl implements ParticipantsService {
	
	
	@Autowired
	private ParticipantsDAO participantsDAO;
	
	@Override
	public int deleteParticipants(Participants participants) {
		return participantsDAO.deleteParticipants(participants);
	}

	@Override
	public int countRoomMember(long roomId) {
		Participants participants = new Participants();
		participants.setRoomId(roomId);
		return participantsDAO.findParticipants(participants).size();
	}

}
