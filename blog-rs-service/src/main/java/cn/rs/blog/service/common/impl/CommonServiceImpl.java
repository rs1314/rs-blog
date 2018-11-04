package cn.rs.blog.service.common.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.dao.common.ICommonDao;
import cn.rs.blog.service.common.ICommonService;

/**
 * Created by rs
 */
@Service("commonService")
public class CommonServiceImpl implements ICommonService {
    @Autowired
    private ICommonDao commonDao;

    @Override
    public String getMysqlVsesion() {
        return commonDao.getMysqlVsesion();
    }
}
