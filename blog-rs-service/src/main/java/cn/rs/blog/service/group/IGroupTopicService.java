package cn.rs.blog.service.group;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.rs.blog.bean.group.GroupTopic;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IGroupTopicService {
    GroupTopic findById(int id);

    GroupTopic findById(int id,Member loginMember);

    boolean save(Member member, GroupTopic groupTopic);

    boolean update(Member member, GroupTopic groupTopic);

    boolean delete(Member loginMember, int id);

    boolean indexDelete(HttpServletRequest request, Member loginMember, int id);

    ResultModel listByPage(Page page, String key, int groupId, int status, int memberId, int typeId);

    boolean audit(Member member, int id);

    boolean top(Member member, int id, int top);

    boolean essence(Member member, int id, int essence);

    ResultModel favor(Member loginMember, int id);

    List<GroupTopic> listByCustom(int gid, String sort, int num, int day, int thumbnail);
}
