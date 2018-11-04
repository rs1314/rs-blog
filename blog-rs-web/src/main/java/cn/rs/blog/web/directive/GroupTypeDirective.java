package cn.rs.blog.web.directive;

import java.io.IOException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rs.blog.bean.group.GroupType;
import cn.rs.blog.core.directive.BaseDirective;
import cn.rs.blog.core.handler.DirectiveHandler;
import cn.rs.blog.service.group.IGroupTypeService;
import freemarker.template.TemplateException;

/**
 * @author: rs
 */
@Component
public class GroupTypeDirective extends BaseDirective {

    @Autowired
    private IGroupTypeService groupTypeService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        List<GroupType> list = groupTypeService.list();
        handler.put("groupTypeList", list).render();
    }

}
