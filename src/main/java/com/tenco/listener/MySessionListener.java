package com.tenco.listener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;

/*
 * 세션이 생성될때 감지.. 사용
 */
@WebListener
public class MySessionListener implements HttpSessionListener{

	private static final Logger logger = Logger.getLogger(MySessionListener.class.getName());
	
	private String timeFormat() {
		SimpleDateFormat time = new SimpleDateFormat("hh:mm:ss a");
		Date today = new Date();
		String todayTime = time.format(today);
		
		return todayTime;
	}
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		// 세션 생성 시 실행됨
		logger.info("새로운 세션이 생성됨 " + se.getSession().getId());
		se.getSession().setAttribute("loginTime" , System.currentTimeMillis());
	}
	
	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		// 세션 소멸 시 실행됨
		Long loginTime = (Long) se.getSession().getAttribute("loginTime");
		Long logoutTime = System.currentTimeMillis();
		
		if(loginTime != null) {
			Long sessionDurationMs = logoutTime - loginTime; // 밀리초 단위
			double sessionDurationSec = sessionDurationMs/1000.0; // 초 단위로 변환
			System.out.println("세션 지속 시간 : " + sessionDurationSec);
		}
	}
	
}
