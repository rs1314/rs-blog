package cn.rs.blog.bean.group;


import java.util.Date;

import cn.rs.blog.bean.common.Archive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupTopic extends Archive {
    /**
	 * 
	 */
	private static final long serialVersionUID = 4475980890093269641L;
	private Integer id;
    private Date collectTime;
    private Integer groupId;
    private Group group;
    private Integer typeId;
    private GroupTopicType groupTopicType;
    private Integer status;
    private Integer isTop;
    private Integer isEssence;


}