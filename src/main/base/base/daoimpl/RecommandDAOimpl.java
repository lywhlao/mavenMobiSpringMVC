package base.daoimpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.jdbc.core.JdbcTemplate;

import base.bean.ContentSimilarBean;
import base.bean.DownloadRecordBean;
import base.bean.MobiBean;
import base.dao.IRecommandDAO;
import base.mapper.ContentRecommandMapper;
import base.mapper.DownloadRecordMapper;
import base.mapper.MobiBeanMapper;
import base.util.Constent;

public class RecommandDAOimpl implements IRecommandDAO {

	private JdbcTemplate mJdbcTemplate;

	private static final String INSERT_DOWNLOAD_RECORD = "insert into user_download values(?,?,?)";

	private static final String GET_DOWLOAD_RECORD="select * from user_download";
	
	private static final String INSERT_SIMILAR_VALUE="insert into content_similar values(?,?,?)";
	
	private static final String GET_RECOMMEND_CONTENT = "SELECT * from ("
			+ "SELECT DISTINCT c.content_source, c.content_dest,c.similar "
			+ "from user_download u join content_similar c "
			+ "on u.content=c.content_source where u.name=? and "
			+ "c.content_dest not in ( SELECT content from user_download "
			+ "WHERE user_download.name= ?) "
			+ "ORDER BY c.similar DESC) new_table join mobi on new_table.content_dest=mobi.content "
			+ "GROUP BY new_table.content_dest "
			+ "ORDER BY new_table.similar DESC ";
	
	private static final String GET_HOT_CONTENT = "select mobi.content,mobi.url,mobi.author,mobi.description from mobi join "
			+ "(SELECT user_download.content,count(*) num FROM user_download GROUP BY user_download.content) new_table "
			+ " on mobi.content=new_table.content"
			+ " limit "+Constent.ROW_NUM_LIMIT;
	
	private static final String GET_RANDOM_CONTENT="select * from mobi limit ? ,"+Constent.ROW_NUM_LIMIT;
	
	private static final String RESET_CONTENT_SIMILAR="delete from content_similar where 1=1";
	
	
	public RecommandDAOimpl(JdbcTemplate jdbcTemplate) {
		this.mJdbcTemplate = jdbcTemplate;
	}

	public void recordDowload(String content, String user, Date time) {
		try {
			mJdbcTemplate.update(INSERT_DOWNLOAD_RECORD, content, user, time);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<DownloadRecordBean> getRecords() {
		List<DownloadRecordBean> downloadRecordBeans = mJdbcTemplate.query(
				GET_DOWLOAD_RECORD, new DownloadRecordMapper());
		return downloadRecordBeans;
	}

	public void recordSimlar(List<ContentSimilarBean> contentSimilarBeans) {
      for(ContentSimilarBean temp:contentSimilarBeans){
    	  String contentSource=temp.getContentSource();
    	  String contentDest=temp.getContentDest();
    	  double similar=temp.getSimilar();
    	  try {
			mJdbcTemplate.update(INSERT_SIMILAR_VALUE,contentSource,contentDest,similar);
			mJdbcTemplate.update(INSERT_SIMILAR_VALUE,contentDest,contentSource,similar);
    	  } catch (Exception e) {
    		  e.printStackTrace();
		}
      }
	}

	public List<ContentSimilarBean> getRecommendList(String userName) {
		List<ContentSimilarBean> contentSimilarList=new ArrayList<ContentSimilarBean>();
		try {
			contentSimilarList=mJdbcTemplate.query(GET_RECOMMEND_CONTENT,new ContentRecommandMapper(),userName,userName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return contentSimilarList;
	}

	public List<MobiBean> getHotContent() {
		List<MobiBean> list=new ArrayList<MobiBean>();
		try {
			list=mJdbcTemplate.query(GET_HOT_CONTENT, new MobiBeanMapper());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<MobiBean> getRandomContent() {
		Random random=new Random();
		int num=random.nextInt(Constent.CONTENT_SUM_NUM);
		List<MobiBean> list=new ArrayList<MobiBean>();
		try {
			list=mJdbcTemplate.query(GET_RANDOM_CONTENT, new MobiBeanMapper(),num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public void resetContentSimilarDB() {
		try {
			mJdbcTemplate.update(RESET_CONTENT_SIMILAR);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

}
