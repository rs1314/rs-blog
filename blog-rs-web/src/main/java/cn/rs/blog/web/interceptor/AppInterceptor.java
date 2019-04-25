package cn.rs.blog.web.interceptor;

import cn.rs.blog.bean.telephone.AppVO;
import cn.rs.blog.commoms.utils.AppGetquestAppVoUtils;
import cn.rs.blog.web.utils.AppThreadLocal;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public class AppInterceptor implements HandlerInterceptor {
    Logger logger = Logger.getLogger(AppInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        AppVO appVo = AppGetquestAppVoUtils.getAppVo(request);

        AppThreadLocal.setAppVo(appVo);

        response.setHeader("Access-Control-Allow-Origin", "*");
        // 允许哪些Origin发起跨域请求,nginx下正常
        // response.setHeader( "Access-Control-Allow-Origin", config.getInitParameter( "AccessControlAllowOrigin" ) );
        // 允许请求的方法
        response.setHeader("Access-Control-Allow-Methods", "POST,GET,OPTIONS,DELETE,PUT");
        // 多少秒内，不需要再发送预检验请求，可以缓存该结果
        response.setHeader("Access-Control-Max-Age", "3600");
        // 表明它允许跨域请求包含xxx头
        response.setHeader("Access-Control-Allow-Headers", "x-auth-token,Origin,Access-Token,X-Requested-With,Content-Type, Accept");
        //是否允许浏览器携带用户身份信息（cookie）
        response.setHeader("Access-Control-Allow-Credentials", "true");
        // response.setHeader( "Access-Control-Expose-Headers", "*" );
        if (request.getMethod().equals("OPTIONS")) {
            response.setStatus(200);
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        AppThreadLocal.removeAppvo();
    }
}
