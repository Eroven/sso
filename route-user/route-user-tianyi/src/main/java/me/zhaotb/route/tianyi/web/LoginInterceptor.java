package me.zhaotb.route.tianyi.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import me.zhaotb.common.jms.JMSHander;
import me.zhaotb.common.service.UserSessionService;
import me.zhaotb.common.utils.AuthorizeFailureHander;
import me.zhaotb.common.utils.R;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private JMSHander jms;
	
	@Autowired
	private UserSessionService redis;
	
	private static AuthorizeFailureHander hander = new AuthorizeFailureHander();
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		String rId = (String) session.getAttribute(R.C.RID);
		if(rId != null) {//在session存在全局会话id(已经登录)
			boolean exist = redis.accessExistence(rId);
			if(exist) {
				Object user = session.getAttribute(R.C.USER_IN_SESSION);
				if(user == null) {//将全局session中的user值取出来储存在局部session中
					session.setAttribute(R.C.USER_IN_SESSION, redis.getUser(rId));
				}					
				return true;
			}else {//全局会话摧毁，则表示已经登出，那么重定向去授权
				session.invalidate();
				response.sendRedirect(R.AUTHORIZE_URL+"?server="+request.getRequestURL().toString());
				return false;
			}
		}
		
		String tk = request.getParameter("tk");//刚拿到授权码
		if(tk != null) {//tk不为null说明已经经过授权
			String rID = jms.recevieRID(tk);//拿到全局会话id
			if(rID != null) {
				String user = redis.getUser(rID);
				if(user == null) {
					hander.handFailure(request, response);
					return false;
				}
				hander.handSusccess(request, response);
				session.setAttribute(R.C.RID, rID);
				session.setAttribute(R.C.USER_IN_SESSION, user);
				return true;
			}
		}
		
		//用户未登录、授权失败、其他情况     重定向到授权中心
		String url = request.getRequestURL().toString();
		response.sendRedirect(R.AUTHORIZE_URL+"?server="+url);
		return false;
	}

	
}
