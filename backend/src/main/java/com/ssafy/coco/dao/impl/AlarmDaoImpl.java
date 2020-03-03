package com.ssafy.coco.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ssafy.coco.dao.AlarmDao;
import com.ssafy.coco.vo.Alarm;

@Repository
public class AlarmDaoImpl implements AlarmDao{
	private String ns = "Alarm_Mapper.";
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public List<Alarm> findAllAlarm() {
		return sqlSession.selectList(ns+"findAllAlarm");
	}

	@Override
	public List<Alarm> findAlarm(Alarm alarm) {
		return sqlSession.selectList(ns+"findAlarm",alarm);
	}

	@Override
	public int addAlarm(Alarm alarm) {
		return sqlSession.insert(ns+"addAlarm",alarm);
	}

	@Override
	public int updateAlarm(Alarm alarm) {
		return sqlSession.update(ns+"updateAlarm",alarm);
	}

	@Override
	public int deleteAlarm(Alarm alarm) {
		return sqlSession.delete(ns+"deleteAlarm",alarm);
	}
}
