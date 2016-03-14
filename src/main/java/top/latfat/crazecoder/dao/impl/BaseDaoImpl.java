package top.latfat.crazecoder.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import top.latfat.crazecoder.dao.BaseDao;
  
/**
 * BaseDaoImpl 
 * 
 * @author sean
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
public class BaseDaoImpl<T> implements BaseDao<T> {  
  
    private Class<T> clazz;  
  
    public BaseDaoImpl() {  
        ParameterizedType type = (ParameterizedType) this.getClass().getGenericSuperclass();  
        clazz = (Class<T>) type.getActualTypeArguments()[0];  
    }  
  
    @Resource  
    private SessionFactory sessionFactory;  
  
    protected Session getSession() {  
        return this.sessionFactory.getCurrentSession();  
    }  
  
    public Serializable save(T entity) {  
        return this.getSession().save(entity);  
    }  
  
    public void update(T entity) {  
        this.getSession().update(entity);  
    }  
  
    public void delete(Serializable id) {  
        this.getSession().delete(this.findById(id));  
    }  
  
    public T findById(Serializable id) {  
        return (T) this.getSession().get(this.clazz, id);  
    }  
  
    public List<T> findByHQL(String hql, Object... params) {  
        Query query = this.getSession().createQuery(hql);
        for (int i = 0; params != null && i < params.length; i++) {  
            query.setParameter(i, params[i]);
        }  
        return query.list();  
    }  
}  