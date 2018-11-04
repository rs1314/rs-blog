package cn.rs.blog.service.system;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import cn.rs.blog.bean.system.Config;

/**
 * Created by rs
 */
public interface IConfigService {
    List<Config> allList();

    Map<String,String> getConfigToMap();

    String getValue(String key);

    boolean update(Map<String,String> params, HttpServletRequest request);
}
