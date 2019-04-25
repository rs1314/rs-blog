package cn.rs.blog.commoms.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {
    private static final Logger logger = LogManager.getLogger(ResultUtils.class.getName());
    public  static<T> Map<String,T> getMapResult(T t){
        Map<String,T> map =new HashMap<String,T>();
        map.put("data",t);
        return map;
    }

    public  static<T> Map<String,T> getMapResult(String[]args,@SuppressWarnings("unchecked") T...datas){

        Map<String,T> map =new HashMap<String,T>();
        try {
            for (int i = 0; i < args.length; i++) {
                map.put(args[i], datas[i]);
            }
        }catch(Exception e){
            logger.info("数据不匹配，请把args,datas保持一至的长度....");
        }
        return map;
    }

    /**
     * 默认返回
     * @param datas
     * @param <T>
     * @return
     */
    public static <T> Object getDefaultResult(@SuppressWarnings("unchecked") T datas){
        String[] args ={"code","result"};
        return getMapResult(args,"200",datas);
    }
}
