package cn.rs.blog.dao.picture;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.picture.PictureFavor;
import cn.rs.blog.dao.common.IBaseDao;

public interface IPictureFavorDao extends IBaseDao<PictureFavor> {

    PictureFavor find(@Param("pictureId") Integer pictureId, @Param("memberId") Integer memberId);

    Integer save(@Param("pictureId") Integer pictureId, @Param("memberId") Integer memberId);

    Integer delete(@Param("pictureId") Integer pictureId, @Param("memberId") Integer memberId);
}