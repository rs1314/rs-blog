package cn.rs.blog.bean.member;

import java.util.Date;

import cn.rs.blog.bean.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 签到
 * @author rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkin extends BaseEntity {
    private Integer id;

    private Date createTime;

    private Integer memberId;

    private Member member;

    private Integer continueDay;

   
}