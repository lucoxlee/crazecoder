package top.latfat.crazecoder.dao;

import java.io.Serializable;
import java.util.List;

/**
 * BaseDAO
 * 
 * 公用方法方在此处
 * 
 * @author sean
 */
public interface BaseDao<T> {

	public Serializable save(T entity);

	public void update(T entity);

	public void delete(Serializable id);

	public T findById(Serializable id);

	public List<T> findByHQL(String hql, Object... params);

}