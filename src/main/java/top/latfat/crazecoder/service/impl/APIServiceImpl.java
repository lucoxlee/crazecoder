package top.latfat.crazecoder.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import top.latfat.crazecoder.entity.WechatMsg;
import top.latfat.crazecoder.service.APIService;

@Service
public class APIServiceImpl implements APIService {

	@Resource
	private WechatMsg msg;
	
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
	public String handleRequest(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
