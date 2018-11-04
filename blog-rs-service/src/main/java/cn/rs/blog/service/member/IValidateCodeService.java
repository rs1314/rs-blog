package cn.rs.blog.service.member;


import cn.rs.blog.bean.member.ValidateCode;

/**
 * 验证码Service接口
 * Created by rs
 */
public interface IValidateCodeService {

    boolean save(ValidateCode validateCode);

    /**
     * 验证，30分钟以内有效
     * @param email
     * @param code
     * @param type
     * @return
     */
    ValidateCode valid(String email, String code, int type);

    boolean used(Integer id);
}