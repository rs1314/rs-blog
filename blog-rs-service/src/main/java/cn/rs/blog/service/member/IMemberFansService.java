package cn.rs.blog.service.member;

import cn.rs.blog.bean.member.MemberFans;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IMemberFansService {

    boolean save(Integer whoFollowId, Integer followWhoId);

    boolean delete(Integer whoFollowId, Integer followWhoId);

    ResultModel followsList(Page page, Integer whoFollowId);

    ResultModel fansList(Page page, Integer followWhoId);

    MemberFans find(Integer whoFollowId, Integer followWhoId);
}
