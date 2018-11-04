package cn.rs.blog.dao.group;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.group.GroupTopicType;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * @author: rs
 */
public interface IGroupTopicTypeDao extends IBaseDao<GroupTopicType> {

    List<GroupTopicType> list(@Param("groupId") Integer groupId);

}