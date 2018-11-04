package cn.rs.blog.core.utils;

import java.util.UUID;

/**
 * Created by rs
 */
public class TokenUtil {

    public static String getToken(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
