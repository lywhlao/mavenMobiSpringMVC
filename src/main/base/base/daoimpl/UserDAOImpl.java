package base.daoimpl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import base.bean.UserBean;
import base.dao.IUserDAO;
import base.mapper.UserBeanMapper;

public class UserDAOImpl implements IUserDAO {

	private JdbcTemplate mJdbcTemplate;
	private static final String CRATE_USER_STRING = "insert into user(name,password,email) values(?,?,?)";
	private static final String QUERY_USER = "select * from user where name=?";
	private static final String LOGIN = "select * from user where name=? and password=?";

	public UserDAOImpl(JdbcTemplate jdbcTemplate) {
		this.mJdbcTemplate = jdbcTemplate;
	}

	public boolean createUser(UserBean userBean) {
		String name = userBean.getUserName();
		String passwrod = userBean.getPassword();
		String email = userBean.getPassword();
		boolean result = true;
		try {
			mJdbcTemplate.update(CRATE_USER_STRING, name, passwrod, email);
		} catch (DataAccessException e) {
			e.printStackTrace();
			result = false;
		}
		return result;
	}

	public UserBean searchUser(String name) {
		UserBean userBean = mJdbcTemplate.queryForObject(QUERY_USER,
				new Object[] { name }, new UserBeanMapper());
		return userBean;
	}

	public boolean updatePaasword(UserBean userBean) {
		// TODO Auto-generated method stub
		return false;
	}


	public UserBean login(UserBean userBean) {
		String name = userBean.getUserName();
		String password = userBean.getPassword();
		// 密码加入MD5校验
		//TODO
		UserBean result;
		try {
			result = mJdbcTemplate.queryForObject(LOGIN, new Object[] { name,
					password }, new UserBeanMapper());
		} catch (Exception e) {
			result=null;
		}
		return result;
	}

}
