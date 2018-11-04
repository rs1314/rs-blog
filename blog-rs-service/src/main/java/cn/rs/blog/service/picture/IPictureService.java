package cn.rs.blog.service.picture;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.Picture;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;

/**
 * Created by rs
 */
public interface IPictureService {

    List<Picture> find(Integer foreignId);

    Picture findById(Integer pictureId,int loginMemberId);

    ResultModel<Picture> listByPage(Page page, int loginMemberId);

    ResultModel<Picture> listByAlbum(Page page, Integer pictureAlbumId, int loginMemberId);

    int deleteByForeignId(HttpServletRequest request, Integer foreignId);

    boolean delete(HttpServletRequest request, Integer pictureId);

    int save(Picture picture);

    int update(Integer foreignId, String ids,String description);

    ResultModel favor(Member loginMember, int pictureId);
}