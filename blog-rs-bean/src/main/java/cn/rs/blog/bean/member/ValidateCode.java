package cn.rs.blog.bean.member;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 验证码
 * Created by rs
 */
@Data
@AllArgsConstructor
public class ValidateCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4902177723535030141L;
	private Integer id;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date createTime;
	private String email;
	private String code;
	private Integer status;
	private Integer type;

	public ValidateCode(){

	}

	public ValidateCode(String email, String code, Integer type) {
		this.email = email;
		this.code = code;
		this.type = type;
	}


}