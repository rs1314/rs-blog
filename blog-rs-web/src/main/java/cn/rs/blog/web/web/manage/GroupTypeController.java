package cn.rs.blog.web.web.manage;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.rs.blog.bean.group.GroupType;
import cn.rs.blog.core.annotation.Before;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.service.group.IGroupTypeService;
import cn.rs.blog.web.interceptor.AdminLoginInterceptor;
import cn.rs.blog.web.web.common.BaseController;

/**
 * @author: rs
 */
@Controller("manageGroupTypeController")
@RequestMapping("/")
@Before(AdminLoginInterceptor.class)
public class GroupTypeController extends BaseController {
    private static final String MANAGE_FTL_PATH = "/manage/group/type/";
    @Autowired
    private IGroupTypeService groupTypeService;

    @RequestMapping(value = "${rsblog.managePath}/group/type/list")
    public String index(Model model) {
        List<GroupType> list = groupTypeService.list();
        model.addAttribute("list", list);
        return MANAGE_FTL_PATH + "lists";
    }

    @RequestMapping(value = "${rsblog.managePath}/group/type/add", method = RequestMethod.GET)
    public Object add() {
        return MANAGE_FTL_PATH + "adds";
    }

    @RequestMapping(value = "${rsblog.managePath}/group/type/save", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel save(GroupType groupType) {
        return new ResultModel(groupTypeService.save(groupType));
    }


    @RequestMapping(value = "${rsblog.managePath}/group/type/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model model) {
        GroupType groupType = groupTypeService.findById(id);
        model.addAttribute("groupType", groupType);
        return MANAGE_FTL_PATH + "edits";
    }

    @RequestMapping(value = "${rsblog.managePath}/group/type/update", method = RequestMethod.POST)
    @ResponseBody
    public ResultModel update(GroupType groupType) {
        return new ResultModel(groupTypeService.update(groupType));
    }


    @RequestMapping(value = "${rsblog.managePath}/group/type/delete/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResultModel delete(@PathVariable("id") int id) {
        return new ResultModel(groupTypeService.delete(id));
    }


}
