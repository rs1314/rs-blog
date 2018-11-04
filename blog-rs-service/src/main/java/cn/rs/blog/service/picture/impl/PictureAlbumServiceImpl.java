package cn.rs.blog.service.picture.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.picture.PictureAlbum;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.dao.picture.IPictureAlbumDao;
import cn.rs.blog.service.picture.IPictureAlbumService;

/**
 * Created by rs
 */
@Service
public class PictureAlbumServiceImpl implements IPictureAlbumService {
    @Autowired
    private IPictureAlbumDao pictureAlbumDao;

    @Override
    public ResultModel listByMember(Integer memberId) {
        List<PictureAlbum> list = pictureAlbumDao.listByMember(memberId);
        ResultModel model = new ResultModel(0);
        model.setData(list);
        return model;
    }

    @Override
    public ResultModel<PictureAlbum> listByPage(Page page) {
        List<PictureAlbum> list = pictureAlbumDao.listByPage(page);
        ResultModel model = new ResultModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public boolean delete(Integer id) {
        return pictureAlbumDao.delete(id) == 1;
    }

    @Override
    public boolean save(PictureAlbum pictureAlbum) {
        if (pictureAlbum.getType() == null){
            pictureAlbum.setType(0);
        }
        if (StringUtils.isEmpty(pictureAlbum.getCover())){
            pictureAlbum.setCover(Const.DEFAULT_PICTURE_COVER);
        }
        return pictureAlbumDao.save(pictureAlbum) == 1;
    }

    @Override
    public boolean update(PictureAlbum pictureAlbum) {
        return pictureAlbumDao.update(pictureAlbum) == 1;
    }

    @Override
    public PictureAlbum findWeiboAlbum(Integer memberId) {
        return pictureAlbumDao.findWeiboAlbum(memberId);
    }

    @Override
    public PictureAlbum findById(Integer id) {
        return pictureAlbumDao.findById(id);
    }
}
