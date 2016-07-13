package base.util;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

//分页类
public class Pagination {

	public static final int DEFAULT_TOTAL_PAGE = -1;
	public static final int NUMBERS_PER_PAGE = 10;
	private int totalPages = DEFAULT_TOTAL_PAGE; // 总页数
	private int page; // 当前页码
	private List<?> resultList; // 结果集存放List
    private String content;//搜索的内容
    
	/**
	 * @param sql 执行的sql语句
	 * @param currentPage 当前页码
	 * @param numPerPage 每一页的行数
	 * @param jTemplate  jdbcTemplate
	 * @param content 搜索的关键字
	 */
	public Pagination(String sql, int currentPage, int numPerPage,
			JdbcTemplate jTemplate,String content) {
		this.content=content;
		if (jTemplate == null) {
			throw new IllegalArgumentException(
					"base.util.jTemplate is null,please initial it first. ");
		} else if (sql == null || sql.equals("")) {
			throw new IllegalArgumentException(
					"base.util.sql is empty,please initial it first. ");
		}
         
		if (totalPages == DEFAULT_TOTAL_PAGE) {
			String countSQL = getSQLCount(sql);
			setPage(currentPage);
			Number number=jTemplate.queryForObject(countSQL,Integer.class);
			setTotalPages(numPerPage, number.intValue());
		}
		int startIndex = (currentPage - 1) * numPerPage; // 数据读取起始index
		StringBuffer paginationSQL = new StringBuffer(" ");
		paginationSQL.append(sql);
		paginationSQL.append(" limit " + startIndex + "," + numPerPage);
		setResultList(jTemplate.queryForList(paginationSQL.toString()));
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	// 获得所有的个数
	public String getSQLCount(String sql) {
		String sqlBak = sql.toLowerCase();
		String searchValue = " from ";
		String sqlCount = "select count(*) from "
				+ sql.substring(
						sqlBak.indexOf(searchValue) + searchValue.length(),
						sqlBak.length());
		return sqlCount;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public List<?> getResultList() {
		return resultList;
	}

	public void setResultList(List<?> resultList) {
		this.resultList = resultList;
	}

	// 计算总页数
	public void setTotalPages(int numPerPage, int totalRows) {
		if (totalRows % numPerPage == 0) {
			this.totalPages = totalRows / numPerPage;
		} else {
			this.totalPages = (totalRows / numPerPage) + 1;
		}
	}

}