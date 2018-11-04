package cn.rs.blog.bean.group;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTopicType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -668625832441902558L;
	private Integer id;
    private Date createTime;
    private Integer groupId;
    private String name;
    private Integer juri;

  
}
