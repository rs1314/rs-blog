package cn.rs.blog.service.group;

import java.util.List;

import cn.rs.blog.bean.group.GroupType;

/**
 * @author: rs
 */
public interface IGroupTypeService {

    GroupType findById(int id);

    List<GroupType> list();

    boolean delete(int id);

    boolean save(GroupType groupType);

    boolean update(GroupType groupType);
}
