package cn.rs.blog.service.group;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.group.GroupFans;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IGroupFansService {

    boolean save(Member loginMember, Integer groupId);

    boolean delete(Member loginMember, Integer groupId);

    ResultModel listByPage(Page page, Integer groupId);

    GroupFans findByMemberAndGroup(@Param("groupId") Integer groupId, @Param("memberId") Integer memberId);

    /**
     * 获取用户关注的群组列表
     * @param page
     * @param memberId
     * @return
     */
    ResultModel listByMember(Page page, Integer memberId);
}
