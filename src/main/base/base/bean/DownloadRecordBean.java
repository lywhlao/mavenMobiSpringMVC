package base.bean;

import java.sql.Date;

/**
 * 下载记录
 * @author Administrator
 *
 */
public class DownloadRecordBean {

	String content;
	String userName;
	Date time;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "DownloadRecordBean [content=" + content + ", userName="
				+ userName + ", time=" + time + "]";
	}
	
	
}
