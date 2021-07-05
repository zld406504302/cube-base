package cn.cube.base.third.wx.response;
import cn.cube.base.core.util.StringUtils;

/**
 * Description:微信支付通知响应
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class WxPayNoticeResponse {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";
    private static final String pattern = "<xml><return_code><![CDATA[{}]]></return_code><return_msg><![CDATA[{}]]></return_msg></xml>";

    public static String success() {
        return StringUtils.format(pattern,SUCCESS,"OK");
    }

    public static String fail(String errMsg) {
        return StringUtils.format(pattern,FAIL,errMsg);
    }

}
