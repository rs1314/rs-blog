package cn.rs.blog.bean.group;

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
public class GroupTopicComment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 9160348483461670742L;

	private Integer id;

    private Date createTime;

    private Integer groupTopicId;

    private GroupTopic groupTopic;

    private Integer memberId;

    private Member member;

    private String content;

    private Integer commentId;

    private GroupTopicComment groupTopicComment;


  

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

  
}