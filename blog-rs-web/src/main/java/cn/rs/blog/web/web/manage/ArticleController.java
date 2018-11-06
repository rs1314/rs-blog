package cn.rs.blog.web.web.manage;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.cms.Article;
import cn.rs.blog.bean.cms.ArticleCate;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.service.cms.IArticleCateService;
import cn.rs.blog.service.cms.IArticleService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller("manageArticleController")
@RequestMapping("/")
@Before(AdminLoginInterceptor.class)
public class ArticleController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/cms/article/";
    @Autowired
    private IArticleCateService articleCateService;
    @Autowired
    private IArticleService articleService;

    @RequestMapping("${rsblog.managePath}/cms/index")
    @Before(AdminLoginInterceptor.class)
    public String index(String key, @RequestParam(value = "cateid",defaultValue = "0",required = false) Integer cateid,
    @RequestParam(value = "status",defaultValue = "2",required = false) Integer status,
    @RequestParam(value = "memberId",defaultValue = "0",required = false) Integer memberId, Model model) {
        List<ArticleCate> cateList = articleCateService.list();
        Page page = new Page(request);
        ResultModel resultModel = articleService.listByPage(page,key,cateid,status,memberId);
        model.addAttribute("model", resultModel);
        model.addAttribute("cateList",cateList);
        model.addAttribute("key",key);
        model.addAttribute("cateid",cateid);
        return MANAGE_FTL_PATH + "indexs";
    }

    @RequestMapping(value="${rsblog.managePath}/cms/article/add",method = RequestMethod.GET)
    public String add(Model model) {
        List<ArticleCate> cateList = articleCateService.list();
        model.addAttribute("cateList",cateList);
        return MANAGE_FTL_PATH + "adds";
    }

    @RequestMapping(value="${rsblog.managePath}/cms/article/save",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel save(@Valid Article article, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResultModel(-1,getErrorMessages(bindingResult));
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        return new ResultModel(articleService.save(loginMember,article));
    }

    @RequestMapping(value="${rsblog.managePath}/cms/article/list",method = RequestMethod.GET)
    public String list(String key, @RequestParam(value = "cateid",defaultValue = "0",required = false) Integer cateid,
                       @RequestParam(value = "status",defaultValue = "2",required = false) Integer status,
                       @RequestParam(value = "memberId",defaultValue = "0",required = false) Integer memberId, Model model) {
        Page page = new Page(request);
        ResultModel resultModel = articleService.listByPage(page,key,cateid,status,memberId);
        model.addAttribute("model", resultModel);
        return MANAGE_FTL_PATH + "list";
    }

    @RequestMapping(value="${rsblog.managePath}/cms/article/edit/{id}",method = RequestMethod.GET)
    public String edit(@PathVariable("id") Integer id, Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        List<ArticleCate> cateList = articleCateService.list();
        model.addAttribute("cateList",cateList);
        Article article = articleService.findById(id,loginMember);
        model.addAttribute("article",article);
        return MANAGE_FTL_PATH + "edits";
    }

    @RequestMapping(value="${rsblog.managePath}/cms/article/update",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel update(@Valid Article article,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            new ResultModel(-1,getErrorMessages(bindingResult));
        }
        if(article.getId() == null){
            return new ResultModel(-2);
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        return new ResultModel(articleService.update(loginMember,article));
    }


    @RequestMapping(value = "${rsblog.managePath}/cms/article/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel delete(@PathVariable("id") Integer id){
        Member loginMember = MemberUtil.getLoginMember(request);
        return new ResultModel(articleService.delete(loginMember,id));
    }

    @RequestMapping(value = "${rsblog.managePath}/cms/article/audit/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel audit(@PathVariable("id") Integer id){
        return new ResultModel(articleService.audit(id));
    }

}
