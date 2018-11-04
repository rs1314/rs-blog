package cn.rs.blog.service.picture;


import cn.rs.blog.bean.picture.PictureFavor;

/**
 * 图片点赞Service接口
 * Created by rs
 */
public interface IPictureFavorService {

    PictureFavor find(Integer pictureId, Integer memberId);

    void save(Integer pictureId, Integer memberId);

    void delete(Integer pictureId, Integer memberId);
}