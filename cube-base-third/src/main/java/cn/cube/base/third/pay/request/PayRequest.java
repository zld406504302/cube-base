package cn.cube.base.third.pay.request;


import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.StringUtils;
import cn.cube.base.third.pay.PayChannel;
import cn.cube.base.third.pay.PayMethod;
import cn.cube.base.third.pay.PayPlatform;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * Description:支付request父类
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class PayRequest implements IPayRequest {
    //应用编号
    @NotEmpty
    private String appId;
    //用户uid
    private Long uid=0L;
    //小程序内部用户id
    private Long thdUid;
    //内部订单号
    private Long inTradeNo;
    //支付渠道
    @NotNull
    private PayChannel payChannel;
    //支付平台
    private PayPlatform payPlatform;
    //支付方式
    @NotNull
    private PayMethod payMethod;
    //openid JSAPI支付（必填）
    private String openid;
    //金额
    private Integer amount;
    //支付文案
    private String body ;
    //回调地址
    private String notifyUrl;

    private Boolean profitSharing = false;
    public Boolean getProfitSharing() {
        return profitSharing;
    }

    public void setProfitSharing(Boolean profitSharing) {
        this.profitSharing = profitSharing;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public PayChannel getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(PayChannel payPlatform) {
        this.payChannel = payPlatform;
    }

    public PayPlatform getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(PayPlatform payPlatform) {
        this.payPlatform = payPlatform;
    }

    public String getNotifyUrl() {
        return notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public Long getInTradeNo() {
        return inTradeNo;
    }

    public String getStrInTradeNo() {
        return String.valueOf(inTradeNo);
    }

    public void setInTradeNo(Long inTradeNo) {
        this.inTradeNo = inTradeNo;
    }

    public Long getThdUid() {
        return thdUid = (null == thdUid ? 0L : thdUid);
    }

    public void setThdUid(Long thdUid) {
        this.thdUid = thdUid;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    @Override
    public void check() {

        if (StringUtils.isEmpty(appId)) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, "appId 不能为空");
        }

        if (null == uid && null == thdUid) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, "uid 和 thdUid 不能同时为空");
        }

        if (PayMethod.JS.equals(payMethod) && StringUtils.isEmpty(openid)) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, "openid 不能为空");
        }

    }

    @Override
    public String toString() {
        return "uid=" + uid +
                ",appId=" + appId +
                ", inTradeNo='" + inTradeNo + '\'' +
                ", platform=" + payChannel;
    }
}
