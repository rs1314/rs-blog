package cn.rs.blog.web.web.manage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.system.Tag;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.service.system.ITagService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller
@RequestMapping("/${rsblog.managePath}/tag")
@Before(AdminLoginInterceptor.class)
public class TagController extends BaseController{
    private static final String MANAGE_FTL_PATH = "/manage/tag/";
    @Autowired
    private ITagService tagService;

    @RequestMapping("/list/{funcType}")
    public String list(Model model,@PathVariable("funcType") Integer funcType){
        Page page = new Page(request);
        ResultModel resultModel = tagService.listByPage(page,funcType);
        model.addAttribute("funcType",funcType);
        model.addAttribute("model", resultModel);
        return MANAGE_FTL_PATH + "lists";
    }

    @RequestMapping("/add/{funcType}")
    public String add(Model model,@PathVariable("funcType") Integer funcType){
        model.addAttribute("funcType",funcType);
        return MANAGE_FTL_PATH + "adds";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultModel save(Tag tag){
        return new ResultModel(tagService.save(tag));
    }


    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        Tag tag = tagService.findById(id);
        model.addAttribute("tag",tag);
        return MANAGE_FTL_PATH + "edits";
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultModel update(Tag tag){
        ValidUtill.checkIsNull(tag);
        return new ResultModel(tagService.update(tag));
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ResultModel delete(@PathVariable("id") Integer id){
        return new ResultModel(tagService.delete(id));
    }

}
