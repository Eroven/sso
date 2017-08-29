package me.zhaotb.route.miku.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {
	
	@Autowired
	private RedisClient redis;
	
	private String user = "Zhao Tangbo";
	
	@RequestMapping("toLogin")
	public ModelAndView loginPage(HttpServletRequest request) {
		return new ModelAndView("login.jsp");
	}

	@RequestMapping("doLogin")
	public void login(String account,String password,HttpSession session,HttpServletRequest request,HttpServletResponse response) throws Exception {
		if("ztb".equals(account) && "123456".equals(password)) {
			String RID = RandomUtil.uuid();
			redis.saveUser(RID, user);
			Cookie c = new Cookie(R.C.RID, RID);
			c.setMaxAge(-1);//当浏览器关闭时，删除全局会话id
			response.addCookie(c);
			response.sendRedirect(R.AUTHORIZE_URL);
		}
	}
}
