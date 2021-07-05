package cn.cube.base.third.wx;

import cn.cube.base.core.common.ConfigProperties;
import org.springframework.boot.autoconfigure.availability.ApplicationAvailabilityAutoConfiguration;
/**
 * 公众平台支付（公众号，小程序支付）
 * User: rizenguo
 * Date: 2014/10/29
 * Time: 14:40
 * 这里放置各种配置数据
 */
public class WxMpPayConfigure {
    //key
    private static String key = ConfigProperties.getValue("wx.pay.key");

    //支付异步通知地址
    private static String notifyUrl = ConfigProperties.getValue("wx.pay.notify.url");

    private static final String appId = ConfigProperties.getValue("wx.open.pay.app.id");

    private static final String mchid = ConfigProperties.getString("wx.open.pay.mch.id");

    private static final String certLocalPath = ConfigProperties.getString("wx.open.pay.cert.local.path");

    private static final String certPassword = ConfigProperties.getString("wx.open.pay.cert.password");


    //链接超时
    private static int connectTimeout = 5000;
    //订单过期时间（分钟数）
    private static int payTimeout = ConfigProperties.getIntValue("wx.pay.timeout", 30);


    //随机串长度
    private static int noticeStrLength = 32;

    //机器IP
    private static String ip = "";

    public static String getNotifyUrl() {
        return notifyUrl;
    }

    public static String getKey() {
        return key;
    }

    //微信分配的公众号ID（开通公众号之后可以获取到）
    public static String getAppid() {
        return appId;
    }

    //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
    public static String getMchid() {
        return mchid;
    }

    //HTTPS证书的本地路径
    public static String getCertLocalPath() {
        return certLocalPath;
    }

    //HTTPS证书密码，默认密码等于商户号MCHID
    public static String getCertPassword() {
        return certPassword;
    }

    public static int getPayTimeout() {
        return payTimeout;
    }

    public static void setPayTimeout(int payTimeout) {
        WxMpPayConfigure.payTimeout = payTimeout;
    }

    public static String getIP() {
        return ip;
    }

    public static int getNoticeStrLength() {
        return noticeStrLength;
    }


    public static int getConnectTimeout() {
        return connectTimeout;
    }

    public static void setConnectTimeout(int connectTimeout) {
        WxMpPayConfigure.connectTimeout = connectTimeout;
    }

}
