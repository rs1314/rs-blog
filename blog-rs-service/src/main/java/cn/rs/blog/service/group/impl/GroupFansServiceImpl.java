package cn.rs.blog.service.group.impl;

import java.util.List;



import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.group.Group;
import cn.rs.blog.bean.group.GroupFans;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.group.IGroupFansDao;
import cn.rs.blog.service.group.IGroupFansService;

/**
 * Created by rs
 */
@Service("groupFansService")
public class GroupFansServiceImpl implements IGroupFansService {
    @Autowired
    private IGroupFansDao groupFansDao;

    @Override
    public ResultModel listByPage(Page page, Integer groupId) {
        List<GroupFans> list = groupFansDao.listByPage(page, groupId);
        ResultModel model = new ResultModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public GroupFans findByMemberAndGroup(@Param("groupId") Integer groupId, @Param("memberId") Integer memberId) {
        return groupFansDao.findByMemberAndGroup(groupId,memberId);
    }

    /**
     * 关注
     * @param loginMember
     * @param groupId
     * @return
     */
    @Override
    public boolean save(Member loginMember, Integer groupId) {
        if (null != groupFansDao.findByMemberAndGroup(groupId,loginMember.getId())){
            throw new OpeErrorException("已经关注");
        }
        return groupFansDao.save(groupId,loginMember.getId()) == 1;
    }

    /**
     * 取消关注
     * @param loginMember
     * @param groupId
     * @return
     */
    @Override
    public boolean delete(Member loginMember, Integer groupId) {
       return groupFansDao.delete(groupId,loginMember.getId()) > 0;
    }


    @Override
    public ResultModel listByMember(Page page, Integer memberId) {
        List<Group> list = groupFansDao.listByMember(page, memberId);
        ResultModel model = new ResultModel(0,page);
        model.setData(list);
        return model;
    }

}
