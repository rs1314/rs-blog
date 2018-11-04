package cn.rs.blog.service.common;

import java.util.List;

/**
 * Created by rs
 */
public interface IBaseService<T> {

    T get(Integer id);

    int save(T entity);

    int update(T entity);

    int delete(Integer id);

    List allList();
}
