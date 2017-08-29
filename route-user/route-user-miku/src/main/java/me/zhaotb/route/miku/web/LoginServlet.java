package me.zhaotb.route.miku.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Deprecated
public class LoginServlet extends HttpServlet{

	private static final long serialVersionUID = -94624496562365477L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Object account = req.getParameter("account");
		Object password = req.getParameter("password");
		if("ztb".equals(account) && "123456".equals(password)) {
			session.setAttribute("user", "Zhao Tangbo");
			String tk = req.getRemoteAddr();
			saveUserInfo("Zhao Tangbo",tk,session.getId());
		}
		resp.sendRedirect("http://localhost:8090/route-user-miku/after.html");
	}

	private RedisClient redis;
	private Integer SESSION_TIME_OUT = 1800;
	private void saveUserInfo(Object info,String tk,String sid) {
		ensureResource();
		tk = tk.replaceAll("-", "");
		Jedis jedis = redis.getResource();
		jedis.hset("user:"+tk, "name",info.toString());
		jedis.hset("user:"+tk, "lastSessionId", sid);
		jedis.expire("user:"+tk, SESSION_TIME_OUT);
		jedis.close();
	}

	public void ensureResource() {
		if(redis == null) {
			redis = new RedisClient();
			JedisPool pool = new JedisPool(new JedisPoolConfig(),"192.168.71.128",6379);
			redis.setPool(pool);
		}
	}

}
