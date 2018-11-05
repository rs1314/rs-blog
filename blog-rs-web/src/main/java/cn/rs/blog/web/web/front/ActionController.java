package cn.rs.blog.web.web.front;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.rs.blog.bean.system.ActionLog;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.model.Page;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.service.system.IActionLogService;
import cn.rs.blog.web.web.common.BaseController;

/**
 * 动态
 * Created by rs
 */
@Controller("frontActionController")
@RequestMapping("/action/")
public class ActionController extends BaseController {
    @Autowired
    private IActionLogService actionLogService;
    @Autowired
    private RsBlogConfig rsBlogConfig;

    @RequestMapping("list")
    public String list(Model model){
        Page page = new Page(request);
        ResultModel<ActionLog> actionList = actionLogService.memberActionLog(page,0);
        model.addAttribute("model", actionList);
        return rsBlogConfig.getFrontTemplate() + "/action/lists";
    }


}
