package cn.rs.blog.web.web.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.group.Group;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.service.group.IGroupService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller("manageGroupController")
@RequestMapping("/")
@Before(AdminLoginInterceptor.class)
public class GroupController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/group/";
    @Autowired
    private IGroupService groupService;

    @RequestMapping(value = "${rsblog.managePath}/group/index")
    public String index(@RequestParam(value = "status",required = false,defaultValue = "-1") Integer status,
                        String key,
                        Model model) {
        Page page = new Page(request);
        List<Group> list = groupService.list(status,key);
        model.addAttribute("list",list);
        model.addAttribute("key",key);
        return MANAGE_FTL_PATH + "indexs";
    }

    @RequestMapping(value = "${rsblog.managePath}/group/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel delete(@PathVariable("id") int id){
        Member loginMember = MemberUtil.getLoginMember(request);
        return new ResultModel(groupService.delete(loginMember,id));
    }

    @RequestMapping(value = "${rsblog.managePath}/group/changeStatus/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel changeStatus(@PathVariable("id") int id){
        return new ResultModel(groupService.changeStatus(id));
    }



}
