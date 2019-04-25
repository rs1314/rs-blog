package cn.rs.blog.web.utils;

import cn.rs.blog.bean.telephone.AppVO;

public class AppThreadLocal {
    public static ThreadLocal<AppVO> appvos = new ThreadLocal<AppVO>();

    public static void setAppVo(AppVO appVo){
        appvos.set(appVo);
    }

    public static AppVO getAppVo(){
        return  appvos.get();
    }

    public static void removeAppvo(){
        appvos.remove();
    }
}
