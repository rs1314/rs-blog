package cn.rs.blog.bean.picture;

import java.util.Date;

import cn.rs.blog.bean.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureAlbumComment {
    private Integer id;

    private Date createTime;

    private Integer memberId;

    private Member member;

    private Integer pictureAlbumId;

    private PictureAlbum pictureAlbum;

    private String content;

 
}