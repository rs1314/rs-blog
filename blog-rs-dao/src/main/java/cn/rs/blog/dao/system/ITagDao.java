package cn.rs.blog.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.system.Tag;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

public interface ITagDao extends IBaseDao<Tag> {

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    List<Tag> listByPage(@Param("page") Page page, @Param("funcType") int funcType);

}
