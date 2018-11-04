package cn.rs.blog.service.group;

import cn.rs.blog.bean.group.GroupTopicComment;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IGroupTopicCommentService {

    GroupTopicComment findById(int id);

    boolean save(Member loginMember, String content, Integer groupTopicId, Integer commentId);

    boolean delete(Member loginMember, int id);

    ResultModel listByGroupTopic(Page page, int groupTopicId);

    void deleteByTopic(int groupTopicId);
}
