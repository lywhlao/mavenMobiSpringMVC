package base.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import base.bean.UserBean;

public class UserBeanMapper implements RowMapper<UserBean> {

	public UserBean mapRow(ResultSet resultSet, int arg1) throws SQLException {
		UserBean userBean = new UserBean();
		userBean.setEmail(resultSet.getString("email"));
		userBean.setPassword(resultSet.getString("password"));
		userBean.setUserName(resultSet.getString("name"));
		return userBean;
	}

}
