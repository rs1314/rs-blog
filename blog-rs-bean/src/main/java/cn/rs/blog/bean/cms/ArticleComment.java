package cn.rs.blog.bean.cms;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.rs.blog.bean.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章评论实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleComment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1768547470940645219L;

	private Integer id;

    private Date createTime;

    private Integer articleId;

    private Article article;

    private Integer memberId;

    private Member member;

    private String content;


   

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}