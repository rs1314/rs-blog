package cn.rs.blog.dao.member;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.rs.blog.bean.member.Checkin;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IBaseDao;

/**
 * 会员签到DAO
 * Created by rs
 */
public interface ICheckinDao extends IBaseDao<Checkin> {
    List<Checkin> listByPage(@Param("page") Page page, @Param("memberId") Integer memberId);

    List<Checkin> todayList(@Param("page") Page page);

    List<Checkin> todayContinueList();

    Checkin todayCheckin(@Param("memberId") Integer memberId);

    Checkin yesterdayCheckin(@Param("memberId") Integer memberId);

}