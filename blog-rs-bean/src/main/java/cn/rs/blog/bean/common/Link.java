package cn.rs.blog.bean.common;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 友情链接
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Link {
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private String name;
    private String url;
    private Integer sort;
    private Integer recomment;
    private Integer status;

  
}
