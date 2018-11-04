package cn.rs.blog.web.web.manage;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.consts.AppTag;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.service.picture.IPictureService;
import cn.rs.blog.service.system.ITagService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 *
 * @author rs
 */
@Controller
@RequestMapping("/${rsblog.managePath}/picture")
@Before(AdminLoginInterceptor.class)
public class PictureController extends BaseController{
    private static final String MANAGE_FTL_PATH = "/manage/picture/";
    @Autowired
    private IPictureService pictureService;
    @Autowired
    private ITagService tagService;

    @RequestMapping("/tagList")
    public String tagList(Model model){
        Page page = new Page(request);
        ResultModel resultModel = tagService.listByPage(page, AppTag.PICTURE);
        model.addAttribute("model", resultModel);
        return MANAGE_FTL_PATH + "tagList";
    }


    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model){
        Page page = new Page(request);
        ResultModel resultModel = pictureService.listByPage(page,0);
        model.addAttribute("model", resultModel);
        return MANAGE_FTL_PATH + "list";
    }

    @RequestMapping(value = "/delete/{pictureId}",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel delete(@PathVariable("pictureId") Integer pictureId){
        return new ResultModel(pictureService.delete(request,pictureId));
    }

}
