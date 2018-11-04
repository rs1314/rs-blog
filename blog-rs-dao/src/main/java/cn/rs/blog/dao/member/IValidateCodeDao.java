package cn.rs.blog.dao.member;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.member.ValidateCode;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * 验证码DAO接口
 * Created by rs
 */
public interface IValidateCodeDao extends IBaseDao<ValidateCode> {

    /**
     * 验证，30分钟以内有效
     * @param email
     * @param code
     * @param type
     * @return
     */
    ValidateCode valid(@Param("email") String email, @Param("code") String code, @Param("type") int type);

    int used(@Param("id") Integer id);
}