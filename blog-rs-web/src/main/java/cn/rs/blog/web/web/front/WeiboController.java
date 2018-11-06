package cn.rs.blog.web.web.front;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.weibo.Weibo;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.commoms.utils.ValidUtill;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.exception.NotFountException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.service.weibo.IWeiboCommentService;
import cn.rs.blog.service.weibo.IWeiboService;
import cn.rs.blog.web.interceptor.UserLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by rs
 */
@Controller("frontWeiboController")
@RequestMapping("/${rsblog.weiboPath}")
public class WeiboController extends BaseController {
    @Autowired
    private IWeiboService weiboService;
    @Autowired
    private IWeiboCommentService weiboCommentService;
    @Autowired
    private RsBlogConfig rsBlogConfig;

    @RequestMapping(value = "/publish",method = RequestMethod.POST)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel publish(String content, String pictures,String md5){
        Member loginMember = MemberUtil.getLoginMember(request);
        return new ResultModel(weiboService.save(request, loginMember,content, pictures,md5));
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(@RequestParam(value = "key",required = false,defaultValue = "") String key, Model model){
        Page page = new Page(request);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        ResultModel resultModel = weiboService.listByPage(page,0,loginMemberId,key);
        model.addAttribute("model", resultModel);
        List<Weibo> hotList = weiboService.hotList(loginMemberId);
        model.addAttribute("hotList",hotList);
        model.addAttribute("loginUser", loginMember);
        return rsBlogConfig.getFrontTemplate() + "/weibo/lists";
    }

    @RequestMapping(value = "/detail/{weiboId}",method = RequestMethod.GET)
    public String detail(@PathVariable("weiboId") Integer weiboId, Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        Weibo weibo = weiboService.findById(weiboId,loginMemberId);
        if (weibo == null){
            throw new NotFountException(Messages.WEIBO_NOT_EXISTS);
        }
        model.addAttribute("weibo",weibo);
        model.addAttribute("loginUser", loginMember);
        return rsBlogConfig.getFrontTemplate() + "/weibo/details";
    }

    @RequestMapping(value="/delete/{weiboId}",method = RequestMethod.GET)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel delete(@PathVariable("weiboId") Integer weiboId){
        Member loginMember = MemberUtil.getLoginMember(request);
        boolean result = weiboService.userDelete(request, loginMember,weiboId);
        ResultModel resultModel = new ResultModel(result);
        if(resultModel.getCode() >= 0){
            resultModel.setCode(2);
            resultModel.setUrl(Const.WEIBO_PATH + "list");
        }
        return resultModel;
    }


    @RequestMapping(value="/comment/{weiboId}",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel comment(@PathVariable("weiboId") Integer weiboId, String content, Integer weiboCommentId){
        Member loginMember = MemberUtil.getLoginMember(request);
        ValidUtill.checkLogin(loginMember);
        return new ResultModel(weiboCommentService.save(loginMember,content,weiboId,weiboCommentId));
    }

    @RequestMapping(value="/commentList/{weiboId}.json",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel commentList(@PathVariable("weiboId") Integer weiboId){
        Page page = new Page(request);
        return weiboCommentService.listByWeibo(page,weiboId);
    }

    @RequestMapping(value="/favor/{weiboId}",method = RequestMethod.GET)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel favor(@PathVariable("weiboId") Integer weiboId){
        Member loginMember = MemberUtil.getLoginMember(request);
        ResultModel resultModel = weiboService.favor(loginMember,weiboId);
        return resultModel;
    }
}
