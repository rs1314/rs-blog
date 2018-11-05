package cn.rs.blog.web.web.front;

import java.io.IOException;
import java.io.PrintWriter;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.system.ActionLog;
import cn.rs.blog.commoms.utils.EmojiUtil;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.ErrorUtil;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.service.cms.IArticleService;
import cn.rs.blog.service.common.IArchiveService;
import cn.rs.blog.service.common.ILinkService;
import cn.rs.blog.service.group.IGroupFansService;
import cn.rs.blog.service.group.IGroupService;
import cn.rs.blog.service.group.IGroupTopicService;
import cn.rs.blog.service.member.IMemberFansService;
import cn.rs.blog.service.member.IMemberService;
import cn.rs.blog.service.system.IActionLogService;
import cn.rs.blog.service.weibo.IWeiboService;
import cn.rs.blog.web.web.common.BaseController;
import net.sf.json.JSONObject;

/**
 * Created by rs
 */
@Controller("indexController")
@RequestMapping("/")
public class IndexController extends BaseController{
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IGroupTopicService groupTopicService;
    @Autowired
    private IGroupService groupService;
    @Autowired
    private IWeiboService weiboService;
    @Autowired
    private IMemberService memberService;
    @Autowired
    private IArchiveService archiveService;
    @Autowired
    private IActionLogService actionLogService;
    @Autowired
    private RsBlogConfig rsBlogConfig;
    @Autowired
    private IGroupFansService groupFansService;
    @Autowired
    private IMemberFansService memberFansService;
    @Autowired
    private ILinkService linkService;

    @RequestMapping(value={"/", "index"},method = RequestMethod.GET)
    public String index(@RequestParam(value = "key",required = false,defaultValue = "") String key, Integer cateid,Model model) {
        Page page = new Page(request);
        if(cateid == null){
            cateid = 0;
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        ResultModel linkModel = linkService.recommentList();
        page.setPageSize(50);
        model.addAttribute("linkModel",linkModel);

        return rsBlogConfig.getFrontTemplate() + "/index";
    }

    @RequestMapping(value = "u/{id}",method = RequestMethod.GET)
    public String u(@PathVariable("id") Integer id, Model model){
        Page page = new Page(request);
        Member member = memberService.findById(id);
        if(member == null){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member",member);
        Member loginMember = MemberUtil.getLoginMember(request);
        model.addAttribute("loginMember", loginMember);
        ResultModel<ActionLog> list = actionLogService.memberActionLog(page,id);
        model.addAttribute("actionLogModel",list);
        return rsBlogConfig.getFrontTemplate() + "/us";
    }

    @RequestMapping(value = "u/{id}/home/{type}",method = RequestMethod.GET)
    public String home(@PathVariable("id") Integer id, @PathVariable("type") String type, Model model){
        Page page = new Page(request);
        Member member = memberService.findById(id);
        if(member == null){
            return rsBlogConfig.getFrontTemplate() + ErrorUtil.error(model,-1005, Const.INDEX_ERROR_FTL_PATH);
        }
        model.addAttribute("member",member);

        Member loginMember = MemberUtil.getLoginMember(request);
        model.addAttribute("loginMember", loginMember);
        int loginMemberId = 0;
        if(loginMember != null){
            loginMemberId = loginMember.getId().intValue();
        }
        if("article".equals(type)){
            model.addAttribute("model", articleService.listByPage(page,"",0,1, id));
        } else if("groupTopic".equals(type)){
            model.addAttribute("model", groupTopicService.listByPage(page,"",0,1, id,0));
        } else if("group".equals(type)){
            model.addAttribute("model", groupFansService.listByMember(page, id));
        } else if("weibo".equals(type)){
            model.addAttribute("model", weiboService.listByPage(page,id,loginMemberId,""));
        } else if("follows".equals(type)){
            model.addAttribute("model", memberFansService.followsList(page,id));
        } else if("fans".equals(type)){
            model.addAttribute("model", memberFansService.fansList(page,id));
        }
        model.addAttribute("type",type);
        return rsBlogConfig.getFrontTemplate() + "/homes";
    }


    @RequestMapping(value = "newVersion",method = RequestMethod.GET)
    public void newVersion(@RequestParam("callback") String callback){
        response.setCharacterEncoding("utf-8");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("LAST_SYSTEM_VERSION",Const.LAST_SYSTEM_VERSION);
        jsonObject.put("LAST_SYSTEM_UPDATE_TIME", Const.LAST_SYSTEM_UPDATE_TIME);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.print(callback + "(" + jsonObject.toString() + ")");
        out.flush();
        out.close();
    }
    /**
     * 获取Emoji数据
     * @return
     */
    @RequestMapping(value="/emoji/emojiJsonData.json",method = RequestMethod.GET)
    @ResponseBody
    public Object emojiJsonData(){
        return EmojiUtil.emojiJson();
    }

    @RequestMapping(value="/404",method = RequestMethod.GET)
    public String rs404(){
        return rsBlogConfig.getFrontTemplate() + "/common/404";
    }

    @RequestMapping(value="/error",method = RequestMethod.GET)
    public String error(String msg){
        if (StringUtils.isNotBlank(msg)){
            request.setAttribute("msg", msg);
        }
        return rsBlogConfig.getFrontTemplate() + "/common/error";
    }

    /**
     * 友情链接
     * @param model
     * @return
     */
    @RequestMapping(value={"/link"},method = RequestMethod.GET)
    public String link(Model model) {
        ResultModel linkModel = linkService.allList();
        model.addAttribute("linkModel",linkModel);
        return rsBlogConfig.getFrontTemplate() + "/links";
    }
}
