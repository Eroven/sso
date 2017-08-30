package me.zhaotb.common.dao.imp;

import org.springframework.stereotype.Repository;

import me.zhaotb.common.dao.UserSessionDAO;
import me.zhaotb.common.redis.RedisClient;
import me.zhaotb.common.utils.R;
import redis.clients.jedis.Jedis;

@Repository
public class UserSessionDAOImp extends RedisClient implements UserSessionDAO {
	
	public boolean saveUser(String key,String user) {
		Jedis jedis = getResource();
		boolean res = "OK".equals(jedis.set(key, user)) ;
		if(res)
			jedis.expire(key, R.SESSION_TIME_OUT);
		jedis.close();
		return res;
	}
	
	public String getUser(String key) {
		Jedis jedis = getResource();
		String res = null;
		try {
			res = jedis.get(key);
			jedis.expire(key, sessionTimeout);
		}catch(Exception e) {
		}finally {
			jedis.close();
		}
		return res;
	}

	/**
	 * 访问指定key的存在性，同时会刷新session timeout 时间
	 * @param key
	 * @return
	 */
	public boolean accessExistence(String key) {
		Jedis jedis = getResource();
		boolean res = jedis.exists(key);
		jedis.expire(key, sessionTimeout);
		jedis.close();
		return res;
	}
	
	/**
	 * 使指定的全局session失效
	 * @param key
	 */
	public void invalidSession(String key) {
		Jedis jedis = getResource();
		jedis.expire(key, 0);
		jedis.close();
	}
}
