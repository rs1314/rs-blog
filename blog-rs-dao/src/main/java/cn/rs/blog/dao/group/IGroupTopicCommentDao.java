package cn.rs.blog.dao.group;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.group.GroupTopicComment;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * Created by rs
 */
public interface IGroupTopicCommentDao extends IBaseDao<GroupTopicComment> {

    List<GroupTopicComment> listByGroupTopic(@Param("page") Page page, @Param("groupTopicId") Integer groupTopicId);

    int deleteByTopic(@Param("groupTopicId") Integer groupTopicId);
}