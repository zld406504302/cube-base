package cn.cube.base.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerUtils {

    public static <T> Logger getLogger(Class<T> cl) {
        return LoggerFactory.getLogger(cl);
    }

}
