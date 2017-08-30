package me.zhaotb.common.redis;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.zhaotb.common.utils.R;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * 封装redis的客户端
 * @author ztb
 *
 */
@Component
public abstract class RedisClient {
	
	private static final Logger logger = Logger.getLogger(RedisClient.class);
	
	protected int sessionTimeout = R.SESSION_TIME_OUT;
	
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
	
}
