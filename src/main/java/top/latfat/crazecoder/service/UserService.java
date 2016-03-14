package top.latfat.crazecoder.service;

import top.latfat.crazecoder.entity.Result;
import top.latfat.crazecoder.entity.User;
/**
 * 特有服务放在此
 * @author xiaochen@inshn
 *
 */
public interface UserService extends BaseService<User> {

	Result checkLogin(User guest);

}
