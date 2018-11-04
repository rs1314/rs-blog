package cn.rs.blog.bean.group;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

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
public class Group implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5544920862431399954L;
	private Integer id;
    private Date createTime;
    @NotBlank(message = "群组名称不能为空")
    private String name;
    private String logo;
    private Integer creator;
    private Member creatorMember;
    private String managers;
    private String tags;
    private String introduce;
    private Integer status;
    private Integer canPost;
    private Integer topicReview;
    private Integer topicCount;
    private Integer fansCount;
    private Integer typeId;


   

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}