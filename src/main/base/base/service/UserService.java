package base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import base.bean.UserBean;
import base.dao.IUserDAO;
import base.util.Constent;

@Service
public class UserService {

	public static final String CREATE_USER_SUCCESS_VIEW = "create_user_success";
	public static final String CREATE_USER_FAIL_VIEW = "create_user_fail";

	@Autowired
	IUserDAO mUserDAO;

	/**
	 * 创建用户
	 * 
	 * @param userBean
	 * @return
	 */
	public boolean createUser(UserBean userBean,Model model) {
		boolean result = mUserDAO.createUser(userBean);
		if (result) {
			model.addAttribute("userBean", userBean);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 查找用户
	 * 
	 * @param name
	 */
	public void searchUser(String name) {
		UserBean userBean = mUserDAO.searchUser(name);
		if (userBean == null) {
			// TODO
		} else {

		}
	}


	/**用户登录
	 * @param userBean
	 * @param model
	 * @return
	 */
	public String login(UserBean userBean,Model model) {
		UserBean result = mUserDAO.login(userBean);
		if(result==null || result.isEmpty()){
			return Constent.ERROR_PAGE;
		}else {
			model.addAttribute("userBean",result);
			return "redirect:/home";
		}
	}

}
