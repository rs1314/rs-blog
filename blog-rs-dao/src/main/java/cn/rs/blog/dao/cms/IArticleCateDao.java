package cn.rs.blog.dao.cms;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.cms.ArticleCate;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * 文章栏目DAO接口
 * Created by rs
 */
public interface IArticleCateDao extends IBaseDao<ArticleCate> {

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
    List<ArticleCate> findListByFid(@Param("fid") int fid);

}