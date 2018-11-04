package cn.rs.blog.bean.common;

import cn.rs.blog.bean.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringEscapeUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Digits;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;

/**
 * 文章实体类
 * Created by rs
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Archive implements Serializable {
    private Integer archiveId;
    private Integer postType;
    @NotBlank(message = "文章标题不能为空")
    private String title;

    private Integer memberId;

    private Member member;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String description;

    private String keywords;

    @Digits(integer = 1,fraction = 0,message = "浏览权限只能是数字")
    private Integer viewRank;

    @Digits(integer = 11,fraction = 0,message = "浏览数只能是数字")
    private Integer viewCount;

    private String writer;

    private String source;

    private Date pubTime;

    private Date updateTime;

    private String thumbnail;

    private Date lastReply;

    private Integer canReply;

    private Integer goodNum;

    private Integer badNum;

    private Integer checkAdmin;

    @NotBlank(message = "文章内容不能为空")
    private String content;

    //喜欢数量
    private Integer favor;

    //会员是否已点击喜欢
    private Integer isFavor;

    public String getContent() {
        //转义字符串的处理
        content = StringEscapeUtils.unescapeHtml4(content);
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Archive copy(Object object) throws Exception {
        Class<?> classType = object.getClass();
        //当前对象
        Class<?> newClassType = this.getClass();
        Object objectCopy = classType.getConstructor(new Class[]{}).newInstance(new Object[]{});
        //获得当前对象的所有成员变量
        Field[] fields = newClassType.getDeclaredFields();
        for(Field field : fields) {
            //获取成员变量的名字
            String name = field.getName();
            //获取get和set方法的名字
            String firstLetter = name.substring(0,1).toUpperCase();
            String getMethodName = "get" + firstLetter + name.substring(1);
            String setMethodName = "set" + firstLetter + name.substring(1);
            //获取方法对象
            Method getMethod = classType.getMethod(getMethodName, new Class[]{});
            Method setMethod = classType.getMethod(setMethodName, new Class[]{field.getType()});
            //调用get方法获取旧的对象的值
            Object value = getMethod.invoke(object, new Object[]{});
            //调用set方法将这个值复制到新的对象中去
            setMethod.invoke(objectCopy, new Object[]{value});
        }
        return (Archive) objectCopy;
    }

}