package cn.rs.blog.config;

import cn.rs.blog.core.filter.XssFilter;
import cn.rs.blog.core.utils.SpringContextHolder;
import cn.rs.blog.web.directive.*;
import cn.rs.blog.web.listener.InitListener;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.utility.XmlEscape;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Rs      529811807@qq.com
 * @data 2018/10/31   11:45
 */
@Configuration
@Log4j
public class RsConfigBlogs {


    /**
     * 避免IE执行AJAX时，返回JSON出现下载文件
     *
     * @return
     */
    @Bean
    public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter(@Qualifier("mappingJackson2HttpMessageConverter")MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter) {

        RequestMappingHandlerAdapter requestMappingHandlerAdapter = new RequestMappingHandlerAdapter();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        messageConverters.add(mappingJackson2HttpMessageConverter);

        requestMappingHandlerAdapter.setMessageConverters(messageConverters);
        return requestMappingHandlerAdapter;
    }

    @Bean(name ="mappingJackson2HttpMessageConverter")
    public MappingJackson2HttpMessageConverter getMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 时区指定
        objectMapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        MediaType[] types = {MediaType.TEXT_HTML, MediaType.TEXT_PLAIN, MediaType.APPLICATION_XML};
        List<MediaType> mediaTypes = new ArrayList<MediaType>();
        MediaType mediaType = null;
        for (int i = 0; i < types.length; i++) {
            mediaType = types[i];
            mediaTypes.add(mediaType);
        }
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        return mappingJackson2HttpMessageConverter;
    }

    /**
     *  配置Freemarker属性文件路径
     * @return
     */
   @Bean(name = "freemarkerConfiguration")
   public PropertiesFactoryBean getPropertiesFactoryBean(){
       PropertiesFactoryBean propertiesFactoryBean= new PropertiesFactoryBean();
       Resource resource = new ClassPathResource("freemarket/freemarker.properties");
       propertiesFactoryBean.setLocation(resource);
       return propertiesFactoryBean;

   }

    /**
     *  配置freeMarker的模板路径
     * @param propertiesFactoryBean
     * @param fmXmlEscape
     * @param articleDirective
     * @param weiboDirective
     * @param groupDirective
     * @param groupTopicDirective
     * @param groupTypeDirective
     * @param adsDirective
     * @return
     * @throws IOException
     */
    @Bean(name="freemarkerConfig")
    public FreeMarkerConfigurer getFreeMarkerConfigurer(@Qualifier("freemarkerConfiguration")PropertiesFactoryBean propertiesFactoryBean
      , @Qualifier("fmXmlEscape")XmlEscape fmXmlEscape , @Qualifier("articleDirective") ArticleDirective articleDirective , @Qualifier("weiboDirective") WeiboDirective weiboDirective
            , @Qualifier("groupDirective") GroupDirective groupDirective , @Qualifier("groupTopicDirective") GroupTopicDirective groupTopicDirective
            , @Qualifier("groupTypeDirective") GroupTypeDirective groupTypeDirective , @Qualifier("adsDirective")AdsDirective adsDirective) throws IOException {
        FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
        freeMarkerConfigurer.setTemplateLoaderPath("classpath:/web/");
        freeMarkerConfigurer.setFreemarkerSettings(propertiesFactoryBean.getObject());

        Map<String, Object> variablesMaps = new HashMap<String, Object>();
        variablesMaps.put("xml_escape",fmXmlEscape);
        variablesMaps.put("cms_article_list",articleDirective);
        variablesMaps.put("wb_weibo_list",weiboDirective);
        variablesMaps.put("group_list",groupDirective);
        variablesMaps.put("group_topic_list",groupTopicDirective);
        variablesMaps.put("group_type_list",groupTypeDirective);
        variablesMaps.put("ads",adsDirective);
        freeMarkerConfigurer.setFreemarkerVariables(variablesMaps);
        return freeMarkerConfigurer;
    }
    @Bean(name = "fmXmlEscape")
    public XmlEscape getXmlEscape(){
        XmlEscape xmlEscape = new XmlEscape();
        return xmlEscape;
    }

    /**
     * 配置freeMarker视图解析器
     * @return
     */
    @Bean
    public FreeMarkerViewResolver getFreeMarkerViewResolver(){
        FreeMarkerViewResolver freeMarkerViewResolver = new FreeMarkerViewResolver();
        freeMarkerViewResolver.setViewClass(FreeMarkerView.class);
        freeMarkerViewResolver.setContentType("text/html; charset=utf-8");
        freeMarkerViewResolver.setSuffix(".ftl");
        freeMarkerViewResolver.setOrder(0);
        return freeMarkerViewResolver;
    }




    /**
     * 配置文件上传
     *
     * @return
     */
    @Bean
    public CommonsMultipartResolver getCommonsMultipartResolver() {
        CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
        //默认编码
        commonsMultipartResolver.setDefaultEncoding("utf-8");
        //文件大小最大值
        commonsMultipartResolver.setMaxUploadSize(10485760000l);
        //内存中的最大值
        commonsMultipartResolver.setMaxInMemorySize(40960);
        return commonsMultipartResolver;
    }
    /**
     * 需要注入ResourceBundleMessageSource。注意： bean的id必须是messageSource。
     *国际化资源文件
     * @return
     */
   @Bean(name="messageSource")
    public ReloadableResourceBundleMessageSource getReloadableResourceBundleMessageSource(){
       ReloadableResourceBundleMessageSource reloadableResourceBundleMessageSource = new ReloadableResourceBundleMessageSource();
       reloadableResourceBundleMessageSource.setBasename("classpath:i18n/messages");
       //如果在国际化资源文件中找不到对应代码的信息，就用这个代码作为名称
       reloadableResourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
       reloadableResourceBundleMessageSource.setCacheSeconds(0);
       reloadableResourceBundleMessageSource.setDefaultEncoding("UTF-8");
       return  reloadableResourceBundleMessageSource;
    }

    /**
     * 配置过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean someFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(XssFilter());
        registration.addUrlPatterns("/*");
        registration.addInitParameter("paramName", "paramValue");
        registration.setName("XssFilter");

        EnumSet<DispatcherType> dispatcherTypes = EnumSet.allOf(DispatcherType.class);
        dispatcherTypes.add(DispatcherType.REQUEST);
        registration.setDispatcherTypes(dispatcherTypes);

       log.info("filter");
        return registration;
    }

    /**
     * 创建一个bean   XSS攻击过滤器
     * @return
     */
    @Bean(name = "XssFilter")
    public Filter XssFilter() {
        return new XssFilter();
    }

    /**
     * 配置监听器
     * @return
     */
    @Bean
    public ServletListenerRegistrationBean listenerRegist(@Qualifier("initListener")InitListener initListener ) {
        ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
        srb.setListener(initListener);
        log.info("listener");
        return srb;
    }
    @Bean(name = "initListener")
    public InitListener getInitListener(@Qualifier("springContextHolder")SpringContextHolder springContextHolder){
        return new InitListener(springContextHolder);
    }


}
