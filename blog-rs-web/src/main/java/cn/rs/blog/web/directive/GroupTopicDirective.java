package cn.rs.blog.web.directive;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rs.blog.bean.group.GroupTopic;
import cn.rs.blog.core.directive.BaseDirective;
import cn.rs.blog.core.handler.DirectiveHandler;
import cn.rs.blog.service.group.IGroupTopicService;
import freemarker.template.TemplateException;

/**
 * Created by rs
 */
@Component
public class GroupTopicDirective extends BaseDirective {
    @Autowired
    private IGroupTopicService groupTopicService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        int gid = handler.getInteger("gid",0);
        int num = handler.getInteger("num",0);
        String sort = handler.getString("sort","id");
        int day = handler.getInteger("day",0);
        int thumbnail = handler.getInteger("thumbnail",0);
        List<GroupTopic> list = groupTopicService.listByCustom(gid,sort,num,day,thumbnail);
        handler.put("groupTopicList", list).render();
    }

}
