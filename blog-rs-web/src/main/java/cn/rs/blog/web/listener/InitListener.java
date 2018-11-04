package cn.rs.blog.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import cn.rs.blog.bean.system.Config;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.core.utils.SpringContextHolder;
import cn.rs.blog.service.system.IConfigService;

/**
 * Created by rs.
 */
public class InitListener implements ServletContextListener {
    SpringContextHolder springContextHolder;
    public InitListener() {
    }

    public InitListener(SpringContextHolder springContextHolder) {
        this.springContextHolder =springContextHolder;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            Const.PROJECT_PATH = sce.getServletContext().getContextPath();
            sce.getServletContext().setAttribute("basePath", Const.PROJECT_PATH);
            RsBlogConfig rsBlogConfig = springContextHolder.getBean("rsBlogConfig");
            sce.getServletContext().setAttribute("rsBlogConfig",rsBlogConfig);
            String frontTemplate = rsBlogConfig.getFrontTemplate();
            sce.getServletContext().setAttribute("frontTemplate",frontTemplate);
            String managePath = Const.PROJECT_PATH + "/" + rsBlogConfig.getManagePath();
            Const.GROUP_PATH = Const.PROJECT_PATH + "/" + rsBlogConfig.getGroupPath();
            Const.WEIBO_PATH = Const.PROJECT_PATH + "/" + rsBlogConfig.getWeiboPath();
            sce.getServletContext().setAttribute("managePath",managePath);
            sce.getServletContext().setAttribute("groupPath",Const.GROUP_PATH);
            sce.getServletContext().setAttribute("weiboPath",Const.WEIBO_PATH);
            IConfigService configService = springContextHolder.getBean("configService");
            List<Config> configList = configService.allList();
            for (Config config : configList) {
                sce.getServletContext().setAttribute(config.getJkey().toUpperCase(),config.getJvalue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
