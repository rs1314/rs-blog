package cn.rs.blog.bean.picture;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.rs.blog.bean.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureComment {
    private Integer id;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private Integer memberId;

    private Member member;

    private Integer pictureId;

    private Picture picture;

    private String content;

 
}