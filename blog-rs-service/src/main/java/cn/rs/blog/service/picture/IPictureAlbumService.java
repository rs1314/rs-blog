package cn.rs.blog.service.picture;

import cn.rs.blog.bean.picture.PictureAlbum;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;

/**
 * Created by rs
 */
public interface IPictureAlbumService {

    ResultModel<PictureAlbum> listByMember(Integer memberId);

    ResultModel<PictureAlbum> listByPage(Page page);

    boolean delete(Integer id);

    boolean save(PictureAlbum pictureAlbum);

    boolean update(PictureAlbum pictureAlbum);

    PictureAlbum findWeiboAlbum(Integer memberId);

    PictureAlbum findById(Integer id);
}