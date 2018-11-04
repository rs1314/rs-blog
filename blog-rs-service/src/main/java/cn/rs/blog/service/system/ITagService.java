package cn.rs.blog.service.system;

import cn.rs.blog.bean.system.Tag;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;

public interface ITagService {
    boolean save(Tag tag);

    ResultModel listByPage(Page page, int funcType);

    boolean update(Tag tag);

    boolean delete(Integer id);

    Tag findById(Integer id);

}
