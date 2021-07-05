package cn.cube.base.core.common;

import cn.cube.base.core.util.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

/**
 * Description:配置加载类
 * Author:zhanglida
 * Date:2017/6/23
 * Email:406504302@qq.com
 */
public class ConfigProperties {

    private static Environment env;

    @Autowired
    public void setEnv(Environment env) {
        this.env = env;
    }

    public static String getString(String key) {
        return env.getProperty(key);
    }

    public static String getValue(String key) {
        String value = getString(key);
        if (!StringUtils.isEmpty(value)) {
            return StringUtils.trim(value);
        }
        return value;
    }

    public static String getValue(String key, String defaultValue) {
        String value = getValue(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    public static int getIntValue(String key, int defaultValue) {
        String value = getValue(key);
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return NumberUtils.toInt(value);
    }

}
