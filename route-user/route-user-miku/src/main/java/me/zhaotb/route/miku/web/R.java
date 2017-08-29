package me.zhaotb.route.miku.web;

/**
 * 
 * 常量类
 */
public final class R {
	
	/**
	 * 授权的URL，可选参数：server ->用来表示授权完成后跳转的服务
	 */
	public static final String AUTHORIZE_URL = "http://localhost:8090/route-user-miku/authorize/user";

	/**
	 * 会话失效时间30分钟
	 */
	public static final int SESSION_TIME_OUT = 1800;
	
	/**
	 * 全局的一些存在session中的变量
	 * @author lenovo
	 *
	 */
	public static final class C{
		public static final String USER_IN_SESSION = "user"; 
		public static final String RID = "RSESSIONID";
		public static final String RID_DESC = "全局会话id";
	}
	
}
