package cn.rs.blog.service.common;

import cn.rs.blog.bean.common.Ads;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;

/**
 * Created by rs
 */
public interface IAdsService {
    /**
     * 保存广告信息
     * @param ads
     * @return
     */
    boolean save(Ads ads);
    /**
     * 分页查询广告信息
     * @param page
     * @return
     */
    ResultModel listByPage(Page page);

    boolean update(Ads ads);

    boolean delete(Integer id);

    Ads findById(Integer id);

    boolean enable(Integer id);
}
