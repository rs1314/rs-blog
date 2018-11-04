package cn.rs.blog.service.weibo;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.weibo.WeiboComment;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IWeiboCommentService {

    WeiboComment findById(int id);

    boolean save(Member loginMember, String content, Integer weiboId, Integer weiboCommentId);

    boolean delete(Member loginMember, int id);

    ResultModel listByWeibo(Page page, int weiboId);

    void deleteByWeibo(Integer weiboId);
}
