package cn.rs.blog.web.web.manage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.common.Ads;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.DateFormatUtil;
import cn.rs.blog.service.common.IAdsService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller
@RequestMapping("/${rsblog.managePath}/ads")
@Before(AdminLoginInterceptor.class)
public class AdsController extends BaseController{
    private static final String MANAGE_FTL_PATH = "/manage/ads/";
    @Autowired
    private IAdsService adsService;

    @RequestMapping("/list")
    public String list(Model model){
        Page page = new Page(request);
        ResultModel resultModel = adsService.listByPage(page);
        model.addAttribute("model", resultModel);
        return MANAGE_FTL_PATH + "lists";
    }

    @RequestMapping("/add")
    public String add(){
        return MANAGE_FTL_PATH + "adds";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultModel save(Ads ads){
        String startTimeStr = getParam("startDateTime");
        String endTimeStr = getParam("endDateTime");
        ads.setStartTime(DateFormatUtil.formatDateTime(startTimeStr));
        ads.setEndTime(DateFormatUtil.formatDateTime(endTimeStr));
        ads.setContent(ads.getContent().replace("&lt;","<").replace("&gt;",">").replace("&#47;","/"));
        return new ResultModel(adsService.save(ads));
    }


    @RequestMapping("/edit/{id}")
    public String edit(Model model, @PathVariable("id") Integer id){
        Ads ads = adsService.findById(id);
        model.addAttribute("ads",ads);
        return MANAGE_FTL_PATH + "edits";
    }

    @RequestMapping("/update")
    @ResponseBody
    public ResultModel update(Ads ads){
        String startTimeStr = getParam("startDateTime");
        String endTimeStr = getParam("endDateTime");
        ads.setStartTime(DateFormatUtil.formatDateTime(startTimeStr));
        ads.setEndTime(DateFormatUtil.formatDateTime(endTimeStr));
        ads.setContent(ads.getContent().replace("&lt;","<").replace("&gt;",">").replace("&#47;","/"));
        return new ResultModel(adsService.update(ads));
    }

    @RequestMapping("/delete/{id}")
    @ResponseBody
    public ResultModel delete(@PathVariable("id") Integer id){
        return new ResultModel(adsService.delete(id));
    }

    @RequestMapping("/enable/{id}")
    @ResponseBody
    public ResultModel enable(@PathVariable("id") Integer id){
        return new ResultModel(adsService.enable(id));
    }


}
