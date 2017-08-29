package me.zhaotb.ac.user;

/**
 * 
 * 封装用户信息
 *
 */
public class UserState {
	Object user;
	int state = 404;
	String comment;
	/**
	 * 当且仅当{@code state}值为200时才有值
	 * @see UserState#getState()
	 * @return
	 */
	public Object getUser() {
		return user;
	}
	/**
	 * 状态码：
	 * <table>
	 * 	<tr><td>码:</td><td>意义</td></tr>
	 * 	<tr><td>200:</td><td>用户存在且有效</td></tr>
	 * 	<tr><td>300:</td><td>用户存在但无效</td></tr>
	 * 	<tr><td>404:</td><td>账号不存在</td></tr>
	 * 	<tr><td>500:</td><td>密码错误</td></tr>
	 * </table>
	 */
	public int getState() {
		return state;
	}
	public void setUser(Object user) {
		this.user = user;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
