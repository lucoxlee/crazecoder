package top.latfat.crazecoder.entity.tuling;

public class News extends UrlList {

	private String article;
	private String source;

	public String getArticle() {
		return article;
	}
	public void setArticle(String article) {
		this.article = article;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	@Override
	public String toString() {
		return "article=" + article + "\nsource=" + source + "\nicon=" + icon
				+ "\ndetailurl=" + detailurl + "\n";
	}
}
