package cn.cube.base.third.ali;

import cn.cube.base.core.common.ConfigProperties;

/**
 * Description:支付宝支付配置
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class AliPayConfigure {

    private static String gateway = ConfigProperties.getValue("ali.pay.gateway");
    private static String notifyUrl = ConfigProperties.getValue("ali.pay.notify.url");
    private static String privateKey = ConfigProperties.getValue("ali.pay.private.key");
    private static String publicKey = ConfigProperties.getValue("ali.pay.public.key");
    private static String appId = ConfigProperties.getValue("ali.pay.app.id");
    private static String timeOut = "45m";
    private static String productMode = "QUICK_MSECURITY_PAY";

    private static final String CHARSET = "UTF-8";
    private static final String SIGN_TYPE = "RSA2";

    public static String getGateway() {
        return gateway;
    }

    public static String getNotifyUrl() {
        return notifyUrl;
    }

    public static String getAppId(){
        return appId ;
    }

    public static String getPrivateKey() {
        return privateKey;
    }

    public static String getPublicKey() {
        return publicKey;
    }

    public static String getProductMode() {
        return productMode;
    }

    public static String getTimeOut() {
        return timeOut;
    }

    public static String getCharset() {
        return CHARSET;
    }

    public static String getSignType() {
        return SIGN_TYPE;
    }
}
