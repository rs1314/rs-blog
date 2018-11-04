package cn.rs.blog.service.system.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.system.ScoreRule;
import cn.rs.blog.dao.system.IScoreRuleDao;
import cn.rs.blog.service.system.IScoreRuleService;

/**
 * Created by rs
 */
@Service
public class ScoreRuleServiceImpl implements IScoreRuleService {
    @Autowired
    private IScoreRuleDao scoreRuleDao;


    @Override
    public List<ScoreRule> list() {
        return scoreRuleDao.allList();
    }

    @Override
    public ScoreRule findById(Integer id) {
        return scoreRuleDao.findById(id);
    }

    @Override
    public boolean update(ScoreRule scoreRule) {
        return scoreRuleDao.update(scoreRule) == 1;
    }

    @Override
    public boolean enabled(int id) {
        return scoreRuleDao.enabled(id) == 1;
    }

}
