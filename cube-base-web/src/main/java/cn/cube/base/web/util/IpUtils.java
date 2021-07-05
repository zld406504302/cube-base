package cn.cube.base.web.util;

import cn.cube.base.core.util.StringUtils;
import io.undertow.attribute.ExchangeAttributes;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.HttpString;

import javax.servlet.http.HttpServletRequest;

/**
 * IP 辅助类，获取请求的ip地址信息
 * Created by Administrator on 2017/4/5.
 */
public class IpUtils {
    private static HttpString REAL_IP = new HttpString("X-Forwarded-For");
    private static String LOCAL_IP = "127.0.0.1";

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return getIp(ip);
    }

    public static String getIpAddress(io.vertx.core.http.HttpServerRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           // ip = request.getRemoteAddr();
        }
        return getIp(ip);
    }

    public static String getIpAddress(HttpServerExchange exchange) {
        String ip = ExchangeAttributes.requestHeader(REAL_IP).readAttribute(exchange);
        return getIp(ip);
    }

    private static String getIp(String ip) {
        if (StringUtils.isEmpty(ip)) {
            ip = LOCAL_IP;
        }
        return ip;
    }

}
