package cn.cube.base.core.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:KeyParam
 * Author:zhanglida
 * Date:2018/3/11
 * Email:406504302@qq.com
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface KeyParam {
    String key() default "";
}
