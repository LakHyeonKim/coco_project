package com.ssafy.coco.dao;

import java.util.List;

import com.ssafy.coco.vo.Alarm;

public interface AlarmDao {
	public List<Alarm> findAllAlarm();
	public List<Alarm> findAlarm(Alarm alarm);
	public int addAlarm(Alarm alarm);
	public int updateAlarm(Alarm alarm);
	public int deleteAlarm(Alarm alarm);
}
