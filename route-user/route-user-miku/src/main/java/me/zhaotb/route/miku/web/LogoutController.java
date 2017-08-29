package me.zhaotb.route.miku.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LogoutController {
	
	@Autowired
	private RedisClient redis;

	@RequestMapping("logout")
	public void logout(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Object RID = request.getSession().getAttribute(R.C.RID);
		if(RID != null)
			redis.invalidSession(RID.toString());//摧毁全局Session
		response.sendRedirect(R.AUTHORIZE_URL);
	}
}
