package cn.rs.blog.commoms.utils;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.exception.NotLoginException;
import cn.rs.blog.core.exception.ParamException;
import cn.rs.blog.core.utils.StringUtils;

/**
 * @author rs
 */
public class ValidUtill {

    public static void checkLogin(Member member){
        if (null == member){
            throw new NotLoginException();
        }
    }

    public static void checkParam(Boolean... boos){
        for (boolean boo :boos){
            if (!boo){
                throw new ParamException();
            }
        }
    }

    public static void checkIsNull(Object obj){
        if (null == obj){
            throw new ParamException();
        }
    }

    public static void checkIsNull(Object obj, Messages messages){
        if (null == obj){
            throw new ParamException(messages);
        }
    }
    public static void checkIsBlank(String val, Messages messages){
        if (StringUtils.isBlank(val)){
            throw new ParamException(messages);
        }
    }
}
