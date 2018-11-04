package cn.rs.blog.bean.system;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRule implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1602527606471853236L;
	private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    private String name;
    private Integer score;
    private String remark;
    private String type;
    private Integer status;

}
