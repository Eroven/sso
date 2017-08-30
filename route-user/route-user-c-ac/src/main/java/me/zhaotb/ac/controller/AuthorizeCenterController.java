package me.zhaotb.ac.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;

import me.zhaotb.ac.user.UserProvider;
import me.zhaotb.ac.user.UserState;
import me.zhaotb.common.dao.UserSessionDAO;
import me.zhaotb.common.jms.JMSHander;
import me.zhaotb.common.utils.R;
import me.zhaotb.common.utils.RandomUtil;

@Controller
@RequestMapping("authorize")
public class AuthorizeCenterController {
	
	@Autowired
	private UserProvider provider;

	@Autowired
	private JMSHander jms;

	@Autowired
	private UserSessionDAO redis;

	@RequestMapping("user")
	public void authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String serCli = request.getParameter("server");
		if (serCli != null)
			session.setAttribute(R.C.LAST_SERVER, serCli);
		String rID = getRID(request);
		if (rID == null || !redis.accessExistence(rID)) {// 授权中心表示没有用户信息，则需要用户登录
			response.sendRedirect(R.DEFAULT_LOGIN_PAGE);
			return;
		}

		String tk = RandomUtil.uuid();
		jms.sendRID(tk, rID);
		Object lastServer = session.getAttribute(R.C.LAST_SERVER);
		if (lastServer != null)
			response.sendRedirect(lastServer.toString() + "?tk=" + tk);
		else
			response.sendRedirect(R.DEFAULT_INDEX + "?tk=" + tk);
	}

	/**
	 * 通过获取cookie中的RSESSIONID来获取全局会话保存的用户信息
	 * 
	 * @param request
	 * @return
	 */
	private String getRID(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie c : cookies) {
				if (R.C.RID.equals(c.getName())) {
					return c.getValue();
				}
			}
		return null;
	}
	
	@RequestMapping("doLogin")
	public void login(String account,String password,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if(provider == null)
			throw new NullPointerException("请在spring容器中配置me.zhaotb.ac.user.UserProvider的实现类（注解或配置bean）");
		UserState state = provider.valideUser(account, password);
		if(200 == state.getState()) { //验证通过,创建全局session
			String RID = RandomUtil.uuid();
			redis.saveUser(RID, JSON.toJSONString(state.getUser()));
			Cookie c = new Cookie(R.C.RID, RID);
			c.setMaxAge(-1);//当浏览器关闭时，删除全局会话id
			response.addCookie(c);
			response.sendRedirect(R.AUTHORIZE_URL);
		}
	}
	
	@RequestMapping("logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
		String RID = getRID(request);
		if(RID != null)
			redis.invalidSession(RID.toString());//摧毁全局Session
		Object server = request.getSession().getAttribute("lastServer");
		if(server == null)
			server = R.DEFAULT_INDEX;
		response.sendRedirect(R.AUTHORIZE_URL+"?server="+server);
	}

}
