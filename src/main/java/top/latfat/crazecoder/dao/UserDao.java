package top.latfat.crazecoder.dao;

import top.latfat.crazecoder.entity.User;
/**
 * 
 * @author sean
 *
 *	特有方法放在此处
 */
public interface UserDao extends BaseDao<User> {

	User findByName(String name);

}
