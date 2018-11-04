package cn.rs.blog.bean.member;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 会员实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Member implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8600356032165086166L;
	private Integer id;
	private Date createTime;

	//分组ID
	private Integer groupId;

	//会员名称
	private String name;

	//邮箱
	private String email;

	//手机号码
	private String phone;

	//密码
	private String password;

	//性别
	private String sex;

	//头像
	private String avatar;

	//注册IP
	private String regip;

	//登录次数
	private Integer loginCount;

	//本次登录时间
	private Date currLoginTime;

	//本次登录IP
	private String currLoginIp;

	//最后登录时间
	private Date lastLoginTime;

	//最后登录IP
	private String lastLoginIp;

	//更新资料时间
	private Date updateTime;

	//金额
	private Double money;

	//积分
	private Integer score;

	//是否已激活，0未激活，1已激活
	private Integer isActive;

	//0禁用，1启用
	private Integer status;

	//生日
	private String birthday;

	//居住省份
	private String addprovince;

	//居住城市
	private String addcity;

	//居住地区
	private String addarea;

	//居住地址
	private String address;

	//QQ
	private String qq;

	//微信
	private String wechat;

	//联系手机号
	private String contactPhone;

	//联系邮箱
	private String contactEmail;

	//个人网站
	private String website;

	//个人介绍
	private String introduce;

	//是否管理员
	private Integer isAdmin;

	//关注会员数
	private Integer follows;

	//粉丝数
	private Integer fans;

	//私信列表
	private List<Message> messages;


	

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}