package cn.rs.blog.bean.common;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArchiveFavor implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -2652254683221004267L;
	private Integer id;
    private Date createTime;
    private Integer memberId;
    private Integer archiveId;

 
}
