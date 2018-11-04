package cn.rs.blog.service.group;

import java.util.List;

import cn.rs.blog.bean.group.Group;
import cn.rs.blog.bean.member.Member;


/**
 * Created by rs
 */
public interface IGroupService {

    Group findById(int id);

    boolean save(Member loginMember, Group group);

    boolean update(Member loginMember, Group group);

    boolean delete(Member loginMember, int id);

    List<Group> list(int status, String key);

    boolean follow(Member loginMember, Integer groupId, int type);

    boolean changeStatus(int id);

    List<Group> listByCustom(int status, int num, String sort);
}
