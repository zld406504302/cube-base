package cn.cube.base.third.pay.response;

import cn.cube.base.third.wx.WxOpenPayConfigure;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Description:微信app支付response
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class WxAppPayResponse extends PayResponse implements IPayResponse {

    //预支付交易会话标识
    private String prepayId;
    //随机串
    private String nonceStr;
    //package
    private String signType = "Sign=WXPay";

    //sign
    private String sign;

    public String getAppId() {
        return WxOpenPayConfigure.getAppId();
    }

    public String getPartnerId(String appId) {
        return WxOpenPayConfigure.getMchid();
    }

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public Map<String, Object> signParam(String appId) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("appid", getAppId());
        data.put("partnerid", getPartnerId(appId));
        data.put("prepayid", prepayId);
        data.put("package", signType);
        String timestamp = System.currentTimeMillis() + "";
        timestamp = timestamp.substring(0, 10);
        data.put("timestamp", timestamp);
        data.put("noncestr", nonceStr);
        return data;
    }
}
