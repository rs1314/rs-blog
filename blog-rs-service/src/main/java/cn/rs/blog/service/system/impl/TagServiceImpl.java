package cn.rs.blog.service.system.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.rs.blog.bean.system.Tag;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.exception.OpeErrorException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.dao.system.ITagDao;
import cn.rs.blog.service.system.ITagService;

/**
 * Created by rs
 */
@Service("tagService")
public class TagServiceImpl implements ITagService {
    @Autowired
    private ITagDao tagDao;

    @Override
    public boolean save(Tag tag) {
        return tagDao.save(tag) == 1;
    }

    @Override
    public ResultModel listByPage(Page page, int funcType) {
        List<Tag> list = tagDao.listByPage(page,funcType);
        ResultModel model = new ResultModel(0, page);
        model.setData(list);
        return model;
    }

    @Override
    public boolean update(Tag tag) {
        Tag findTag = this.findById(tag.getId());
        if (findTag == null) {
            throw new OpeErrorException("标签不存在");
        }
        return tagDao.update(tag) == 1;
    }

    @Override
    public boolean delete(Integer id) {
        return tagDao.delete(id) > 0;
    }

    @Override
    public Tag findById(Integer id) {
        return tagDao.findById(id);
    }
}
