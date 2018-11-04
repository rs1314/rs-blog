package cn.rs.blog.bean.member;

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
public class MemberToken implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 2258229021955276059L;
	private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer memberId;
    private String token;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date expireTime;
    private Integer status;


}