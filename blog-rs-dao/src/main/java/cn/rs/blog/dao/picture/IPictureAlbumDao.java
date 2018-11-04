package cn.rs.blog.dao.picture;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.picture.PictureAlbum;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

public interface IPictureAlbumDao extends IBaseDao<PictureAlbum> {

    List<PictureAlbum> listByMember(@Param("memberId") Integer memberId);

    List<PictureAlbum> listByPage(@Param("page") Page page);

    PictureAlbum findWeiboAlbum(@Param("memberId") Integer memberId);
}