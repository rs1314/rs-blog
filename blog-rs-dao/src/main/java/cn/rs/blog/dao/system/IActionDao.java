package cn.rs.blog.dao.system;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.system.Action;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * Created by rs
 */
public interface IActionDao extends IBaseDao<Action> {
    int isenable(@Param("id") Integer id);
}
