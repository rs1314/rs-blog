package cn.rs.blog.service.picture;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.PictureComment;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;


/**
 * Created by rs
 */
public interface IPictureCommentService {

    PictureComment findById(int id);

    boolean save(Member loginMember, String content, Integer pictureId);

    boolean delete(Member loginMember, int id);

    ResultModel listByPicture(Page page, int pictureId);

    void deleteByPicture(Integer pictureId);
}
