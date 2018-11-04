package cn.rs.blog.web.web.manage;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller("manageMemberController")
@RequestMapping("/")
@Before(AdminLoginInterceptor.class)
public class MemberController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/member/";
    @Autowired
    private IMemberService memberService;

    @RequestMapping("${rsblog.managePath}/member/index")
    @Before(AdminLoginInterceptor.class)
    public String index(String key,Model model) {
        Page page = new Page(request);
        ResultModel resultModel = memberService.listByPage(page,key);
        model.addAttribute("model", resultModel);
        model.addAttribute("key",key);
        return MANAGE_FTL_PATH + "index";
    }


    /**
     * 会员启用、禁用操作
     */
    @RequestMapping(value = "${rsblog.managePath}/member/isenable/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel isenable(@PathVariable("id") int id) {
        return memberService.isenable(id);
    }

    @RequestMapping(value = "${rsblog.managePath}/member/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel delete(@PathVariable("id") int id) {
        return memberService.delete(id);
    }

    @RequestMapping(value = "${rsblog.managePath}/member/changepwd/{id}", method = RequestMethod.GET)
    public String changepwd(@PathVariable("id") int id, Model model) {
        Member member = memberService.findById(id);
        if (member == null) {
            return errorModel(model, "会员不存在");
        }
        model.addAttribute("member", member);
        return MANAGE_FTL_PATH + "changepwd";
    }

    @RequestMapping(value = "${rsblog.managePath}/member/changepwd", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel changepwd(int id, String password) {
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.changepwd(loginMember,id, password);
    }






    /**
     * 管理员列表
     * @param key
     * @param model
     * @return
     */
    @RequestMapping(value = "${rsblog.managePath}/member/managerList",method = RequestMethod.GET)
    public String managerList(String key,Model model) {
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember.getIsAdmin() == 1){
            return errorModel(model, "没有权限");
        }
        Page page = new Page(request);
        ResultModel resultModel = memberService.managerList(page,key);
        model.addAttribute("model", resultModel);
        model.addAttribute("key",key);
        return MANAGE_FTL_PATH + "managerList";
    }

    /**
     * 授权管理员
     * @param model
     * @return
     */
    @RequestMapping(value = "${rsblog.managePath}/member/managerAdd",method = RequestMethod.GET)
    public String managerAdd(Model model) {
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember.getIsAdmin() == 1){
            return errorModel(model, "没有权限");
        }
        return MANAGE_FTL_PATH + "managerAdd";
    }

    /**
     * 授权管理员
     * @param name
     * @return
     */
    @RequestMapping(value = "${rsblog.managePath}/member/managerAdd",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel managerAdd(String name) {
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember.getId() != 1 && loginMember.getIsAdmin() == 1){
            return new ResultModel(-1,"没有权限");
        }
        //管理员授权，只能授权普通管理员
        return memberService.managerAdd(loginMember, name);
    }

    /**
     * 取消管理员
     * @param id
     * @return
     */
    @RequestMapping(value = "${rsblog.managePath}/member/managerCancel/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel managerCancel(@PathVariable("id") Integer id) {
        if(id == null){
            return new ResultModel(-1,"参数错误");
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember.getIsAdmin() == 1){
            return new ResultModel(-1,"没有权限");
        }
        return memberService.managerCancel(loginMember, id);
    }
}
