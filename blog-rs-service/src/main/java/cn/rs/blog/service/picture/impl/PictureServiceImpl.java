package cn.rs.blog.service.picture.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.Picture;
import cn.rs.blog.commoms.utils.PictureUtil;
import cn.rs.blog.core.consts.AppTag;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.MessageType;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.dao.picture.IPictureDao;
import cn.rs.blog.service.member.IMessageService;
import cn.rs.blog.service.picture.IPictureFavorService;
import cn.rs.blog.service.picture.IPictureService;

/**
 *
 * @author rs
 */
@Service
public class PictureServiceImpl implements IPictureService {
    @Autowired
    private IPictureDao pictureDao;
    @Autowired
    private IPictureFavorService pictureFavorService;
    @Autowired
    private IMessageService messageService;

    @Override
    public List<Picture> find(Integer foreignId) {
        return pictureDao.find(foreignId);
    }

    @Override
    public Picture findById(Integer pictureId,int loginMemberId) {
        return pictureDao.findById(pictureId,loginMemberId);
    }

    @Override
    public ResultModel<Picture> listByPage(Page page, int loginMemberId) {
        List<Picture> list = pictureDao.listByPage(page,loginMemberId);
        ResultModel model = new ResultModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public ResultModel<Picture> listByAlbum(Page page, Integer pictureAlbumId, int loginMemberId) {
        List<Picture> list = pictureDao.listByAlbum(page,pictureAlbumId,loginMemberId);
        ResultModel model = new ResultModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public int deleteByForeignId(HttpServletRequest request, Integer foreignId) {
        List<Picture> pictures = this.find(foreignId);
        PictureUtil.delete(request,pictures);
        return pictureDao.deleteByForeignId(foreignId);
    }

    @Override
    public boolean delete(HttpServletRequest request, Integer pictureId) {
        Picture picture = this.findById(pictureId,0);
        PictureUtil.delete(request,picture);
        return pictureDao.delete(pictureId) == 1;
    }

    @Override
    public int save(Picture picture) {
        return pictureDao.save(picture);
    }

    @Override
    public int update(Integer foreignId, String ids,String description) {
        if(StringUtils.isNotEmpty(ids)){
            String[] idsArr = ids.split(",");
            return pictureDao.update(foreignId, idsArr,description);
        }
        return 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultModel favor(Member loginMember, int pictureId) {
        String message;
        ResultModel<Integer> resultModel;
        Picture picture = this.findById(pictureId,loginMember.getId());
        if(pictureFavorService.find(pictureId,loginMember.getId()) == null){
            //增加
            pictureDao.favor(pictureId,1);
            picture.setFavorCount(picture.getFavorCount() + 1);
            pictureFavorService.save(pictureId,loginMember.getId());
            message = "点赞成功";
            resultModel = new ResultModel(0,message);
            //点赞之后发送系统信息
            messageService.diggDeal(loginMember.getId(),picture.getMemberId(), AppTag.PICTURE, MessageType.PICTURE_ZAN,pictureId);
        }else {
            //减少
            pictureDao.favor(pictureId,-1);
            picture.setFavorCount(picture.getFavorCount() - 1);
            pictureFavorService.delete(pictureId,loginMember.getId());
            message = "取消赞成功";
            resultModel = new ResultModel(1,message);
        }
        resultModel.setData(picture.getFavorCount());
        return resultModel;
    }
}
