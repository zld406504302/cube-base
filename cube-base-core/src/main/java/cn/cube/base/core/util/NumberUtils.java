package cn.cube.base.core.util;

import java.math.BigDecimal;
import java.util.Random;

/**
 * Created by Administrator on 2017/2/20.
 */
public class NumberUtils {


    public static int toInt(final String str) {
        return toInt(str, 0);
    }

    public static int toInt(final String str, final int defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static long toLong(final String str) {
        return toLong(str, 0L);
    }

    public static long toLong(final String str, final long defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Long.parseLong(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static float toFloat(final String str) {
        return toFloat(str, 0.0f);
    }

    public static float toFloat(final String str, final float defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Float.parseFloat(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static double toDouble(final String str) {
        return toDouble(str, 0.0d);
    }

    public static double toDouble(final String str, final double defaultValue) {
        if (str == null) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(str);
        } catch (final NumberFormatException nfe) {
            return defaultValue;
        }
    }

    public static boolean isDigits(String str) {
        return org.apache.commons.lang3.math.NumberUtils.isDigits(str);
    }

    public static double scaleFormat(Double value, int limit) {
        if (value == null) {
            return BigDecimal.ZERO.doubleValue();
        }
        return new BigDecimal(value).setScale(limit, BigDecimal.ROUND_HALF_EVEN).doubleValue();
    }

    public static BigDecimal toYuan(BigDecimal amount) {
        if (null != amount) {
            return amount.movePointLeft(2);
        }
        return BigDecimal.ZERO;
    }

    public static BigDecimal toYuan(Integer amount) {
        if (null != amount) {
            return BigDecimal.valueOf(amount).movePointLeft(2);
        }
        return BigDecimal.ZERO;
    }

    public static Integer random(Integer min ,Integer max){
        return new Random().nextInt(max-min)+min;
    }


}
