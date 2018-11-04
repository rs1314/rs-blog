package cn.rs.blog.service.common;

import cn.rs.blog.bean.common.Link;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;

/**
 * Created by rs
 */
public interface ILinkService {

    boolean save(Link link);
   
    ResultModel listByPage(Page page);

    ResultModel allList();

    ResultModel recommentList();

    boolean update(Link link);

    boolean delete(Integer id);

    Link findById(Integer id);

    boolean enable(Integer id);
}
