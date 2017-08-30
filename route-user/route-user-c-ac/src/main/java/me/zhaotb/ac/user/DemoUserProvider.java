package me.zhaotb.ac.user;

import me.zhaotb.common.dao.imp.UserSessionDAOImp;

public class DemoUserProvider extends UserSessionDAOImp implements UserProvider{

	public UserState valideUser(String account, String password) {
		UserState user = new UserState();
		if("ztb".equals(account) && "123456".equals(password)) {
			user.setState(200);
			user.setUser("Zhao Tangbo");
		}
		return user;
	}

	
}
