package top.latfat.crazecoder.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import top.latfat.crazecoder.dao.UserDao;
import top.latfat.crazecoder.entity.Result;
import top.latfat.crazecoder.entity.User;
import top.latfat.crazecoder.service.UserService;
/**
 * 
 * @author sean
 *
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

	@Resource
	private UserDao dao;
	
	@Override
	public Result checkLogin(User guest) {
		if (StringUtils.isBlank(guest.getName()) || StringUtils.isBlank(guest.getPwd())) {
			return result.setAll(2, "用户名或密码不能为空！", null);
		}else if (guest.getName().matches("/[a-zA-Z0-9_]{3,16}/")) {
			return result.setAll(3, "用户名不合法！只可以使用数字、字母、下划线！", null);
		} else if (guest.getPwd().length()<6 || guest.getPwd().length() > 30) {
			return result.setAll(4, "密码最少6个字符，最多30个字符!", null);
		}
		User user = dao.findByName(guest.getName());
		if (user != null && user.getPwd().equals(guest.getPwd())) {
			return result.setAll(1, "登录成功！", user);
		}
		return result.setAll(0, "用户名或密码错误！", user);
	}

}
