package cn.rs.blog.service.group;

import java.util.List;

import cn.rs.blog.bean.group.GroupTopicType;
import cn.rs.blog.bean.member.Member;

/**
 * @author: rs
 */
public interface IGroupTopicTypeService {

    GroupTopicType findById(int id);

    List<GroupTopicType> list(int groupId);

    boolean delete(Member member, int id);

    boolean save(Member member, GroupTopicType groupTopicType);

    boolean update(Member member, GroupTopicType groupTopicType);
}
