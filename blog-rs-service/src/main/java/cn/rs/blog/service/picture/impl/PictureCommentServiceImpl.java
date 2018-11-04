package cn.rs.blog.service.picture.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.Picture;
import cn.rs.blog.bean.picture.PictureComment;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.consts.AppTag;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.MessageType;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.picture.IPictureCommentDao;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.member.IMessageService;
import cn.rs.blog.service.picture.IPictureCommentService;
import cn.rs.blog.service.picture.IPictureService;

/**
 * Created by rs
 */
@Service("pictureCommentService")
public class PictureCommentServiceImpl implements IPictureCommentService {
    @Autowired
    private IPictureCommentDao pictureCommentDao;
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private IMemberService memberService;

    @Override
    public PictureComment findById(int id) {
        PictureComment PictureComment = pictureCommentDao.findById(id);
        atFormat(PictureComment);
        return PictureComment;
    }

    @Override
    public boolean save(Member loginMember, String content, Integer pictureId) {
        Picture picture = pictureService.findById(pictureId,loginMember.getId());
        ValidUtill.checkIsNull(picture, Messages.PICTURE_NOT_EXISTS);
        PictureComment pictureComment = new PictureComment();
        pictureComment.setMemberId(loginMember.getId());
        pictureComment.setPictureId(pictureId);
        pictureComment.setContent(content);
        int result = pictureCommentDao.save(pictureComment);
        if(result == 1){
            //@会员处理并发送系统消息
            messageService.atDeal(loginMember.getId(),content, AppTag.PICTURE, MessageType.PICTURE_COMMENT_REFER,picture.getPictureId());
            //回复微博发送系统信息
            messageService.diggDeal(loginMember.getId(), picture.getMemberId(), content,AppTag.PICTURE, MessageType.PICTURE_REPLY, picture.getPictureId());
        }
        return result == 1;
    }

    @Override
    public ResultModel listByPicture(Page page, int pictureId) {
        List<PictureComment> list = pictureCommentDao.listByPicture(page, pictureId);
        atFormat(list);
        ResultModel model = new ResultModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public void deleteByPicture(Integer pictureId) {
        pictureCommentDao.deleteByPicture(pictureId);
    }

    @Override
    public boolean delete(Member loginMember, int id) {
        PictureComment pictureComment = this.findById(id);
        ValidUtill.checkIsNull(pictureComment, Messages.COMMENT_NOT_EXISTS);
        int result = pictureCommentDao.delete(id);
        return result == 1;
    }

    public PictureComment atFormat(PictureComment pictureComment){
        pictureComment.setContent(memberService.atFormat(pictureComment.getContent()));
        return pictureComment;
    }

    public List<PictureComment> atFormat(List<PictureComment> pictureCommentList){
        for (PictureComment pictureComment : pictureCommentList){
            atFormat(pictureComment);
        }
        return pictureCommentList;
    }
}
