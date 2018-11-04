package cn.rs.blog.service.picture;


import cn.rs.blog.bean.picture.PictureAlbumFavor;

/**
 * 图片点赞Service接口
 * Created by rs
 */
public interface IPictureAlbumFavorService {

    PictureAlbumFavor find(Integer pictureAlbumId, Integer memberId);

    void save(Integer pictureAlbumId, Integer memberId);

    void delete(Integer pictureAlbumId, Integer memberId);
}