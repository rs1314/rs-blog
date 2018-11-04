package cn.rs.blog.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.common.Link;
import cn.rs.blog.core.model.Page;

/**
 * Created by rs
 */
public interface ILinkDao extends IBaseDao<Link>{

    /**
     * 分页查询
     * @param page
     * @return
     */
    List<Link> listByPage(@Param("page") Page page);

    List<Link> recommentList();

    int enable(@Param("id") Integer id);
}
