package cn.cube.base.third.pay.utils;

import cn.cube.base.third.pay.PayChannel;
import org.slf4j.Logger;

/**
 * Description:支付request response 日志打印工具类
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class PayLogPrinterUtil {
    public static final String pattern = "[platform:{}] [method:{}] [app_id:{}] [uid:{}] ";

    public static void printRequest(Logger logger, PayChannel payPlatform, String method, String appId, Long uid, String request) {
        logger.info(pattern + " [request:{}]", payPlatform, method,appId, uid, request);
    }

    public static void printResponse(Logger logger, PayChannel payPlatform, String method, String appId, Long uid, String response) {
        logger.info(pattern + " [response:{}]", payPlatform, method,appId, uid, response);
    }

    public static void printError(Logger logger, PayChannel payPlatform, String method, String appId, Long uid, String errCode, String errMsg){
        logger.error(pattern + " [errCode:{}] [errMsg:{}]", payPlatform, method,appId, uid, errCode,errMsg);
    }
}
