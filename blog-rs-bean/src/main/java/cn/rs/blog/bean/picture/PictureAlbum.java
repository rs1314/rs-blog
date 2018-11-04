package cn.rs.blog.bean.picture;

import java.util.Date;

import cn.rs.blog.bean.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureAlbum {
    private Integer id;

    private Date createTime;

    private Date updateTime;

    private Integer memberId;

    private Member member;

    private Integer commentCount;

    private Integer favorCount;

    private String name;

    private String description;

    private Integer juri;

    private String cover;

    private Integer type;

  
}