package cn.fyd.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 验证对象属性注解
 * @author fanyidong
 * @date 2018-12-11
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface IsEmpty {
    String name();

    boolean require() default true;

    String requireFailMessage() default "不能为空";
}
