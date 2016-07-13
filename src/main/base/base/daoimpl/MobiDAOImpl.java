package base.daoimpl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.ui.Model;

import base.dao.IMobiDAO;
import base.util.Constent;
import base.util.Pagination;
import base.util.StringUtil;

public class MobiDAOImpl implements IMobiDAO {

	private JdbcTemplate mJdbcTemplate;
	private String SQL_SEARCH_STRING = "select * from mobi where content like ";

	public MobiDAOImpl(JdbcTemplate jdbcTemplate) {
		this.mJdbcTemplate = jdbcTemplate;
	}

	public String searchMobi(String content,int currentPage,Model model) {
		if(StringUtil.isEmpty(content)){
			return Constent.ERROR_PAGE;
		}
		// 实现一些操作
		String newContent = "'%" + content + "%'";
		String searchSQL=SQL_SEARCH_STRING+newContent;
		Pagination pagination=new Pagination(searchSQL,currentPage, Pagination.NUMBERS_PER_PAGE, mJdbcTemplate,content);
		model.addAttribute("pagination",pagination);
		return "searchResult";
	}
}
