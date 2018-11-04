package cn.rs.blog.service.member.impl;

import java.util.Calendar;
import java.util.Date;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.member.MemberToken;
import cn.rs.blog.dao.member.IMemberTokenDao;
import cn.rs.blog.service.member.IMemberTokenService;

/**
 * Created by rs
 */
@Service("memberTokenService")
public class MemberTokenServiceImpl implements IMemberTokenService {
    @Autowired
    private IMemberTokenDao memberTokenDao;

    @Override
    public MemberToken getByToken(String token) {
        return memberTokenDao.getByToken(token);
    }

    @Override
    public void save(Integer memberId,String token) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.HOUR_OF_DAY,1);
        memberTokenDao.save(memberId,token,calendar.getTime());
    }
}
