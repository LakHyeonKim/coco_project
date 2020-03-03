package com.ssafy.service;

import com.ssafy.vo.Participants;

public interface ParticipantsService {
	public int deleteParticipants(Participants participants);
	public int countRoomMember(long roomId);
}
