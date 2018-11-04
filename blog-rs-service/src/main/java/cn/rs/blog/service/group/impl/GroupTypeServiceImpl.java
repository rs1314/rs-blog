package cn.rs.blog.service.group.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.group.GroupType;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.dao.group.IGroupTypeDao;
import cn.rs.blog.service.group.IGroupTypeService;

/**
 * @author: rs
 */
@Service
public class GroupTypeServiceImpl implements IGroupTypeService {
    @Autowired
    private IGroupTypeDao groupTypeDao;

    @Override
    public GroupType findById(int id) {
        return groupTypeDao.findById(id);
    }

    @Override
    public List<GroupType> list() {
        return groupTypeDao.allList();
    }

    @Override
    public boolean delete(int id) {
        if (id == 1){
            throw new OpeErrorException("默认分类无法删除");
        }
        int result = groupTypeDao.delete(id);
        if (result == 0){
            throw new OpeErrorException();
        }
        return true;
    }

    @Override
    public boolean save(GroupType groupType) {
        int result = groupTypeDao.save(groupType);
        if (result == 0){
            throw new OpeErrorException();
        }
        return true;
    }

    @Override
    public boolean update(GroupType groupType) {
        int result = groupTypeDao.update(groupType);
        if (result == 0){
            throw new OpeErrorException();
        }
        return true;
    }
}
