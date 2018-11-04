package cn.rs.blog.service.group.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.group.GroupTopicType;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.dao.group.IGroupTopicTypeDao;
import cn.rs.blog.service.group.IGroupTopicTypeService;

/**
 * @author: rs
 */
@Service
public class GroupTopicTypeServiceImpl implements IGroupTopicTypeService {
    @Autowired
    private IGroupTopicTypeDao groupTopicTypeDao;

    @Override
    public GroupTopicType findById(int id) {
        return groupTopicTypeDao.findById(id);
    }

    @Override
    public List<GroupTopicType> list(int groupId) {
        return groupTopicTypeDao.list(groupId);
    }

    @Override
    public boolean delete(Member member, int id) {
        int result = groupTopicTypeDao.delete(id);
        return result == 1;
    }

    @Override
    public boolean save(Member member, GroupTopicType groupTopicType) {
        int result = groupTopicTypeDao.save(groupTopicType);
        return result == 1;
    }

    @Override
    public boolean update(Member member, GroupTopicType groupTopicType) {
        int result = groupTopicTypeDao.update(groupTopicType);
        return result == 1;
    }
}
