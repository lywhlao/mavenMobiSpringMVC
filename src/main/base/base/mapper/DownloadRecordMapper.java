package base.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import base.bean.DownloadRecordBean;

public class DownloadRecordMapper implements RowMapper<DownloadRecordBean>{

	public DownloadRecordBean mapRow(ResultSet rs, int arg1) throws SQLException {
		DownloadRecordBean downloadRecordBean= new DownloadRecordBean();
		downloadRecordBean.setContent(rs.getString("content"));
		downloadRecordBean.setUserName(rs.getString("name"));
		downloadRecordBean.setTime(rs.getDate("date"));
		return downloadRecordBean;
	}

}
