package cn.rs.blog.service.weibo;


import cn.rs.blog.bean.weibo.WeiboFavor;

/**
 * 微博点赞Service接口
 * Created by rs
 */
public interface IWeiboFavorService {

    WeiboFavor find(Integer weiboId, Integer memberId);

    void save(Integer weiboId, Integer memberId);

    void delete(Integer weiboId, Integer memberId);
}