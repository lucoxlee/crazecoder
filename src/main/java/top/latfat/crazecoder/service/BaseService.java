package top.latfat.crazecoder.service;

import top.latfat.crazecoder.entity.Result;
  
/** 
 * BaseService 
 *  
 *  通用服务放在此
 * @author sean 
 */  
public interface BaseService<T> {  
  
    public Result save(T entity);  
  
    public Result update(T entity);  
  
    public Result delete(java.io.Serializable id);  
  
    public Result getById(java.io.Serializable id);  
  
    public Result getByHQL(String hql, Object... params);  
}