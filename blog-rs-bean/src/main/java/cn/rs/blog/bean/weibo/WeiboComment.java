package cn.rs.blog.bean.weibo;

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
public class WeiboComment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -8564598769332388424L;
	private Integer id;
    private Date createTime;
    private Integer memberId;
    private Integer weiboId;
    private Member member;
    private Weibo weibo;
    private Integer commentId;
    private WeiboComment weiboComment;
    private String content;
    private Integer status;

  

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

   
}
