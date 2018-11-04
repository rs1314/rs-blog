package cn.rs.blog.dao.picture;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.picture.PictureAlbumFavor;
import cn.rs.blog.dao.common.IBaseDao;

public interface IPictureAlbumFavorDao extends IBaseDao<PictureAlbumFavor> {
    PictureAlbumFavor find(@Param("pictureAlbumId") Integer pictureAlbumId, @Param("memberId") Integer memberId);

    Integer save(@Param("pictureAlbumId") Integer pictureAlbumId, @Param("memberId") Integer memberId);

    Integer delete(@Param("pictureAlbumId") Integer pictureAlbumId, @Param("memberId") Integer memberId);
}