package base.dao;

import base.bean.UserBean;

public interface IUserDAO {

	//创建用户
	public boolean createUser(UserBean userBean);

	//查找用户
	public UserBean searchUser(String name);

	//更新密码
	public boolean updatePaasword(UserBean userBean);
	
	//登录用户
	public UserBean login(UserBean userBean);
}
