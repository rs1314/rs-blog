package cn.rs.blog.bean.weibo;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.picture.Picture;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Weibo implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7273104198908253587L;
	private Integer id;
    private Date createTime;
    private Integer memberId;
    private Member member;
    private Integer type;
    private String content;
    private Integer favor;
    private Integer status;
    private Integer commentCount;
    //是否已点赞，0未点赞，1已点赞
    private Integer isFavor;
    private List<Picture> pictures;

    private String md5;//和微博表关联起来的键
   

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
  
}
