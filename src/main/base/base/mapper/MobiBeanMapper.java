package base.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import base.bean.MobiBean;

public class MobiBeanMapper implements RowMapper<MobiBean> {

	public MobiBean mapRow(ResultSet rs, int arg1) throws SQLException {
		MobiBean mobiBean = new MobiBean();
		mobiBean.setContent(rs.getString("content"));
		mobiBean.setUrl(rs.getString("url"));
		mobiBean.setAuthor(rs.getString("author"));
		mobiBean.setDescription(rs.getString("description"));
		return mobiBean;
	}
}
