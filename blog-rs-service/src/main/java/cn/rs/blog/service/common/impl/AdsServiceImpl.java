package cn.rs.blog.service.common.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.common.Ads;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.exception.NotFountException;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.IAdsDao;
import cn.rs.blog.service.common.IAdsService;

/**
 * Created by rs
 */
@Service("adsService")
public class AdsServiceImpl implements IAdsService {
    @Autowired
    private IAdsDao adsDao;

    /**
     * 保存广告信息
     *
     * @param ads
     * @return
     */
    @Override
    public boolean save(Ads ads) {
        if (adsDao.save(ads) == 0) {
            throw new OpeErrorException();
        }
        return true;
    }

    @Override
    public ResultModel listByPage(Page page) {
        List<Ads> list = adsDao.listByPage(page);
        ResultModel model = new ResultModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public boolean update(Ads ads) {
        Ads findAds = this.findById(ads.getId());
        if (findAds == null){
            throw new NotFountException(Messages.AD_NOT_EXISTS);
        }
        if (adsDao.update(ads) == 0) {
            throw new OpeErrorException();
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        if (adsDao.delete(id) == 0) {
            throw new OpeErrorException();
        }
        return true;
    }

    @Override
    public Ads findById(Integer id) {
        return adsDao.findById(id);
    }

    @Override
    public boolean enable(Integer id) {
        if (adsDao.enable(id) == 0){
            throw new OpeErrorException();
        }
        return true;
    }
}
