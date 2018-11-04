package cn.rs.blog.service.member;

import java.util.List;

import cn.rs.blog.bean.member.Checkin;
import cn.rs.blog.core.model.Page;


/**
 * 签到
 * Created by rs
 */
public interface ICheckinService {

    List<Checkin> list(Page page, Integer memberId);

    List<Checkin> todayList(Page page);

    List<Checkin> todayContinueList();

    Checkin todayCheckin(Integer memberId);

    Checkin yesterdayCheckin(Integer memberId);

    boolean save(Integer memberId);
}
