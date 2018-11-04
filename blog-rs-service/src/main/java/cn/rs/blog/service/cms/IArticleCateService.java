package cn.rs.blog.service.cms;


import java.util.List;

import cn.rs.blog.bean.cms.ArticleCate;


/**
 * Created rs
 */
public interface IArticleCateService {

    ArticleCate findById(int id);

    boolean save(ArticleCate articleCate);

    boolean update(ArticleCate articleCate);

    boolean delete(int id);

    /**
     * 获取栏目
     * @return
     */
    List<ArticleCate> list();

    /**
     * 通过父类ID获取子类列表
     * @param fid
     * @return
     */
    List<ArticleCate> findListByFid(int fid);
}
