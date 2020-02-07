package com.ssafy.coco.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.service.AlarmService;
import com.ssafy.coco.vo.Alarm;

@Service
public class AlarmServiceImpl implements AlarmService{
	@Autowired
	private AlarmDao alarmdao;

	@Override
	public List<Alarm> findAllAlarm() {
		return alarmdao.findAllAlarm();
	}

	@Override
	public List<Alarm> findAlarm(Alarm alarm) {
		return alarmdao.findAlarm(alarm);
	}

	@Override
	public int addAlarm(Alarm alarm) {
		return alarmdao.addAlarm(alarm);
	}

	@Override
	public int updateAlarm(Alarm alarm) {
		return alarmdao.updateAlarm(alarm);
	}

	@Override
	public int deleteAlarm(Alarm alarm) {
		return alarmdao.deleteAlarm(alarm);
	}
}
