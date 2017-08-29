package me.zhaotb.route.miku.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Deprecated
public class AuthorizeFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String url = ((HttpServletRequest) request).getRequestURL().toString();
		if(url != null && url.endsWith("login.html")) {
			chain.doFilter(request, response);
			return;
		}
		Object object = getRedisUser((HttpServletRequest)request);
		System.out.println("Miku:" + object);
		if (object != null)
			chain.doFilter(request, response);
		else
			((HttpServletResponse) response).sendRedirect("http://localhost:8090/route-user-miku");
	}

	public void destroy() {
	}
	
	private RedisClient redis;
	private Integer SESSION_TIME_OUT = 1800;
	private Object getRedisUser(HttpServletRequest request) {
		ensureResource();
		Jedis jedis = redis.getResource();
		String tk = request.getRemoteAddr();
		String name = jedis.hget("user:"+tk, "name");
		if(name != null)
			jedis.expire("user:"+tk, SESSION_TIME_OUT);
		return name;
	}
	
	public void ensureResource() {
		if(redis == null) {
			redis = new RedisClient();
			JedisPool pool = new JedisPool(new JedisPoolConfig(),"192.168.71.128",6379);
			redis.setPool(pool);
		}
	}

}
