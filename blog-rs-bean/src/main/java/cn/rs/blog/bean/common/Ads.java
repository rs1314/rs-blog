package cn.rs.blog.bean.common;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.rs.blog.bean.cms.Article;
import cn.rs.blog.bean.cms.ArticleCate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 广告实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ads {
    private Integer id;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer type;
    private String typeName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String content;
    private String link;
    private Integer status;

}
