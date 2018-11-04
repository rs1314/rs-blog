package cn.rs.blog.service.picture.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.PictureAlbum;
import cn.rs.blog.bean.picture.PictureAlbumComment;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.picture.IPictureAlbumCommentDao;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.picture.IPictureAlbumCommentService;
import cn.rs.blog.service.picture.IPictureAlbumService;

/**
 *
 * @author rs
 */
@Service("pictureAlbumCommentService")
public class PictureAlbumCommentServiceImpl implements IPictureAlbumCommentService {
    @Autowired
    private IPictureAlbumCommentDao pictureAlbumCommentDao;
    @Autowired
    private IPictureAlbumService pictureAlbumService;
    @Autowired
    private IMemberService memberService;

    @Override
    public PictureAlbumComment findById(int id) {
        PictureAlbumComment pictureAlbumComment = pictureAlbumCommentDao.findById(id);
        atFormat(pictureAlbumComment);
        return pictureAlbumComment;
    }

    @Override
    public boolean save(Member loginMember, String content, Integer pictureAlbumId) {
        PictureAlbum pictureAlbum = pictureAlbumService.findById(pictureAlbumId);
        ValidUtill.checkIsNull(pictureAlbum, Messages.PICTURE_ALBUM_NOT_EXISTS);
        PictureAlbumComment pictureAlbumComment = new PictureAlbumComment();
        pictureAlbumComment.setMemberId(loginMember.getId());
        pictureAlbumComment.setPictureAlbumId(pictureAlbumId);
        pictureAlbumComment.setContent(content);
        int result = pictureAlbumCommentDao.save(pictureAlbumComment);
        return result == 1;
    }

    @Override
    public ResultModel listByPictureAlbum(Page page, int pictureAlbumId) {
        List<PictureAlbumComment> list = pictureAlbumCommentDao.listByPictureAlbum(page, pictureAlbumId);
        atFormat(list);
        ResultModel model = new ResultModel(0,page);
        model.setData(list);
        return model;
    }

    @Override
    public void deleteByPictureAlbum(Integer pictureAlbumId) {
        pictureAlbumCommentDao.deleteByPictureAlbum(pictureAlbumId);
    }

    @Override
    public boolean delete(Member loginMember, int id) {
        PictureAlbumComment pictureAlbumComment = this.findById(id);
        ValidUtill.checkIsNull(pictureAlbumComment, Messages.COMMENT_NOT_EXISTS);
        int result = pictureAlbumCommentDao.delete(id);
        return result == 1;
    }

    public PictureAlbumComment atFormat(PictureAlbumComment pictureAlbumComment){
        pictureAlbumComment.setContent(memberService.atFormat(pictureAlbumComment.getContent()));
        return pictureAlbumComment;
    }

    public List<PictureAlbumComment> atFormat(List<PictureAlbumComment> pictureAlbumCommentList){
        for (PictureAlbumComment pictureAlbumComment : pictureAlbumCommentList){
            atFormat(pictureAlbumComment);
        }
        return pictureAlbumCommentList;
    }
}
