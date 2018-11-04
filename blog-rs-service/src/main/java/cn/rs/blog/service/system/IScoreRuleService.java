package cn.rs.blog.service.system;

import java.util.List;

import cn.rs.blog.bean.system.ScoreRule;

/**
 * Created by rs
 */
public interface IScoreRuleService {

    List<ScoreRule> list();

    ScoreRule findById(Integer id);

    boolean update(ScoreRule scoreRule);

    boolean enabled(int id);

}
