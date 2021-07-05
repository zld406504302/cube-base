package cn.cube.base.third.wx.response;

/**
 * Description:微信app支付response
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class WxAppPayResponse extends WxBasePayResponse {
    //预支付交易会话标识
    private String prepay_id="";

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }
}
