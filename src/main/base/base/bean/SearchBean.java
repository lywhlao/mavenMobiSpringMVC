package base.bean;

public class SearchBean {

	String content;//搜索的内容
	String type;//搜索的类型 "mobi" "torrent"
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

}
