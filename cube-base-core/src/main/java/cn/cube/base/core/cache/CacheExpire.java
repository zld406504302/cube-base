package cn.cube.base.core.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:CacheExpire
 * Author:zhanglida
 * Date:2018/2/25
 * Email:406504302@qq.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface CacheExpire {
    //Sets the expire time (in seconds).
    long value() default 24*60*60;
}
