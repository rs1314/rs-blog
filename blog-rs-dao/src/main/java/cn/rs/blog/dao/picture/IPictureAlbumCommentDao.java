package cn.rs.blog.dao.picture;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.picture.PictureAlbumComment;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

public interface IPictureAlbumCommentDao extends IBaseDao<PictureAlbumComment> {
    List<PictureAlbumComment> listByPictureAlbum(@Param("page") Page page, @Param("pictureAlbumId") Integer pictureAlbumId);

    int deleteByPictureAlbum(@Param("pictureAlbumId") Integer pictureAlbumId);
}