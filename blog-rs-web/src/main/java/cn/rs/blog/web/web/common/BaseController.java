package cn.rs.blog.web.web.common;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;

import cn.rs.blog.bean.member.MemberToken;
import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.exception.JeeException;
import cn.rs.blog.core.exception.ParamException;
import cn.rs.blog.core.utils.Const;
import cn.rs.blog.core.utils.StringUtils;
import cn.rs.blog.service.member.IMemberTokenService;

/**
 * Controller基类
 * Created by rs
 */
public class BaseController {
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    @Autowired
    protected IMemberTokenService memberTokenService;

    protected ResultModel<MemberToken> validMemberToken(){
        ResultModel model = new ResultModel(-1,"非法操作");
        String token = getParam("token");
        if (StringUtils.isNotEmpty(token)){
            MemberToken memberToken = memberTokenService.getByToken(token);
            if (memberToken != null){
                model.setData(memberToken);
                model.setCode(0);
                model.setMessage("");
            }
        }
        return model;
    }

    protected String getParam(String name){
        return request.getParameter(name);
    }

    protected String getParam(String name, String defaultValue){
        String value = getParam(name);
        if (null == value){
            value = defaultValue;
        }
        return value;
    }

    protected int getParamToInt(String name) throws ParamException {
        String value = getParam(name);
        try {
            return Integer.parseInt(value);
        }catch (Exception e){
            throw new ParamException("参数异常");
        }
    }

    protected int getParamToInt(String name, int defaultValue){
        try {
            return getParamToInt(name);
        } catch (ParamException paramException) {
            return defaultValue;
        }
    }

    protected double getParamToDouble(String name) throws ParamException {
        String value = getParam(name);
        try {
            return Double.parseDouble(value);
        }catch (Exception e){
            throw new ParamException("参数异常");
        }
    }

    protected double getParamToInt(String name, double defaultValue){
        try {
            return getParamToDouble(name);
        } catch (ParamException paramException) {
            return defaultValue;
        }
    }


    protected String getErrorMessages(BindingResult result) {
        StringBuffer errorMessages = new StringBuffer();
        List<FieldError> list = result.getFieldErrors();
        int count = 0;
        for (FieldError error : list) {
            errorMessages.append(error.getDefaultMessage());
            count ++;
            if(count < list.size()){
                errorMessages.append("<br/>");
            }
        }
        return errorMessages.toString();
    }

    protected String errorModel(Model model,String msg){
        model.addAttribute("msg",msg);
        return Const.MANAGE_ERROR_FTL_PATH;
    }

    /**
     * 判断是否是AJAX请求
     * @return true ajax
     */
    protected boolean isAjaxRequest(){
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header) ? true : false;
    }

    /**
     * 全局捕获异常
     * @param e 捕获到的异常
     */
    @ExceptionHandler
    public void execptionHandler(Exception e){
        //ajax请求在aop中处理异常
        if (!isAjaxRequest()){
            try {
                e.printStackTrace();
                String msg = "系统异常：" + e.toString();
                if (e instanceof JeeException && ((JeeException) e).getJeeMessage() != null){
                    msg = ((JeeException) e).getJeeMessage().getMessage();
                }
                msg = URLEncoder.encode(msg, "UTF-8");
                request.setAttribute("msg",msg);
                response.sendRedirect(request.getContextPath() + "/error?msg="+msg);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }
}
