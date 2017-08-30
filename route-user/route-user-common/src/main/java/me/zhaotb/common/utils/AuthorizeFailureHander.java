package me.zhaotb.common.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.http.auth.AuthenticationException;

/**
 * 处理授权失败的情况
 * 
 * @author lenovo
 *
 */
public class AuthorizeFailureHander {

	public void handFailure(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		Integer count = (Integer) session.getAttribute(R.C.FAILED_COUNTER);
		if (count == null) {
			count = Integer.valueOf(1);
		} else {
			count += 1;
		}
		if (count < 4) {
			session.setAttribute(R.C.FAILED_COUNTER, count);
			response.sendRedirect(R.AUTHORIZE_URL+"?server="+request.getRequestURL().toString());
		}else {
			throw new AuthenticationException("在尝试3次授权后，任然失败");
		}
			
	}

	public void handSusccess(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().setAttribute(R.C.FAILED_COUNTER, null);
	}
}
