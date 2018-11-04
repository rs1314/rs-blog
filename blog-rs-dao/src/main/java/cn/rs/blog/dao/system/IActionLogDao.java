package cn.rs.blog.dao.system;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.system.ActionLog;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * Created by rs
 */
public interface IActionLogDao extends IBaseDao<ActionLog> {

    List<ActionLog> listByPage(@Param("page") Page page, @Param("memberId") Integer memberId);

    List<ActionLog> memberActionLog(@Param("page") Page page, @Param("memberId") Integer memberId);
}
