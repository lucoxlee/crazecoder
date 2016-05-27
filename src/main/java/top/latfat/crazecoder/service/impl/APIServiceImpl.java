package top.latfat.crazecoder.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.thoughtworks.xstream.XStream;

import top.latfat.crazecoder.entity.tuling.Menu;
import top.latfat.crazecoder.entity.tuling.News;
import top.latfat.crazecoder.entity.tuling.TulingSaid;
import top.latfat.crazecoder.entity.tuling.TulingSaidList;
import top.latfat.crazecoder.entity.tuling.TulingSaidURL;
import top.latfat.crazecoder.entity.wechat.InputMessage;
import top.latfat.crazecoder.entity.wechat.Item;
import top.latfat.crazecoder.entity.wechat.OutputMessage;
import top.latfat.crazecoder.entity.wechat.WechatMsg;
import top.latfat.crazecoder.httpclint.TalkingHttpClint;
import top.latfat.crazecoder.service.APIService;
/**
 * 
 * 微信接口服务。
 * 1. 验证微信服务器真伪
 * 2. 处理微信服务发来的请求，并合理响应。
 * 3. 未完待续。
 * 
 * 存在问题：
 * 1. 与图灵机器人服务依赖较深，下一步要初步解耦。
 * 2. 消息分发灵活度欠佳，继续优化。
 * @author sean
 *
 */
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
		/*
		 * 因为该服务并未有任何机密信息。所以校验失败并不怀疑微信服务器的
		 * 真实性。
		 * 做掉安全验证为方便以后使用，此时验证失败应该停止对该服务器的服务
		 */
		return this.msg.getEchostr();
	}
	
	/**
	 * 将请求携带信息(xml)解析出来，包装成InputMessage统一处理。
	 * 交给响应方法分发处理。
	 * @param request
	 * @return 响应信息序列化为xml
	 */
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
	
	/**
	 * 分发处理微信请求。
	 * @param in 请求信息包装
	 * @return 序列化xml串
	 */
	private String responseInput(InputMessage in) {
		XStream xs = SerializeXmlUtil.createXstream();
		xs.processAnnotations(OutputMessage.class);
		xs.alias("xml", OutputMessage.class);
		OutputMessage out = new OutputMessage();
		out.setToUserName(in.getFromUserName());
		out.setFromUserName(in.getToUserName());
		out.setCreateTime(System.currentTimeMillis()/1000);
		out.setMsgType("text");
		if ("text".equals(in.getMsgType())) {
			TulingSaid said = clint.talkingTuling(in.getContent(), in.getFromUserName());
			out = execute(said, out);
		} else if ("voice".equals(in.getMsgType())) {
			TulingSaid said = clint.talkingTuling(in.getRecognition(), in.getFromUserName());
			out = execute(said, out);
		} else {
			return "";
		}
		return xs.toXML(out);
	}
	
	private OutputMessage execute(TulingSaid said, OutputMessage out) {
		switch (said.getCode()) {
		case 100000: //图灵机器人回复文本
			out.setContent(said.getText());
			break;
		case 200000: //回复带URL文本
			TulingSaidURL saidURL = (TulingSaidURL) said;
			out.setContent(saidURL.getText() + "<a href=\"" + saidURL.getUrl() + "\">【点击这里】</a>");
			break;
		case 302000: //回复图文消息，新闻类
			@SuppressWarnings("unchecked")
			TulingSaidList<News> saidList = (TulingSaidList<News>) said;
			out.setMsgType("news");
			
			List<News> list = saidList.getList();
			List<Item> news = new ArrayList<Item>();
			out.setArticleCount(list.size()>10?10:list.size());
			for (News n : list) {
				Item item = new Item();
				item.setTitle(n.getArticle());
				item.setDescription(n.getSource());
				item.setPicUrl(n.getIcon());
				item.setUrl(n.getDetailurl());
				news.add(item);
			}
			out.setArticles(news);
			break;
		case 308000: //回复菜谱消息
			@SuppressWarnings("unchecked")
			TulingSaidList<Menu> saidMenu = (TulingSaidList<Menu>) said;
			out.setMsgType("news");
			
			List<Menu> i = saidMenu.getList();
			List<Item> menu = new ArrayList<Item>();
			out.setArticleCount(i.size()>10?10:i.size());
			int index = 0;
			for (Menu n : i) {
				Item item = new Item();
				item.setTitle(n.getName());
				item.setDescription(n.getInfo());
				item.setPicUrl(n.getIcon());
				item.setUrl(n.getDetailurl());
				menu.add(item);
				index++;
				if (index >= 10) {
					break;
				}
			}
			out.setArticles(menu);
			break;
		}
		
		return out;
	}

}
