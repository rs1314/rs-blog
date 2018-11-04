package cn.rs.blog.bean.system;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import cn.rs.blog.bean.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by rs
 */
@Data
@AllArgsConstructor
public class ActionLog implements Serializable {
    private Integer id;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Integer memberId;
    private Member member;
    private Integer actionId;
    private Action action;
    private String remark;
    private String actionIp;
    private Integer type;
    private Integer foreignId;

    public ActionLog(){

    }
    public ActionLog(Integer memberId,Integer actionId,String remark,String actionIp){
        this.memberId = memberId;
        this.actionId = actionId;
        this.remark = remark;
        this.actionIp = actionIp;

    }
    public ActionLog(Integer memberId,Integer actionId,String remark,String actionIp,Integer type,Integer foreignId){
        this.memberId = memberId;
        this.actionId = actionId;
        this.remark = remark;
        this.actionIp = actionIp;
        this.type = type;
        this.foreignId = foreignId;
    }
}
