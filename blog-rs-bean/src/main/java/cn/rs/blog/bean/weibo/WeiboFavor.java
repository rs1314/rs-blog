package cn.rs.blog.bean.weibo;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeiboFavor implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1189116315311665128L;
	private Integer id;
    private Date createTime;
    private Integer memberId;
    private Integer weiboId;

    
}
