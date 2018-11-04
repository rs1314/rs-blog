package cn.rs.blog.dao.weibo;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.weibo.WeiboComment;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * 微博评论DAO接口
 * Created by rs
 */
public interface IWeiboCommentDao extends IBaseDao<WeiboComment> {

    List<WeiboComment> listByWeibo(@Param("page") Page page, @Param("weiboId") Integer weiboId);

    /**
     * 根据微博ID删除评论
     * @param weiboId
     * @return
     */
    int deleteByWeibo(@Param("weiboId") Integer weiboId);
}