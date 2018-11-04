package cn.rs.blog.web.web.rs;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 自定义错误页面处理
 * @author Rs      529811807@qq.com
 * @data 2018/11/2   13:01
 */
@Controller
public class RsErrorController implements  ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request){
        //获取statusCode:401,404,500
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if(statusCode == 404){
            return "/front/common/404";
        }else {
            return "/front/common/error";
        }

    }
    @Override
    public String getErrorPath() {
        return "/error";
    }
}