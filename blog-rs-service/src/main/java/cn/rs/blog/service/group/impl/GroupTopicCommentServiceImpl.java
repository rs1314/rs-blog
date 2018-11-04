package cn.rs.blog.service.group.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.group.GroupTopic;
import cn.rs.blog.bean.group.GroupTopicComment;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.ActionUtil;
import cn.rs.blog.commoms.utils.ScoreRuleConsts;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.consts.AppTag;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.MessageType;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.group.IGroupTopicCommentDao;
import cn.rs.blog.service.group.IGroupTopicCommentService;
import cn.rs.blog.service.group.IGroupTopicService;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.member.IMessageService;
import cn.rs.blog.service.member.IScoreDetailService;
import cn.rs.blog.service.system.IActionLogService;

/**
 * Created by rs
 */
@Service("groupTopicCommentService")
public class GroupTopicCommentServiceImpl implements IGroupTopicCommentService {
    @Autowired
    private IGroupTopicCommentDao groupTopicCommentDao;
    @Autowired
    private IGroupTopicService groupTopicService;
    @Autowired
    private IActionLogService actionLogService;
    @Autowired
    private IScoreDetailService scoreDetailService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMemberService memberService;

    @Override
    public GroupTopicComment findById(int id) {
        return this.atFormat(groupTopicCommentDao.findById(id));
    }

    @Override
    public boolean save(Member loginMember, String content, Integer groupTopicId, Integer commentId) {
        GroupTopic groupTopic = groupTopicService.findById(groupTopicId,loginMember);
        ValidUtill.checkIsNull(groupTopic, Messages.TOPIC_NOT_EXISTS);
        ValidUtill.checkIsNull(content, Messages.CONTENT_NOT_EMPTY);
        GroupTopicComment groupTopicComment = new GroupTopicComment();
        groupTopicComment.setMemberId(loginMember.getId());
        groupTopicComment.setGroupTopicId(groupTopicId);
        groupTopicComment.setContent(content);
        groupTopicComment.setCommentId(commentId);
        int result = groupTopicCommentDao.save(groupTopicComment);
        if(result == 1){
            //@会员处理并发送系统消息
            messageService.atDeal(loginMember.getId(),content, AppTag.GROUP, MessageType.GROUP_TOPIC_COMMENT_REFER,groupTopicComment.getId());
            messageService.diggDeal(loginMember.getId(),groupTopic.getMemberId(),content,AppTag.GROUP,MessageType.GROUP_TOPIC_REPLY,groupTopic.getId());
            if (commentId != null){
                GroupTopicComment replyGroupTopicComment = findById(commentId);
                if(replyGroupTopicComment != null){
                    messageService.diggDeal(loginMember.getId(),replyGroupTopicComment.getMemberId(),content,AppTag.GROUP,MessageType.GROUP_TOPIC_REPLY_REPLY,replyGroupTopicComment.getId());
                }
            }
            //群组帖子评论奖励
            scoreDetailService.scoreBonus(loginMember.getId(), ScoreRuleConsts.GROUP_TOPIC_COMMENTS, groupTopicComment.getId());
        }
        return result == 1;
    }

    @Override
    public ResultModel listByGroupTopic(Page page, int groupTopicId) {
        List<GroupTopicComment> list = groupTopicCommentDao.listByGroupTopic(page, groupTopicId);
        this.atFormat(list);
        ResultModel model = new ResultModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public void deleteByTopic(int groupTopicId) {
        groupTopicCommentDao.deleteByTopic(groupTopicId);
    }

    @Override
    public boolean delete(Member loginMember, int id){
        GroupTopicComment groupTopicComment = this.findById(id);
        ValidUtill.checkIsNull(groupTopicComment, Messages.COMMENT_NOT_EXISTS);
        int result = groupTopicCommentDao.delete(id);
        if(result == 1){
            //扣除积分
            scoreDetailService.scoreCancelBonus(loginMember.getId(),ScoreRuleConsts.GROUP_TOPIC_COMMENTS,id);
            actionLogService.save(loginMember.getCurrLoginIp(),loginMember.getId(), ActionUtil.DELETE_GROUP_TOPIC_COMMENT,"ID："+groupTopicComment.getId()+"，内容："+groupTopicComment.getContent());
        }
        return result == 1;
    }

    public GroupTopicComment atFormat(GroupTopicComment groupTopicComment){
        groupTopicComment.setContent(memberService.atFormat(groupTopicComment.getContent()));
        return groupTopicComment;
    }

    public List<GroupTopicComment> atFormat(List<GroupTopicComment> groupTopicCommentList){
        for (GroupTopicComment groupTopicComment : groupTopicCommentList){
            atFormat(groupTopicComment);
        }
        return groupTopicCommentList;
    }
}
