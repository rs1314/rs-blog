package cn.rs.blog.web.web.front;

import java.io.UnsupportedEncodingException;
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
import cn.rs.blog.bean.cms.ArticleComment;
import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.enums.Messages;
import cn.rs.blog.core.exception.NotFountException;
import cn.rs.blog.core.exception.ParamException;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.core.utils.RedirectUrlUtil;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.service.cms.IArticleCateService;
import cn.rs.blog.service.cms.IArticleCommentService;
import cn.rs.blog.service.cms.IArticleService;
import cn.rs.blog.service.common.IArchiveService;
import cn.rs.blog.web.interceptor.UserLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * 前台文章Controller
 * Created by rs
 */
@Controller("frontArticleController")
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    private RsBlogConfig rsBlogConfig;
    @Autowired
    private IArticleCateService articleCateService;
    @Autowired
    private IArticleService articleService;
    @Autowired
    private IArchiveService archiveService;
    @Autowired
    private IArticleCommentService articleCommentService;

    @RequestMapping(value="/list",method = RequestMethod.GET)
    public String list(String key, @RequestParam(value = "cid",defaultValue = "0",required = false) Integer cid,
                       @RequestParam(value = "memberId",defaultValue = "0",required = false) Integer memberId, Model model) {
        if (StringUtils.isNotEmpty(key)){
            try {
                key = new String(key.getBytes("iso-8859-1"),"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        Page page = new Page(request);
        //status 1是审核的文章  0不是审核的文章
        ResultModel resultModel = articleService.listByPage(page,key,cid,1,memberId);
        model.addAttribute("model", resultModel);
        List<ArticleCate> articleCateList = articleCateService.list();
        model.addAttribute("articleCateList",articleCateList);
        ArticleCate articleCate = articleCateService.findById(cid);
        model.addAttribute("articleCate",articleCate);
        return rsBlogConfig.getFrontTemplate() + "/cms/lists";
    }

    @RequestMapping(value="/detail/{id}",method = RequestMethod.GET)
    public String detail(@PathVariable("id") Integer id, Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        Article article = articleService.findById(id,loginMember);
        //文章不存在或者访问未审核的文章，跳到错误页面，提示文章不存在
        if(article == null || article.getStatus() == 0){
            throw new NotFountException(Messages.ARTICLE_NOT_EXISTS);
        }
        //更新文章访问次数
        archiveService.updateViewCount(article.getArchiveId());
        model.addAttribute("article",article);
        List<ArticleCate> articleCateList = articleCateService.list();
        model.addAttribute("articleCateList",articleCateList);
        model.addAttribute("loginUser",loginMember);
        return rsBlogConfig.getFrontTemplate() + "/cms/details";
    }

    @RequestMapping(value="/add",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String add(Model model) {
        List<ArticleCate> cateList = articleCateService.list();
        model.addAttribute("cateList",cateList);
        String judgeLoginJump = MemberUtil.judgeLoginJump(request, RedirectUrlUtil.ARTICLE_ADD);
        if(StringUtils.isNotEmpty(judgeLoginJump)){
            return judgeLoginJump;
        }
        return rsBlogConfig.getFrontTemplate() + "/cms/adds";
    }

    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel save(@Valid Article article, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return new ResultModel(-1,getErrorMessages(bindingResult));
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        ResultModel resultModel = new ResultModel(articleService.save(loginMember,article));
        if(resultModel.getCode() == 0){
            resultModel.setCode(2);
            //文章需要审核就跳转到列表页面
            if(article.getStatus() == 0){
                resultModel.setMessage("文章发布成功，请等待审核");
                resultModel.setUrl(request.getContextPath()+"/article/list");
            }else {
                resultModel.setUrl(request.getContextPath()+"/article/details/"+article.getId());
            }
        }
        return resultModel;
    }

    @RequestMapping(value="/edit/{id}",method = RequestMethod.GET)
    @Before(UserLoginInterceptor.class)
    public String edit(@PathVariable("id") int id, Model model){
        Member loginMember = MemberUtil.getLoginMember(request);
        String judgeLoginJump = MemberUtil.judgeLoginJump(request, RedirectUrlUtil.ARTICLE_EDIT+"/"+id);
        if(StringUtils.isNotEmpty(judgeLoginJump)){
            return judgeLoginJump;
        }
        Article article = articleService.findById(id,loginMember);
        if(article.getMemberId().intValue() != loginMember.getId().intValue()){
            throw new NotFountException(Messages.ARTICLE_NOT_EXISTS);
        }
        model.addAttribute("article",article);
        List<ArticleCate> cateList = articleCateService.list();
        model.addAttribute("cateList",cateList);
        model.addAttribute("loginUser", loginMember);
        return rsBlogConfig.getFrontTemplate() + "/cms/edits";
    }

    @RequestMapping(value="/update",method = RequestMethod.POST)
    @ResponseBody
    public ResultModel update(@Valid Article article,BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            new ResultModel(-1,getErrorMessages(bindingResult));
        }
        if(article.getId() == null){
            return new ResultModel(-2);
        }
        Member loginMember = MemberUtil.getLoginMember(request);
        ResultModel resultModel = new ResultModel(articleService.update(loginMember,article));
        if(resultModel.getCode() == 0){
            resultModel.setCode(2);
            resultModel.setUrl(request.getContextPath() + "/article/details/"+article.getId());
        }
        return resultModel;
    }

    /**
     * 评论文章
     * @param articleId
     * @param content
     * @return
     */
    @RequestMapping(value="/comment/{articleId}",method = RequestMethod.POST)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel comment(@PathVariable("articleId") Integer articleId, String content){
        Member loginMember = MemberUtil.getLoginMember(request);
        return new ResultModel(articleCommentService.save(loginMember,content,articleId));
    }


    @RequestMapping(value="/commentList/{articleId}.json",method = RequestMethod.GET)
    @ResponseBody
    public ResultModel commentList(@PathVariable("articleId") Integer articleId){
        Page page = new Page(request);
        if(articleId == null){
            articleId = 0;
        }
        List<ArticleComment> list = articleCommentService.listByPage(page,articleId, null);
        ResultModel resultModel = new ResultModel(0,page);
        resultModel.setData(list);
        return resultModel;
    }


    @RequestMapping(value="/delete/{id}",method = RequestMethod.GET)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel delete(@PathVariable("id") int id){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(loginMember.getIsAdmin() == 0){
            return new ResultModel(-1,"权限不足");
        }
        ResultModel resultModel = new ResultModel(articleService.delete(loginMember,id));
        if(resultModel.getCode() > 0){
            resultModel.setCode(2);
            resultModel.setUrl(request.getContextPath() + "/article/list");
        }
        return resultModel;
    }


    /**
     * 文章、喜欢
     * @param id
     * @return
     */
    @RequestMapping(value="/favor/{id}",method = RequestMethod.GET)
    @ResponseBody
    @Before(UserLoginInterceptor.class)
    public ResultModel favor(@PathVariable("id") Integer id){
        Member loginMember = MemberUtil.getLoginMember(request);
        if(id == null) {
            throw new ParamException();
        }
        ResultModel resultModel = articleService.favor(loginMember,id);
        return resultModel;
    }
}
