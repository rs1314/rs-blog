package cn.rs.blog.dao.common;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.common.Ads;
import cn.rs.blog.core.model.Page;

/**
 * Created by rs
 */
public interface IAdsDao extends IBaseDao<Ads>{

    /**
     * 分页查询广告信息
     * @param page
     * @return
     */
    List<Ads> listByPage(@Param("page") Page page);

    int enable(@Param("id") Integer id);
}
