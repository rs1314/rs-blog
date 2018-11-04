package cn.rs.blog.service.common;


import java.util.List;

import cn.rs.blog.dao.common.IBaseDao;

/**
 * Service基类实现类
 * Created by rs
 */
public abstract class BaseServiceImpl<T> implements IBaseService<T> {
    protected abstract IBaseDao<T> getDao();

    public T get(Integer id) {
        return getDao().findById(id);
    }

    public int save(T entity) {
        return getDao().save(entity);
    }

    public int update(T entity) {
        return getDao().update(entity);
    }

    public int delete(Integer id) {
        return getDao().delete(id);
    }

    public List allList() {
        return getDao().allList();
    }

}
