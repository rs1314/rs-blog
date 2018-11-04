package cn.rs.blog.core.annotation;

import java.lang.annotation.*;

/**
 * Created by rs
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
public @interface Clear {

}
