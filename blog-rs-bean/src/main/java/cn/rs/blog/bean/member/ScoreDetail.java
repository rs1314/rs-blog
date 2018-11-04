package cn.rs.blog.bean.member;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by rs
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreDetail implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6566617237517177735L;
	private Integer id;
    private Date createTime;
    private Integer type;
    private Integer memberId;
    private Member member;
    private Integer score;
    private Integer balance;
    private String remark;
    private Integer foreignId;
    private Integer scoreRuleId;
    private Integer status;

  
}