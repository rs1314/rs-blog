package cn.rs.blog.web.web.front;

import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.system.ActionLog;
import cn.rs.blog.commoms.utils.ConfigUtil;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.exception.ParamException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.ErrorUtil;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.member.IMessageService;
import cn.rs.blog.service.system.IActionLogService;
import cn.rs.blog.service.system.IConfigService;
import cn.rs.blog.web.interceptor.UserLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller("memberIndexController")
@RequestMapping("/member")
public class MemberController extends BaseController {
    private static final String MEMBER_FTL_PATH = "/member/";
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IConfigService configService;
    @Autowired
    private IActionLogService actionLogService;
    @Autowired
    private IMessageService messageService;
    @Autowired
    private RsBlogConfig rsBlogConfig;

    @GetMapping(value = "/login")
    public String login(Model model, @RequestParam(value = "redirectUrl",required = false,defaultValue = "") String redirectUrl){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember != null){
            return "redirect:/member/";
        }
       // model.addAttribute("redirectUrl",redirectUrl);

        //return MEMBER_FTL_PATH + "/login";
        return   "member/logins";
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public ResultModel<Member> loginpost(Member member, @RequestParam(value = "redirectUrl",required = false,defaultValue = "") String redirectUrl){
        ResultModel resultModel = new ResultModel(memberService.login(member,request));
        resultModel.setCode(3);
        if (StringUtils.isNotEmpty(redirectUrl) && resultModel.getCode() >= 0){
            resultModel.setUrl(redirectUrl);
        }else {
            resultModel.setUrl(request.getContextPath() + "/member/");
        }
        return resultModel;
    }

    @GetMapping(value = "/register")
    public String register(){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember != null){
            return "redirect:/member/";
        }
       // return MEMBER_FTL_PATH + "/register";
        return   "member/registers";
    }

    @PostMapping(value = "/register")
    @ResponseBody
    public ResultModel registerpost(Member member, String repassword){
        Map<String,String> config = configService.getConfigToMap();
        if("0".equals(config.get(ConfigUtil.MEMBER_REGISTER_OPEN))){
            return new ResultModel(-1,"注册功能已关闭");
        }
        if(member == null){
            throw new ParamException();
        }
        if(member.getName().length() < 6){
            return new ResultModel(-1,"用户名长度最少6位");
        }
        if(!StringUtils.checkNickname(member.getName())){
            return new ResultModel(-1,"用户名只能由中文、字母、数字、下划线(_)或者短横线(-)组成");
        }
        if(!StringUtils.isEmail(member.getEmail())){
            return new ResultModel(-1,"邮箱格式错误");
        }
        if(member.getPassword().length() < 6){
            return new ResultModel(-1,"密码长度最少6位");
        }
        if(!member.getPassword().equals(repassword)){
            return new ResultModel(-1,"两次密码输入不一致");
        }
        return memberService.register(member,request);
    }

    @GetMapping(value = "/active")
    @Before(UserLoginInterceptor.class)
    public String active(){
        return MEMBER_FTL_PATH + "actives";
    }

    @PostMapping(value = "/active")
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel activepost(String randomCode){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.active(loginMember,randomCode,request);
    }

    @GetMapping(value = "/sendEmailActiveValidCode")
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel sendEmailActiveValidCode(){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.sendEmailActiveValidCode(loginMember, request);
    }

    @GetMapping(value = "/forgetpwdfind")
    public String forgetpwds(){
        return "member/forgetpwdsa";
    }

    @PostMapping(value = "/forgetpwd_nn")
    @ResponseBody
    public ResultModel forgetpwd(String name, String email){
        return memberService.forgetpwd(name, email, request);
    }

    @RequestMapping(value = "/resetpwd",method = RequestMethod.GET)
    public String resetpwd(String email,String token,Model model){
        model.addAttribute("email",email);
        model.addAttribute("token",token);
        return MEMBER_FTL_PATH + "resetpwds";
    }

    @PostMapping(value = "/resetpwd")
    @ResponseBody
    public ResultModel resetpwdpost(String email, String token, String password, String repassword){
        if(StringUtils.isEmpty(password)){
            return new ResultModel(-1,"新密码不能为空");
        }
        if(password.length() < 6){
            return new ResultModel(-1,"密码不能少于6个字符");
        }
        if(!password.equals(repassword)){
            return new ResultModel(-1,"两次密码输入不一致");
        }
        return memberService.resetpwd(email,token,password,request);
    }


    @GetMapping(value = "/")
    @Before(UserLoginInterceptor.class)
    public String index(Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        Page page = new Page(request);
        ResultModel<ActionLog> list = actionLogService.memberActionLog(page,loginMemberId);
        model.addAttribute("actionLogModel",list);
        return MEMBER_FTL_PATH + "indexs";
    }


    @GetMapping(value = "/editInfo")
    @Before(UserLoginInterceptor.class)
    public String editInfo(){
        return MEMBER_FTL_PATH + "editInfos";
    }

    @PostMapping(value = "/editBaseInfo")
    @ResponseBody
    public ResultModel editBaseInfo(String name, String sex, String introduce){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.editBaseInfo(loginMember,name,sex,introduce);
    }

    @PostMapping(value = "/editOtherInfo")
    @ResponseBody
    public ResultModel editOtherInfo(String birthday, String qq, String wechat, String contactPhone,
                                     String contactEmail, String website){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.editOtherInfo(loginMember,birthday,qq,wechat,contactPhone,contactEmail,website);
    }

    @GetMapping(value = "/avatar")
    @Before(UserLoginInterceptor.class)
    public String avatar(){
        return MEMBER_FTL_PATH + "avatars";
    }

    @GetMapping(value = "/password")
    @Before(UserLoginInterceptor.class)
    public String password(){
        return MEMBER_FTL_PATH + "passwords";
    }

    @PostMapping(value = "/password")
    @ResponseBody
    public ResultModel passwordpost(String oldPassword, String newPassword, String renewPassword){
        if(StringUtils.isEmpty(oldPassword)){
            return new ResultModel(-1,"旧密码不能为空");
        }
        if(StringUtils.isEmpty(newPassword)){
            return new ResultModel(-1,"新密码不能为空");
        }
        if(!newPassword.equals(renewPassword)){
            return new ResultModel(-1,"两次密码输入不一致");
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.changepwd(loginMember,oldPassword,newPassword);
    }

    @GetMapping(value = "/logout")
    @Before(UserLoginInterceptor.class)
    public String logout(){
        MemberUtil.setLoginMember(request,null);
        return "redirect:/member/login";
    }

    /**
     * 关注、取消关注
     * @param followWhoId
     * @return
     */
    @GetMapping(value = "/follows/{followWhoId}")
    @ResponseBody
    public ResultModel follows(@PathVariable(value = "followWhoId") Integer followWhoId){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.follows(loginMember,followWhoId);
    }

    /**
     * 查询是否已关注该用户
     * @param followWhoId
     * @return
     */
    @GetMapping(value = "/isFollowed/{followWhoId}")
    @ResponseBody
    public ResultModel isFollowed(@PathVariable(value = "followWhoId") Integer followWhoId){
        Member loginMember = MemberUtil.getLoginMember(request);
        return memberService.isFollowed(loginMember,followWhoId);
    }


    /**
     * 获取该登录会员的收件信息
     * @param memberId
     * @return
     */
    @GetMapping(value = "/message")
    @Before(UserLoginInterceptor.class)
    public String message(@RequestParam(value = "mid",required = false,defaultValue = "-1") Integer memberId,Model model){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        Integer loginMemberId = -1;
        if(loginMember != null){
            loginMemberId = loginMember.getId();
        }
        //获取联系人
//        ResponseModel contactMembers = messageService.listContactMembers(page, loginMemberId);
//        获取联系人
//        ResponseModel contactMembers = messageService.listContactMembers(page, memberId, loginMemberId);
//        model.addAttribute("model", contactMembers);
        return MEMBER_FTL_PATH + "messages";
    }

    /**
     * 获取联系人
     * @return
     */
    @GetMapping(value = "/listContactMembers")
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel listContactMembers(){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        //获取联系人
        ResultModel contactMembers = memberService.listContactMembers(page, loginMember.getId());
        return contactMembers;
    }

    /**
     * 获取聊天记录
     * @param memberId
     * @return
     */
    @GetMapping(value = "/messageRecords/{memberId}")
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel messageRecords(@PathVariable("memberId") Integer memberId){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        ResultModel messageRecords = messageService.messageRecords(page, memberId, loginMember.getId());
        return messageRecords;
    }

    /**
     * 发送信息窗口
     * @param memberId
     * @param model
     * @return
     */
    @GetMapping(value = "/sendMessageBox")
    @Before(UserLoginInterceptor.class)
    public String sendMessageBox(@RequestParam(value = "mid") Integer memberId,Model model){
        if(memberId == null){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model, -1000, Const.INDEX_ERROR_FTL_PATH);
        }
        Member findMember= memberService.findById(memberId);
        if(findMember == null){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model, -1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member", findMember);
        return MEMBER_FTL_PATH + "sendMessageBoxs";
    }

    /**
     * 发送信息
     * @param content
     * @param memberId
     * @return
     */
    @PostMapping(value = "/sendMessage")
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel sendMessage(String content,Integer memberId){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(memberId == null){
            return new ResultModel(-1,"请选择发送对象");
        }
        Member findMember= memberService.findById(memberId);
        if(findMember == null){
            return new ResultModel(-1,"会员不存在");
        }
        return messageService.sentMsg(loginMember.getId(), memberId, content);
    }

    /**
     * 系统信息
     * @param model
     * @return
     */
    @GetMapping(value = "/systemMessage")
    @Before(UserLoginInterceptor.class)
    public String systemMessageget(Model model){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        ResultModel messageModel = messageService.systemMessage(page, loginMember.getId(),request.getContextPath());
        model.addAttribute("messageModel",messageModel);
        return MEMBER_FTL_PATH + "systemMessages";
    }
}
