package top.latfat.crazecoder.service.impl;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import top.latfat.crazecoder.entity.InputMessage;
import top.latfat.crazecoder.entity.OutputMessage;
import top.latfat.crazecoder.entity.WechatMsg;
import top.latfat.crazecoder.httpclint.TalkingHttpClint;
import top.latfat.crazecoder.service.APIService;

@Service
public class APIServiceImpl implements APIService {

	@Resource
	private WechatMsg msg;
	@Resource
	private TalkingHttpClint clint;
	
	private Logger logger = Logger.getLogger(getClass());
	@Override
	public String checkWechat(WechatMsg msg) {
		this.msg = this.msg.setAll(msg);
		if (this.msg.accessing()) {
			logger.info("access WeChat server！");
			return this.msg.getEchostr();
		}
		logger.warn("not sure it's wechat server");
		return this.msg.getEchostr();
	}
	@Override
	public String handleRequest(HttpServletRequest request) throws IOException {
		ServletInputStream in = null;
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(InputMessage.class);
		xs.alias("xml", InputMessage.class);
		in = request.getInputStream();
		StringBuffer sb = new StringBuffer();
		byte[] b = new byte[4096];
		for(int n; (n = in.read(b)) != -1;) {
			sb.append(new String(b, 0, n, "UTF-8"));
		}
		InputMessage msg = (InputMessage) xs.fromXML(sb.toString());
		return responseInput(msg);
	}
	private String responseInput(InputMessage in) {
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(OutputMessage.class);
		xs.alias("xml", OutputMessage.class);
		OutputMessage out = new OutputMessage();
		if ("text".equals(in.getMsgType())) {
			out.setToUserName(in.getFromUserName());
			out.setFromUserName(in.getToUserName());
			out.setCreateTime(System.currentTimeMillis()/1000);
			out.setMsgType("text");
			out.setContent(clint.talkingTuling(in.getContent(), in.getFromUserName()));
		}
		return xs.toXML(out);
	}

}
