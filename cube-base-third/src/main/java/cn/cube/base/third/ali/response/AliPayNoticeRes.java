package cn.cube.base.third.ali.response;

/**
 * Description:支付宝支付通知response
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class AliPayNoticeRes {
    public static final String SUCCESS = "success";
    public static final String FAIL = "fail";

    public static String success() {
        return SUCCESS;
    }

    public static String fail() {
        return FAIL;
    }
}
