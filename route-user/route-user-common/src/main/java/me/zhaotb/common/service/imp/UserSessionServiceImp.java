package me.zhaotb.common.service.imp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import me.zhaotb.common.dao.UserSessionDAO;
import me.zhaotb.common.service.UserSessionService;

@Service
public class UserSessionServiceImp implements UserSessionService{
	
	@Autowired
	private UserSessionDAO dao;

	@Transactional
	public boolean saveUser(String key, String user) {
		return dao.saveUser(key, user);
	}

	public String getUser(String key) {
		return dao.getUser(key);
	}

	public void invalidSession(String key) {
		dao.invalidSession(key);
	}

	public boolean accessExistence(String key) {
		return dao.accessExistence(key);
	}

	
}
