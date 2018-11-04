package cn.rs.blog.dao.member;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.member.MemberToken;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * Created by rs
 */
public interface IMemberTokenDao extends IBaseDao<MemberToken> {

    MemberToken getByToken(@Param("token") String token);

    Integer save(@Param("memberId") Integer memberId, @Param("token") String token, @Param("expireTime") Date expireTime);

}