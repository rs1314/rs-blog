package cn.rs.blog.web.aop;

import org.aspectj.lang.ProceedingJoinPoint;

import cn.rs.blog.core.dto.ResultModel;
import cn.rs.blog.core.exception.JeeException;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


/**
 * @author rs
 */
@Aspect
@Component
public class ControllerAop {
    @Pointcut("execution(public cn.rs.blog.core.dto.ResultModel *(..))")
    public void exec(){

    }


    @Around("exec()")
    public Object handlerController(ProceedingJoinPoint pjp){
        ResultModel<?> result;
        try {
            result = (ResultModel) pjp.proceed();
        } catch (Throwable e) {
            result = handlerExceotion(pjp, e);
        }
        return result;
    }

    private ResultModel<?> handlerExceotion(ProceedingJoinPoint pjp, Throwable e){
        ResultModel<?> result = new ResultModel();
        if (e instanceof JeeException && null != ((JeeException) e).getJeeMessage()){
            result.setMessage(((JeeException)e).getJeeMessage());
        }else {
            result.setCode(-1);
            if (null == e.getMessage()){
                result.setMessage("系统异常：" + e.toString());
            }else {
                result.setMessage(e.getMessage());
            }
            e.printStackTrace();
        }
        return result;
    }
}
