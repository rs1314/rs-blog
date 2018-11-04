package cn.rs.blog.core.utils;

import java.util.Locale;

import org.springframework.context.MessageSource;

/**
 * @author rs
 */
public class LocaleUtil {
    private static final ThreadLocal<Locale> tlLocale = new ThreadLocal<Locale>(){
        @Override
        protected Locale initialValue() {
            return Locale.SIMPLIFIED_CHINESE;
        }
    };
    private static MessageSource messageSource;

    public static Locale getLocale() {
        return tlLocale.get();
    }

    public static void setMessageSource(MessageSource messageSource){
        LocaleUtil.messageSource = messageSource;
    }

    public static MessageSource getMessageSource() {
        return messageSource;
    }
}
