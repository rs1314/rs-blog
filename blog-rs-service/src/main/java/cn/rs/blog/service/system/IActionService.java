package cn.rs.blog.service.system;

import java.util.List;

import cn.rs.blog.bean.system.Action;

/**
 * Created by rs
 */
public interface IActionService {

    List<Action> list();

    Action findById(Integer id);

    boolean update(Action action);

    boolean isenable(Integer id);

    boolean canuse(Integer id);
}
