package me.zhaotb.common.dao;

public interface UserSessionDAO {

	/**
	 * 将用户信息储存在Redis中
	 * @param key 键
	 * @param user 用户的信息，字符串形式。
	 * @return  true表示成功<br>
	 * 			false失败，当且仅当Redis中存在key与传入key值相同。建议使用不易重复的key生成方式
	 */
	boolean saveUser(String key, String user);

	/**
	 * 通过key得到值
	 * @param key
	 * @return
	 */
	String getUser(String key);

	/**
	 * 摧毁session
	 * @param key
	 */
	void invalidSession(String key);

	/**
	 * 访问session的存在性
	 * @param key
	 * @return
	 */
	boolean accessExistence(String key);

}