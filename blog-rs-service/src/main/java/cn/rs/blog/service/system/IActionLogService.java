package cn.rs.blog.service.system;

import cn.rs.blog.bean.system.ActionLog;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;

/**
 * Created by rs
 */
public interface IActionLogService {

    ResultModel<ActionLog> listByPage(Page page, Integer memberId);

    ResultModel<ActionLog> memberActionLog(Page page, Integer memberId);

    ActionLog findById(Integer id);

    void save(String actionIp,Integer memberId, Integer actionId);

    void save(String actionIp,Integer memberId, Integer actionId,String remark);

    void save(String actionIp,Integer memberId, Integer actionId,String remark, Integer type, Integer foreignId);

}
