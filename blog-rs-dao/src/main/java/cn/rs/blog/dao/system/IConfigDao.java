package cn.rs.blog.dao.system;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.system.Config;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * 系统配置信息DAO接口
 * Created by rs
 */

public interface IConfigDao extends IBaseDao<Config> {

    boolean update(@Param("key") String key,@Param("value") String value);

    String getValue(@Param("key") String key);
}