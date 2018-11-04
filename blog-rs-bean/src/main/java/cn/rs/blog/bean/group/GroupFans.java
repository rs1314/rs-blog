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
public class GroupFans implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5462276431100956248L;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer groupId;
    private Group group;
    private Integer memberId;
    private Member member;
}