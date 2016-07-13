package base.bean;

public class MobiBean {

	String content;//搜索的内容(书名)
	String url;//搜索到的地址
	String author;//作者
	String description;//描述
	
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Override
	public String toString() {
		return "MobiBean [content=" + content + ", url=" + url + ", author="
				+ author + ", description=" + description + "]";
	}
	
	
}
