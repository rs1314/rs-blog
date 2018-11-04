package cn.rs.blog.bean.system;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rs.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Action implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1272860844545208800L;
	private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String name;
    private String log;
    private Integer status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
}
