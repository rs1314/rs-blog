package cn.rs.blog.bean.cms;


import java.util.Date;

import javax.validation.constraints.Digits;

import cn.rs.blog.bean.common.Archive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article extends Archive {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1915154908469375239L;
	private Integer id;
    private Date collectTime;
    @Digits(integer = 11,fraction = 0,message = "栏目不能为空")
    private Integer cateId;
    private Integer status;

    private ArticleCate articleCate;
}