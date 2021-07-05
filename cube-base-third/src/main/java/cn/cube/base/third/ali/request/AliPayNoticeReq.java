package cn.cube.base.third.ali.request;

import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.third.ali.AliPayConstants;

import java.util.Map;

/**
 * Description:支付宝支付通知request
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class AliPayNoticeReq {
    //通知时间
    private String notifyTime;
    //通知类型
    private AliPayConstants.NotifyType notifyType;
    //支付宝订单号
    private String tradeNo;
    //商户订单号
    private String outTradeNo;

    private String tradeStatus;

    //保存整个结果
    private String result;

    public String getNotifyTime() {
        return notifyTime;
    }

    public void setNotifyTime(String notifyTime) {
        this.notifyTime = notifyTime;
    }

    public AliPayConstants.NotifyType getNotifyType() {
        return notifyType;
    }

    public void setNotifyType(AliPayConstants.NotifyType notifyType) {
        this.notifyType = notifyType;
    }

    public String getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    public Long getOutTradeNo() {
        return Long.valueOf(outTradeNo);
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getTradeStatus() {
        return tradeStatus;
    }

    public void setTradeStatus(String tradeStatus) {
        this.tradeStatus = tradeStatus;
    }


    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static AliPayNoticeReq newInstance(Map<String, String[]> param) {
        AliPayNoticeReq req = new AliPayNoticeReq();
        if (null == param) {
            return null;
        }

        req.notifyTime = getValue(param, "notify_time");
        req.tradeNo = getValue(param, "trade_no");
        String notifyType = getValue(param, "notify_type");
        req.notifyType = AliPayConstants.NotifyType.valueOf(notifyType);
        req.outTradeNo = getValue(param, "out_trade_no");
        req.tradeStatus = getValue(param, "trade_status");
        req.result = JsonUtil.toJson(param);
        return req;

    }

    private static String getValue(Map<String, String[]> param, String key) {
        String[] values = param.get(key);

        String valueStr = "";
        for (int i = 0; i < values.length; i++) {
            valueStr = (i == values.length - 1) ? valueStr + values[i]
                    : valueStr + values[i] + ",";
        }
        return valueStr;
    }

    public boolean isSuccess() {
        return AliPayConstants.TradeStatus.TRADE_SUCCESS.value.equals(this.tradeStatus);
    }


}
