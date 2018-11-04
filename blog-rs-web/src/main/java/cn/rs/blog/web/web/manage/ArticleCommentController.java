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

import cn.rs.blog.bean.cms.ArticleComment;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.service.cms.IArticleCommentService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * Created by rs
 */
@Controller("manageArticleCommentController")
@RequestMapping("${rsblog.managePath}/cms/comment")
@Before(AdminLoginInterceptor.class)
public class ArticleCommentController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/cms/comment/";
    @Autowired
    private IArticleCommentService articleCommentService;

    @RequestMapping("/list")
    public String list(String key, @RequestParam(value = "articleId",defaultValue = "0",required = false) Integer articleId, Model model) {
        Page page = new Page(request);
        List<ArticleComment> list = articleCommentService.listByPage(page, articleId, key);
        ResultModel resultModel = new ResultModel(0, page);
        resultModel.setData(list);
        model.addAttribute("model", resultModel);
        model.addAttribute("key",key);
        return MANAGE_FTL_PATH + "list";
    }


    @RequestMapping(value = "/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel delete(@PathVariable("id") Integer id){
        Member loginMember = MemberUtil.getLoginMember(request);
        return new ResultModel(articleCommentService.delete(loginMember,id));
    }

}
