package cn.rs.blog.web.utils;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import cn.rs.blog.core.utils.LocaleUtil;

/**
 * @author rs
 */
@Component
public class StaticFieldInjection {

    @Autowired
    private MessageSource messageSource;

    @PostConstruct
    private void init(){
        LocaleUtil.setMessageSource(messageSource);
    }
}
