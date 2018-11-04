package cn.rs.blog.bean.member;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员分组实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemGroup implements Serializable {
    private Integer id;
    private Date createTime;
    private Integer isadmin;

    private String name;

    private Integer fid;

    private Integer rankid;
  

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

  

}