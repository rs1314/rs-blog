package cn.rs.blog.service.member.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.rs.blog.bean.member.Checkin;
import cn.rs.blog.commoms.utils.ScoreRuleConsts;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.member.ICheckinDao;
import cn.rs.blog.service.member.ICheckinService;
import cn.rs.blog.service.member.IScoreDetailService;

/**
 * 签到
 * Created by rs
 */
@Service
public class CheckinServiceImpl implements ICheckinService {
    @Autowired
    private ICheckinDao checkinDao;
    @Autowired
    private IScoreDetailService scoreDetailService;

    @Override
    public List<Checkin> list(Page page, Integer memberId) {
        List<Checkin> list = checkinDao.listByPage(page,memberId);
        return list;
    }

    @Override
    public List<Checkin> todayList(Page page) {
        List<Checkin> list = checkinDao.todayList(page);
        return list;
    }

    @Override
    public List<Checkin> todayContinueList() {
        return checkinDao.todayContinueList();
    }

    @Override
    public Checkin todayCheckin(Integer memberId) {
        return checkinDao.todayCheckin(memberId);
    }

    @Override
    public Checkin yesterdayCheckin(Integer memberId) {
        return checkinDao.yesterdayCheckin(memberId);
    }

    @Override
    @Transactional
    public boolean save(Integer memberId) {
        synchronized (this){
            if (null != todayCheckin(memberId)){
                throw new OpeErrorException("今天已经签到过了");
            }
            Checkin checkin = new Checkin();
            checkin.setMemberId(memberId);
            Checkin yesterdayCheckin = yesterdayCheckin(memberId);
            if (null != yesterdayCheckin){
                checkin.setContinueDay(yesterdayCheckin.getContinueDay() + 1);
            }else {
                checkin.setContinueDay(1);
            }
            boolean result = checkinDao.save(checkin) == 1;
            if (result){
                scoreDetailService.scoreBonus(memberId, ScoreRuleConsts.CHECKIN, checkin.getId());
            }
            return result;
        }

    }
}
