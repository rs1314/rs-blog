package cn.rs.blog.bean.member;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员私信
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Message implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3409086848540842672L;
	private Integer id;
    private Date createTime;
    private Integer fromMemberId;
    private Member fromMember;
    private Integer toMemberId;
    private Member toMember;
    private String content;
    private String url;
    private Integer isread;
    private Integer appTag;
    private Integer type;
    private Integer relateKeyId;
    private String description;
    private Integer memberId;
    private Member member;

  
}