package cn.cube.base.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:RequestSafety
 * Author:zhanglida
 * Date:2020/3/12
 * Email:406504302@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface RequestSafety {
    boolean auth() default true;

    boolean encrypt() default true;
}
