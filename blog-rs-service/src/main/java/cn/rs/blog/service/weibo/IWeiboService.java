package cn.rs.blog.service.weibo;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.weibo.Weibo;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by rs.
 */
public interface IWeiboService {

    Weibo findById(int id, int memberId);

    boolean save(HttpServletRequest request, Member loginMember, String content, String pictures);
    boolean save(HttpServletRequest request, Member loginMember, String content, String pictures,String md5);

    ResultModel<Weibo> listByPage(Page page, int memberId, int loginMemberId, String key);

    boolean delete(HttpServletRequest request, Member loginMember, int id);

    boolean userDelete(HttpServletRequest request, Member loginMember, int id);

    List<Weibo> hotList(int loginMemberId);

    ResultModel favor(Member loginMember, int weiboId);

    List<Weibo> listByCustom(int loginMemberId, String sort,int num,int day);
}
