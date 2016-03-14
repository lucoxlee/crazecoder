package top.latfat.crazecoder.entity;  
  
import top.latfat.crazecoder.service.XStreamCDATA;

import com.thoughtworks.xstream.annotations.XStreamAlias;
  
/** 
 *  
 * @author morning 
 * @date 2015年2月16日 下午2:29:32 
 */  
@XStreamAlias("xml")  
public class OutputMessage {  
  
    @XStreamAlias("ToUserName")  
    @XStreamCDATA  
    private String ToUserName;  
  
    @XStreamAlias("FromUserName")  
    @XStreamCDATA  
    private String FromUserName;  
  
    @XStreamAlias("CreateTime")  
    private Long CreateTime;  
  
    @XStreamAlias("MsgType")  
    @XStreamCDATA  
    private String MsgType = "text";
    
    @XStreamAlias("Content")
    @XStreamCDATA
    private String Content;
  
    public String getToUserName() {  
        return ToUserName;  
    }  
  
    public void setToUserName(String toUserName) {  
        ToUserName = toUserName;  
    }  
  
    public String getFromUserName() {  
        return FromUserName;  
    }  
  
    public void setFromUserName(String fromUserName) {  
        FromUserName = fromUserName;  
    }  
  
    public Long getCreateTime() {  
        return CreateTime;  
    }  
  
    public void setCreateTime(Long createTime) {  
        CreateTime = createTime;  
    }  
  
    public String getMsgType() {  
        return MsgType;  
    }  
  
    public void setMsgType(String msgType) {  
        MsgType = msgType;  
    }  

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	@Override
	public String toString() {
		return "ToUserName=" + ToUserName + "\nFromUserName=" + FromUserName
				+ "\nCreateTime=" + CreateTime + "\nMsgType=" + MsgType
				+ "\nContent=" + Content;
	}
  
}  