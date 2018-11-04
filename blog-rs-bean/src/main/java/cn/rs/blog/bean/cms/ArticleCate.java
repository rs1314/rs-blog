package cn.rs.blog.bean.cms;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章栏目实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleCate implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4455501399541888666L;
	private Integer id;
    private Date createTime;
    @Digits(integer = 11,fraction = 0,message = "上级栏目不能为空")
    private Integer fid;
    @NotBlank(message = "栏目名称不能为空")
    private String name;
    private Integer status;
    private Integer sort;

  

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}