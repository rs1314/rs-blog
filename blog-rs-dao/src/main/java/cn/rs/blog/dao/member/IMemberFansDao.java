package cn.rs.blog.dao.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.member.MemberFans;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * Created by rs
 */
public interface IMemberFansDao extends IBaseDao<MemberFans> {

    List<MemberFans> followsList(@Param("page") Page page, @Param("whoFollowId") Integer whoFollowId);

    List<MemberFans> fansList(@Param("page") Page page, @Param("followWhoId") Integer followWhoId);

    MemberFans find(@Param("whoFollowId") Integer whoFollowId, @Param("followWhoId") Integer followWhoId);

    Integer save(@Param("whoFollowId") Integer whoFollowId, @Param("followWhoId") Integer followWhoId);

    Integer delete(@Param("whoFollowId") Integer whoFollowId, @Param("followWhoId") Integer followWhoId);
}