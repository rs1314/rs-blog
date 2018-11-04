package cn.rs.blog.web.web.front;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.member.Checkin;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.service.member.ICheckinService;
import cn.rs.blog.web.interceptor.UserLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * 签到
 * Created by rs
 */
@Controller
@RequestMapping("/checkin/")
public class CheckinController extends BaseController {
    @Autowired
    private ICheckinService checkinService;
    @Autowired
    private RsBlogConfig rsBlogConfig;

    @RequestMapping({"","index"})
    public String index(Model model){
        Page page = new Page<Checkin>(request);
        List<Checkin> list = checkinService.todayList(page);
        ResultModel resultModel = new ResultModel(0, page);
        resultModel.setData(list);
        model.addAttribute("model",resultModel);
        model.addAttribute("todayContinueList",checkinService.todayContinueList());
        return rsBlogConfig.getFrontTemplate() + "/checkin/index";
    }

    @RequestMapping("save")
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel save(){
        Member member = MemberUtil.getLoginMember(request);
        return new ResultModel<>(checkinService.save(member.getId()));
    }
}
