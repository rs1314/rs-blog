package cn.rs.blog.web.directive;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.bean.weibo.Weibo;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.directive.BaseDirective;
import cn.rs.blog.core.handler.DirectiveHandler;
import cn.rs.blog.service.weibo.IWeiboService;
import freemarker.template.TemplateException;

/**
 * Created by rs
 */
@Component
public class WeiboDirective extends BaseDirective {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private IWeiboService weiboService;
    @Override
    public void execute(DirectiveHandler handler) throws TemplateException, IOException {
        int num = handler.getInteger("num",0);
        String sort = handler.getString("sort","id");
        int day = handler.getInteger("day",0);
        Member loginMember = MemberUtil.getLoginMember(request);
        int loginMemberId = loginMember == null ? 0 : loginMember.getId();
        List<Weibo> list = weiboService.listByCustom(loginMemberId,sort,num,day);
        handler.put("weiboList", list).render();
    }

}
