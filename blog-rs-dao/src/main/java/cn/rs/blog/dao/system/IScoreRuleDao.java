package cn.rs.blog.dao.system;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.system.ScoreRule;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * Created by rs
 */
public interface IScoreRuleDao extends IBaseDao<ScoreRule> {

    int enabled(@Param("id") int id);
}
