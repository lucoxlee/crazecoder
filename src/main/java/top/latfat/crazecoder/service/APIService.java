package top.latfat.crazecoder.service;

import javax.servlet.http.HttpServletRequest;

import top.latfat.crazecoder.entity.WechatMsg;

public interface APIService {

	String checkWechat(WechatMsg msg);

	String handleRequest(HttpServletRequest request);
}
