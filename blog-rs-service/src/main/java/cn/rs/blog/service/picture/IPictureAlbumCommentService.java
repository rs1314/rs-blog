package cn.rs.blog.service.picture;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.PictureAlbumComment;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 *
 * @author rs
 */
public interface IPictureAlbumCommentService {

    PictureAlbumComment findById(int id);

    boolean save(Member loginMember, String content, Integer pictureAlbumId);

    boolean delete(Member loginMember, int id);

    ResultModel listByPictureAlbum(Page page, int pictureAlbumId);

    void deleteByPictureAlbum(Integer pictureAlbumId);
}
