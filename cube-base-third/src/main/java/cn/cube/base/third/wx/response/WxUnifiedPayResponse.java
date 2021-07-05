package cn.cube.base.third.wx.response;

import cn.cube.base.core.util.DateUtils;
import cn.cube.base.core.util.StringUtils;
import cn.cube.base.third.wx.Signature;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Description:统一下单响应
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class WxUnifiedPayResponse extends WxPayResponse implements IWxPayResponse {
    //预支付交易会话标识
    private String prepay_id = "";
    //支付渠道appid
    private String appId;
    //支付key
    private String key;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    /**
     * 生成前端
     *
     * @return
     */
    @Override
    public Map<String, String> getPayParam() {
        Map<String, String> data = Maps.newHashMap();
        if (!isSuccess()) {
            return data;
        }
        data.put("appId", appId);
        data.put("timeStamp", DateUtils.get10TimeStamp());
        data.put("nonceStr", StringUtils.randomStringByLength(32));
        data.put("package", "prepay_id=" + prepay_id);
        data.put("signType", "MD5");
        String sign = Signature.getSign(key, data);
        data.put("sign", sign);
        return data;
    }
}
