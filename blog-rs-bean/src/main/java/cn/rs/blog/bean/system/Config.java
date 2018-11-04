package cn.rs.blog.bean.system;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统设置信息实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Config implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4574026765693763246L;

	private String jkey;

    private String jvalue;

    private String description;


}