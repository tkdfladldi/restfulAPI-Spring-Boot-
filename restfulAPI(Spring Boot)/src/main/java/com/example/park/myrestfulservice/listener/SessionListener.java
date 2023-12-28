package com.example.park.myrestfulservice.listener;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.park.myrestfulservice.model.AdminUser;

@WebListener 
public class SessionListener implements HttpSessionListener {
	private final Logger logger = LoggerFactory.getLogger(SessionListener.class);
	private final Map<String, HttpSession> sessions = new ConcurrentHashMap<>();
	
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		logger.info("###########"); 
		logger.info("# Session created");
		logger.info("###########");
	
		HttpSession session = se.getSession();
	
		sessions.put(session.getId(), session);
	}

	@Override
	public void sessionDestroyed (HttpSessionEvent se) {
		logger.info("#***************");
		logger.info("# Session destroyed");
		logger.info("#***************");
		
		HttpSession session = se.getSession();
		String sessionId  = session.getId();
		
		if (sessions.get(sessionId) != null) {
			sessions.remove(sessionId).invalidate();
		}
	}
	

	public synchronized void checkSession(String id) {
		for (String key: sessions.keySet()) {
			HttpSession session =sessions.get(key);
			AdminUser admin = (AdminUser) session.getAttribute("admin");
			
			if (admin != null && Objects.equals(id, String.valueOf(admin.getId()))) {
				session.invalidate();
			}
		}
	}
}
