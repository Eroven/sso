package me.zhaotb.route.miku.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("authorize")
public class AuthorizeCenterController {

	private static final String DEFAULT_INDEX = "http://localhost:8090/route-user-miku/index";

	@Autowired 
	private JMSHander jms;

	@Autowired
	private RedisClient redis;

	@RequestMapping("user")
	public void authorize(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		String serCli = request.getParameter("server");
		if (serCli != null)
			session.setAttribute("lastServer", serCli);
		String rID = getRID(request, response);
		if (rID == null || !redis.accessExistence(rID)) {// 授权中心表示没有用户信息，则需要用户登录
			response.sendRedirect(request.getContextPath() + "/toLogin");
			return;
		}

		String tk = RandomUtil.uuid();
		jms.sendRID(tk, rID);
		Object lastServer = session.getAttribute("lastServer");
		if (lastServer != null)
			response.sendRedirect(lastServer.toString() + "?tk=" + tk);
		else
			response.sendRedirect(DEFAULT_INDEX + "?tk=" + tk);
	}

	/**
	 * 通过获取cookie中的RSESSIONID来获取全局会话保存的用户信息
	 * 
	 * @param request
	 * @return
	 */
	private String getRID(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null)
			for (Cookie c : cookies) {
				if (R.C.RID.equals(c.getName())) {
					return c.getValue();
				}
			}
		return null;
	}

}
