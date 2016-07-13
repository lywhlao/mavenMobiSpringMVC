package base.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import base.bean.ContentSimilarBean;

public class ContentRecommandMapper implements RowMapper<ContentSimilarBean>{

	public ContentSimilarBean mapRow(ResultSet rs, int arg1)
			throws SQLException {
		ContentSimilarBean contentSimilarBean=new ContentSimilarBean();
		contentSimilarBean.setContentSource(rs.getString("content_source"));
		contentSimilarBean.setContentDest(rs.getString("content_dest"));
		contentSimilarBean.setSimilar(rs.getDouble("similar"));
		contentSimilarBean.setUrl(rs.getString("url"));
		contentSimilarBean.setAuthor(rs.getString("author"));
		contentSimilarBean.setDescription(rs.getString("description"));
		return contentSimilarBean;
	}

}
