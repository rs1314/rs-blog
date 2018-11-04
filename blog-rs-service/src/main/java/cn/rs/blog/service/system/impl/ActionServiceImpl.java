package cn.rs.blog.service.system.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.system.Action;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.dao.system.IActionDao;
import cn.rs.blog.service.system.IActionService;

/**
 * Created by rs
 */
@Service("actionService")
public class ActionServiceImpl implements IActionService {
    @Autowired
    private IActionDao actionDao;

    @Override
    public List<Action> list() {
        return actionDao.allList();
    }

    @Override
    public Action findById(Integer id) {
        return actionDao.findById(id);
    }

    @Override
    public boolean update(Action action) {
        if(actionDao.update(action) == 0){
            throw new OpeErrorException();
        }
        return true;
    }

    @Override
    public boolean isenable(Integer id) {
        if(actionDao.isenable(id) == 0){
            throw new OpeErrorException();
        }
        return true;
    }

    /**
     * 状态是否能使用
     * @param id
     * @return true可以使用，false不能使用
     */
    @Override
    public boolean canuse(Integer id) {
        Action action = this.findById(id);
        if(action == null){
            return false;
        }
        if(action.getStatus() == 1){
            return false;
        }
        return true;
    }
}
