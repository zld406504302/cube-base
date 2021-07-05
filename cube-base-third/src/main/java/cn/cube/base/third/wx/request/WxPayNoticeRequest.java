package cn.cube.base.third.wx.request;

import cn.cube.base.core.util.StringUtils;
import cn.cube.base.third.wx.WxApi;
import cn.cube.base.third.wx.response.WxBasePayResponse;

/**
 * Description:微信支付通知req
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class WxPayNoticeRequest extends WxBasePayResponse {
    private String out_trade_no;
    private String transaction_id;
    private String result;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Long getOutTradeNo() {
        return Long.valueOf(out_trade_no);
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static WxPayNoticeRequest newInstance(String input) {
        if (StringUtils.isEmpty(input)) {
            return null;
        }
        WxPayNoticeRequest req = WxApi.getObjectFromXML(input, WxPayNoticeRequest.class);
        req.result = input;
        return req;
    }
}
