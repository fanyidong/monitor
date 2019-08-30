package cn.fyd.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 * @author fanyidong
 * @date Created in 2019-01-28
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@Inherited
public @interface Log {
    String name() default  "";
}
