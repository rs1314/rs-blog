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
public class GroupType implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4721657290117212428L;
	private Integer id;
    private Date createTime;
    private String name;

  

}
