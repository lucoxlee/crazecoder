package top.latfat.crazecoder.entity.tuling;

public class Menu extends UrlList {

	private String name;
	private String info;

	public String getArticle() {
		return name;
	}
	public void setArticle(String article) {
		this.name = article;
	}
	public String getSource() {
		return info;
	}
	public void setSource(String source) {
		this.info = source;
	}
}
