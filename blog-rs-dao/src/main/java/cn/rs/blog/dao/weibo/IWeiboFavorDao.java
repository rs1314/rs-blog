package cn.rs.blog.dao.weibo;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.weibo.WeiboFavor;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * 微博点赞DAO接口
 * Created by rs
 */
public interface IWeiboFavorDao extends IBaseDao<WeiboFavor> {

    WeiboFavor find(@Param("weiboId") Integer weiboId, @Param("memberId") Integer memberId);

    Integer save(@Param("weiboId") Integer weiboId, @Param("memberId") Integer memberId);

    Integer delete(@Param("weiboId") Integer weiboId, @Param("memberId") Integer memberId);
}