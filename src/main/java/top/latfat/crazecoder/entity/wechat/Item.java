package top.latfat.crazecoder.entity.wechat;

import top.latfat.crazecoder.service.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("item")
public class Item {

	/** 图文消息标题 */
	@XStreamAlias("Title")
    @XStreamCDATA
	private String Title;
	
	/** 图文消息描述 */
	@XStreamAlias("Description")
	@XStreamCDATA
	private String Description;
	
	/** 图文消息图片链接 */
	@XStreamAlias("PicUrl")
	@XStreamCDATA
	private String PicUrl;
	
	/** 图文消息跳转*/
	@XStreamAlias("Url")
	@XStreamCDATA
	private String Url;

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
