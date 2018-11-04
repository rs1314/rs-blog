package cn.rs.blog.web.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.rs.blog.bean.member.Member;
import cn.rs.blog.commoms.utils.MemberUtil;
import cn.rs.blog.core.utils.RsBlogConfig;
import cn.rs.blog.core.utils.SpringContextHolder;
import cn.rs.blog.interceptor.RsInterceptor;
import net.sf.json.JSONObject;

/**
 * Created by rs
 */
public class AdminLoginInterceptor implements RsInterceptor {

    @Override
    public boolean interceptor(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Member loginAdmin = MemberUtil.getLoginMember(request);
        if (loginAdmin == null || loginAdmin.getIsAdmin() == 0) {
            if (isAjaxRequest(request)){
                response.setCharacterEncoding("utf-8");
                response.setContentType("application/json");
                PrintWriter out = response.getWriter();
                JSONObject json = new JSONObject();
                json.put("code", -99);
                json.put("message", "登录信息已失效，请重新登录");
                out.write(json.toString());
                out.flush();
                out.close();
            }else {
                RsBlogConfig rsBlogConfig = SpringContextHolder.getBean("rsBlogConfig");
                response.sendRedirect(request.getContextPath() + "/" + rsBlogConfig.getManagePath() + "/login");
            }
            return false;
        }
        request.setAttribute("loginUser", loginAdmin);
        return true;
    }

    /**
     * 判断是否是AJAX请求
     * @return true ajax
     */
    private boolean isAjaxRequest(HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header) ? true : false;
    }
}
