package cn.cube.base.third.wx.request;


import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.core.util.StringUtils;
import cn.cube.base.third.wx.Signature;
import com.alibaba.fastjson.TypeReference;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Description:支付请求共有参数
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class WxBasePayRequest implements IWxBasePayRequest {
    //商户内部订单号
    private String out_trade_no;
    //公众号id
    private String appid = "";
    //商户号id
    private String mch_id = "";
    //签名
    private String sign = "";
    //随机字符串
    private String nonce_str = "";
    //通知地址
    private String notify_url = "";


    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }


    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public Map<String, String> generateSignParam() {
        Map<String, String> map = JsonUtil.toObj(JsonUtil.toJson(this), new TypeReference<Map<String, String>>() {
        });
        Set<String> keys = new HashSet<>(map.keySet());
        keys.forEach(key -> {
            String value = String.valueOf(map.get(key));
            if (StringUtils.isEmpty(value)) {
                map.remove(key);
            }
        });
        return map;
    }

    @Override
    public void sign(String key) {
        String sign = Signature.getSign(key, generateSignParam());
        setSign(sign);
    }
}
