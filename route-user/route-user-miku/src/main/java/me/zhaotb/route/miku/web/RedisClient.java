package me.zhaotb.route.miku.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 封装redis的客户端
 * @author ztb
 *
 */
@Component
public class RedisClient {
	
	private static final Logger logger = Logger.getLogger(RedisClient.class);
	
	/**
	 * Jedis 连接池
	 */
	@Autowired
	private JedisPool pool;
	
	public RedisClient() {
		logger.info("RedisClient Created");
	}
	
	public JedisPool getPool() {
		return pool;
	}

	public void setPool(JedisPool pool) {
		this.pool = pool;
	}

	/**
	 * 获取客户端资源
	 * @return
	 */
	public Jedis getResource() {
		Jedis jedis = null;
		try {
			jedis = pool.getResource();
		} catch (Exception e) {
			logger.error("Jedis 获取资源异常");
		}
		return jedis;
	}
	
	/**
	 * 关闭连接
	 * @param jedis
	 */
	public void disconnect(Jedis jedis) {
		jedis.disconnect();
	}
	
	/**
	 * 关闭客户端
	 * @param jedis
	 */
	public void close(Jedis jedis) {
		jedis.close();
	}
	
	/**
	 * 将用户信息储存在Redis中
	 * @param key 键
	 * @param user 用户的信息，字符串形式。
	 * @return  true表示成功<br>
	 * 			false失败，当且仅当Redis中存在key与传入key值相同。建议使用不易重复的key生成方式
	 */
	public boolean saveUser(String key,String user) {
		Jedis jedis = getResource();
		boolean res = jedis.hsetnx(key, "name", user) != 0;
		if(res)
			jedis.expire(key, R.SESSION_TIME_OUT);
		jedis.close();
		return res;
	}
	
	/**
	 * 通过key得到值
	 * @param key
	 * @return
	 */
	public String getUser(String key) {
		Jedis jedis = getResource();
		String res = jedis.hget(key, "name");
		jedis.expire(key, R.SESSION_TIME_OUT);
		jedis.close();
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
		jedis.expire(key, R.SESSION_TIME_OUT);
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
