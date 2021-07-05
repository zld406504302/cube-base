package cn.cube.base.third.pay.response;

import cn.cube.base.third.wx.WxMpPayConfigure;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Description:微信js支付response
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class WxJsPayResponse extends WxAppPayResponse implements IPayResponse {


    @Override
    public Map<String, Object> signParam(String appId) {
        Map<String, Object> data = Maps.newHashMap();
        data.put("appId", WxMpPayConfigure.getAppid());
        data.put("package", "prepay_id=" + this.getPrepayId());
        String timestamp = System.currentTimeMillis() + "";
        timestamp = timestamp.substring(0, 10);
        data.put("timeStamp", timestamp);
        data.put("nonceStr", getNonceStr());
        data.put("signType", "MD5");
        return data;
    }

}
