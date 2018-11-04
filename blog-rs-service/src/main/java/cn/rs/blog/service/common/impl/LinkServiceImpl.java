package cn.rs.blog.service.common.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.common.Link;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.common.ILinkDao;
import cn.rs.blog.service.common.ILinkService;

/**
 * Created by rs
 */
@Service("linkService")
public class LinkServiceImpl implements ILinkService {
    @Autowired
    private ILinkDao linkDao;

    @Override
    public boolean save(Link link) {
        return linkDao.save(link) == 1;
    }

    @Override
    public ResultModel listByPage(Page page) {
        List<Link> list = linkDao.listByPage(page);
        ResultModel model = new ResultModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public ResultModel allList() {
        List<Link> list = linkDao.allList();
        ResultModel model = new ResultModel(0);
        model.setData(list);
        return model;
    }

    @Override
    public ResultModel recommentList() {
        List<Link> list = linkDao.recommentList();
        ResultModel model = new ResultModel(0);
        model.setData(list);
        return model;
    }

    @Override
    public boolean update(Link link) {
        Link findLink = this.findById(link.getId());
        ValidUtill.checkIsNull(findLink, Messages.FRIIEND_LINK_NOT_EXISTS);
        return linkDao.update(link) == 1;
    }

    @Override
    public boolean delete(Integer id) {
        return linkDao.delete(id) == 1;
    }

    @Override
    public Link findById(Integer id) {
        return linkDao.findById(id);
    }

    @Override
    public boolean enable(Integer id) {
       return linkDao.enable(id) == 1;
    }
}
