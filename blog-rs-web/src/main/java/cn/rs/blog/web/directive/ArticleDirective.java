package cn.rs.blog.web.directive;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rs.blog.bean.cms.Article;
import cn.rs.blog.core.directive.BaseDirective;
import cn.rs.blog.core.handler.DirectiveHandler;
import cn.rs.blog.service.cms.IArticleService;
import freemarker.template.TemplateException;

/**
 * Created by rs
 */
@Component
public class ArticleDirective extends BaseDirective {
    @Autowired
    private IArticleService articleService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        int cid = handler.getInteger("cid",0);
        int num = handler.getInteger("num",0);
        String sort = handler.getString("sort","id");
        int day = handler.getInteger("day",0);
        int thumbnail = handler.getInteger("thumbnail",0);
        List<Article> list = articleService.listByCustom(cid,sort,num,day,thumbnail);
        handler.put("articleList", list).render();
    }

}
