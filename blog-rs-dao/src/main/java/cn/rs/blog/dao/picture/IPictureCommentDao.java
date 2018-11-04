package cn.rs.blog.dao.picture;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.picture.PictureComment;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

public interface IPictureCommentDao extends IBaseDao<PictureComment> {

    List<PictureComment> listByPicture(@Param("page") Page page, @Param("pictureId") Integer pictureId);

    int deleteByPicture(@Param("pictureId") Integer pictureId);
}