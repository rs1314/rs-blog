package cn.rs.blog.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 数据库操作基类接口
 * Created by rs
 * @param <T>
 */
public interface IBaseDao<T> {

    T findById(@Param("id") Integer id);

    int save(T entity);

    int update(T entity);

    int delete(@Param("id") Integer id);

    List<T> allList();
}
