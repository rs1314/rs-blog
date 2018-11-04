package cn.rs.blog.web.directive;

import java.io.IOException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rs.blog.bean.common.Ads;
import cn.rs.blog.core.directive.BaseDirective;
import cn.rs.blog.core.handler.DirectiveHandler;
import cn.rs.blog.service.common.IAdsService;
import freemarker.template.TemplateException;

/**
 * Created by rs
 */
@Component
public class AdsDirective extends BaseDirective {

    @Autowired
    private IAdsService adsService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        int id = handler.getInteger("id",0);
        Ads ads = adsService.findById(id);
        handler.put("ad", ads).render();
    }

}
