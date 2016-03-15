package top.latfat.crazecoder.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import top.latfat.crazecoder.entity.Result;
import top.latfat.crazecoder.entity.User;
import top.latfat.crazecoder.entity.wechat.WechatMsg;
import top.latfat.crazecoder.service.APIService;
import top.latfat.crazecoder.service.UserService;
/**
 * 测试用
 * @author sean
 *
 */
@Controller
public class Main {
	
	@Resource
	private UserService service;
	@Resource
	private APIService api;
	
	private Logger logger = Logger.getLogger(Main.class);

	@RequestMapping("/login.do")
	@ResponseBody
	public Result login(User guest, HttpSession session) {
		Result result = service.checkLogin(guest);
		if (result.getStatus() == 1) {
			session.setAttribute("user", result.getData());
		}
		result.setData(null);
		return result;
	}
	
	@RequestMapping("/regist.do")
	@ResponseBody
	public Result save(User guest) {
		Result result = service.checkLogin(guest);
		if (result.getStatus() != 0) {
			if (result.getData() != null) {
				return result.setAll(0, "用户名已被占用！", null);
			}
			return result;
		}
		return service.save(guest);
	}
	
	@RequestMapping(value="/api.do", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
	public void api(WechatMsg msg, PrintWriter out) {
		logger.info("微信接入！！！");
		out.println(api.checkWechat(msg));
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="/api.do", method = { RequestMethod.POST }, produces = "application/xml;charset=UTF-8")
	public void api(HttpServletRequest request, HttpServletResponse response) {
		PrintWriter out = null;
		try {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			out = response.getWriter();
			out.println(api.handleRequest(request));
			logger.info("处理请求成功！！！");
		} catch (IOException e) {
			out.println("");
			logger.info("处理请求失败！！！");
			e.printStackTrace();
		}
		out.flush();
		out.close();
	}
}
