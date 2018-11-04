package cn.rs.blog.web.web.manage;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.rs.blog.bean.member.Checkin;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.service.member.ICheckinService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * 签到
 * Created rs
 */
@Controller("manageCheckinController")
@RequestMapping("/${rsblog.managePath}/checkin/")
@Before(AdminLoginInterceptor.class)
public class CheckinController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/checkin/";
    @Autowired
    private ICheckinService checkinService;

    @RequestMapping("list")
    public String list(Model model){
        Page page = new Page<Checkin>(request);
        List<Checkin> list = checkinService.list(page, 0);
        ResultModel resultModel = new ResultModel(0, page);
        resultModel.setData(list);
        model.addAttribute("model",resultModel);
        return MANAGE_FTL_PATH + "list";
    }
}
