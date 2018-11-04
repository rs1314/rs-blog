package cn.rs.blog.core.utils;


import java.util.UUID;

/**
 * Created by rs
 */
public class RandomCodeUtil {

    /**
     * 生成6个随机数
     * @return
     */
    public static String randomCode6(){
        int code = (int)((Math.random()*9+1)*100000);
        return String.valueOf(code);
    }

    public static String uuid(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        return uuid;
    }
}
