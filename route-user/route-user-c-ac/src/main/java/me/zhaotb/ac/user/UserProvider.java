package me.zhaotb.ac.user;

public interface UserProvider {
	
	/**
	 * 在后台验证用户的合法性
	 * @param account 账号
	 * @param password 密码/验证码
	 * @return 用户的封装状态
	 * 		
	 */
	UserState valideUser(String account,String password);

}
