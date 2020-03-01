package com.ssafy.coco.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ssafy.coco.dao.MyPageDao;

@Component
public class TodayReset {
	
	private static final Logger logger = LoggerFactory.getLogger(TodayReset.class);
	
	@Autowired
	private MyPageDao myPageDao;
	
	@Scheduled(cron="0 0 0 1/1 * ? *") // "* * * 1 * *"
	public void todayCountReset() {
		logger.info("all member page reset");
		try {
			myPageDao.updateMypageTodayVisitedReset();			
		}catch(Exception ex){
            logger.error(ex.getMessage());
        }
	}
}
