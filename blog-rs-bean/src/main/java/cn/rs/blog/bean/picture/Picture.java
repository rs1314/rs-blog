package cn.rs.blog.bean.picture;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.rs.blog.bean.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Picture implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4219191200775281184L;
	private Integer pictureId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer memberId;
    private Member member;
    private Integer type;
    private Integer foreignId;
    private String path;
    private String thumbnailPath;
    private String smallPath;
    private String md5;
    private Integer width;
    private Integer height;
    private String description;
    private Integer commentCount;
    private Integer favorCount;
    private Integer albumId;
    private PictureAlbum pictureAlbum;
    //是否已点赞，0未点赞，1已点赞
    private Integer isFavor;
}