package cn.rs.blog.web.web.manage;

import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.cms.ArticleCate;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.exception.ParamException;
import cn.rs.blog.service.cms.IArticleCateService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller
@RequestMapping("/")
@Before(AdminLoginInterceptor.class)
public class ArticleCateController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/cms/articleCate";
    @Autowired
    private IArticleCateService articleCateService;

    @RequestMapping("${rsblog.managePath}/cms/articleCate/list")
    public String list(Model model){
        List<ArticleCate> list = articleCateService.list();
        model.addAttribute("list",list);
        return MANAGE_FTL_PATH + "/lists";
    }

    @RequestMapping("${rsblog.managePath}/cms/articleCate/add")
    public String add(Model model){
        return MANAGE_FTL_PATH + "/adds";
    }

    @RequestMapping("${rsblog.managePath}/cms/articleCate/save")
    @ResponseBody
    public ResultModel save(ArticleCate articleCate){
        if(articleCate == null){
            throw new ParamException();
        }
        if(StringUtils.isEmpty(articleCate.getName())){
            throw new ParamException(Messages.NAME_NOT_EMPTY);
        }
        return new ResultModel(articleCateService.save(articleCate));
    }

    @RequestMapping("${rsblog.managePath}/cms/articleCate/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model){
        ArticleCate articleCate = articleCateService.findById(id);
        model.addAttribute("articleCate",articleCate);
        return MANAGE_FTL_PATH + "/edits";
    }

    @RequestMapping("${rsblog.managePath}/cms/articleCate/update")
    @ResponseBody
    public ResultModel update(ArticleCate articleCate){
        if(articleCate == null){
            throw new ParamException();
        }
        if(StringUtils.isEmpty(articleCate.getName())){
            throw new ParamException(Messages.NAME_NOT_EMPTY);
        }
        return new ResultModel(articleCateService.update(articleCate));
    }


    @RequestMapping("${rsblog.managePath}/cms/articleCate/delete/{id}")
    @ResponseBody
    public ResultModel delete(@PathVariable("id") int id){
        return new ResultModel(articleCateService.delete(id));
    }
}
