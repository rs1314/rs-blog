package cn.rs.blog.web.web.manage;

import java.util.Properties;


import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.annotation.Clear;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.service.common.ICommonService;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs.
 */
@Controller("manageIndexController")
@RequestMapping("/${rsblog.managePath}")
@Before(AdminLoginInterceptor.class)
public class IndexController extends BaseController {
    private static final String FTL_PATH = "/manage";
    @Autowired
    private IMemberService memberService;
    @Autowired
    private RsBlogConfig rsBlogConfig;
    @Autowired
    private ICommonService commonService;

    @RequestMapping(value = {"/", "/index"})
    public String index(Model model){
        Properties props = System.getProperties();
        //java版本
        String javaVersion = props.getProperty("java.version");
        //操作系统名称
        String osName = props.getProperty("os.name") + props.getProperty("os.version");
        //用户的主目录
        String userHome = props.getProperty("user.home");
        //用户的当前工作目录
        String userDir = props.getProperty("user.dir");
        //服务器IP
        String serverIP = request.getLocalAddr();
        //客户端IP
        String clientIP = request.getRemoteHost();
        //WEB服务器
        String webVersion = request.getServletContext().getServerInfo();
        //CPU个数
        String cpu = Runtime.getRuntime().availableProcessors() + "核";
        //虚拟机内存总量
        String totalMemory = (Runtime.getRuntime().totalMemory()/1024/1024) + "M";
        //虚拟机空闲内存量
        String freeMemory = (Runtime.getRuntime().freeMemory()/1024/1024) + "M";
        //虚拟机使用的最大内存量
        String maxMemory = (Runtime.getRuntime().maxMemory()/1024/1024) + "M";
        //MYSQL版本
        String mysqlVersion = commonService.getMysqlVsesion();
        //网站根目录
        String webRootPath = request.getSession().getServletContext().getRealPath("");
        model.addAttribute("javaVersion",javaVersion);
        model.addAttribute("osName",osName);
        model.addAttribute("userHome",userHome);
        model.addAttribute("userDir",userDir);
        model.addAttribute("clientIP",clientIP);
        model.addAttribute("serverIP",serverIP);
        model.addAttribute("cpu",cpu);
        model.addAttribute("totalMemory",totalMemory);
        model.addAttribute("freeMemory",freeMemory);
        model.addAttribute("maxMemory",maxMemory);
        model.addAttribute("webVersion",webVersion);
        model.addAttribute("mysqlVersion",mysqlVersion);
        model.addAttribute("webRootPath",webRootPath);
        model.addAttribute("systemVersion", Const.SYSTEM_VERSION);
        model.addAttribute("systemName",Const.SYSTEM_NAME);
        model.addAttribute("systemUpdateTime",Const.SYSTEM_UPDATE_TIME);
        return FTL_PATH + "/indexs";
    }

    /**
     * 登录页面
     * @return
     */
    @Clear()
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String login(){
        return FTL_PATH + "/logins";
    }

    /**
     * 提交登录信息
     * @param member
     * @return
     */
    @Clear()
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel login(Member member){
        if(member == null){
            return new ResultModel(-1,"参数错误");
        }
        if(StringUtils.isEmpty(member.getName())){
            return new ResultModel(-1,"用户名不能为空");
        }
        if(StringUtils.isEmpty(member.getPassword())){
            return new ResultModel(-1,"密码不能为空");
        }
        Member loginMember = memberService.manageLogin(member,request);
        if(loginMember != null){
            MemberUtil.setLoginMember(request,loginMember);
            return new ResultModel(2,"登录成功","index");
        }else {
            return new ResultModel(-1,"用户名或密码错误");
        }
    }
}
