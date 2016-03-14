package top.latfat.crazecoder.service.impl;

import java.io.Serializable;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import top.latfat.crazecoder.dao.BaseDao;
import top.latfat.crazecoder.entity.Result;
import top.latfat.crazecoder.service.BaseService;
  
/** 
 * BaseServiceImpl 
 *  
 * @author sean 
 */  
@Transactional
public class BaseServiceImpl<T> implements BaseService<T> {  
      
	@Resource  
    private BaseDao<T> dao;
	
	protected Result result = new Result(0, "Faild", null);
  
    public Result save(T entity) {  
        return result.setAll(1, "Done", dao.save(entity));  
    }  
  
    public Result update(T entity) {  
        dao.update(entity);
        return result.setAll(1, "Done", null);
    }  
  
    public Result delete(Serializable id) {  
        dao.delete(id);  
        return result.setAll(1, "Done", null);
    }  
  
    public Result getById(Serializable id) {  
        return result.setAll(1, "Done", dao.findById(id));  
    }  
  
    public Result getByHQL(String hql, Object... params) {  
        return result.setAll(1, "Done", dao.findByHQL(hql, params));  
    }  
}  