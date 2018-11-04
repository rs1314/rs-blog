package cn.rs.blog.service.member;


import cn.rs.blog.bean.member.MemberToken;

/**
 * Created by rs
 */
public interface IMemberTokenService {

    MemberToken getByToken(String token);

    void save(Integer memberId,String token);

}