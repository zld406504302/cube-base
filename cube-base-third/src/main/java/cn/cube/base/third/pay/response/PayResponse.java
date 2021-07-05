package cn.cube.base.third.pay.response;

import cn.cube.base.core.util.StringUtils;
import cn.cube.base.third.pay.PayChannel;
import cn.cube.base.third.wx.response.WxPayResponse;
import cn.cube.base.third.pay.request.PayRequest;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Description:支付response 父类
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public  class PayResponse implements IPayResponse {
    public static final String V2_ACCOUNT_SIMPLE_BAN = "V2_ACCOUNT_SIMPLE_BAN";
    public static final String FAIL = "FAIL";
    public static final String PARAM_ERROR = "PARAM_ERROR";
    public static final String OPENID_ERROR = "OPENID_ERROR";

    //用户uid
    private Long uid;
    //thdUId
    private Long thdUid;
    //支付方订单号
    private Long tradeNo;
    //内部订单号
    private Long inTradeNo;
    //平台
    private PayChannel platform;
    //第三方返回的整个结果（json格式字符串）
    //成功还是失败
    private boolean success;
    @JSONField(serialize = false)
    //错误描述
    private String errorMsg;
    //错误code
    @JSONField(serialize = false)
    private String resultCode;
    //错误code
    @JSONField(serialize = false)
    private String errorCode;

    private Object payData;
    public PayResponse() {
    }

    public PayResponse(PayRequest request, WxPayResponse payRes) {
        setSuccess(payRes.isSuccess());
        if (!isSuccess()) {
            setErrorMsg(payRes.getErr_code_des());
            if(StringUtils.isEmpty(this.errorMsg)){
                setErrorMsg(payRes.getReturn_msg());
            }
        }
        setInTradeNo(request.getInTradeNo());
        setUid(request.getUid());
        setThdUid(request.getThdUid());
        setPlatform(request.getPayChannel());
    }
    public PayResponse(PayRequest payRequest) {
        this.uid = payRequest.getUid();
        this.inTradeNo = payRequest.getInTradeNo();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public Long getTradeNo() {
        return tradeNo;
    }

    public void setTradeNo(Long tradeNo) {
        this.tradeNo = tradeNo;
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

    public String getResultCode() {
        return resultCode;
    }

    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    public void setInTradeNo(Long inTradeNo) {
        this.inTradeNo = inTradeNo;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public PayChannel getPlatform() {
        return platform;
    }

    public void setPlatform(PayChannel platform) {
        this.platform = platform;
    }
    @Override
    public Object getPayData() {
        return payData;
    }

    public void setPayData(Object payData) {
        this.payData = payData;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public void setError(String errorMsg) {
        this.errorMsg = errorMsg;
    }
    public void setError(String errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }
    public Long getThdUid() {
        return thdUid;
    }

    public void setThdUid(Long thdUid) {
        this.thdUid = thdUid;
    }

    public boolean isUnknowFail() {
        return FAIL.equals(this.getResultCode());
    }

    @Override
    public String toString() {
        return
                "uid=" + uid +
                        ", thdUid=" + thdUid +
                        ", tradeNo=" + tradeNo +
                        ", inTradeNo=" + inTradeNo +
                        ", platform=" + platform +
                        ", success=" + success +
                        ", errorMsg='" + errorMsg + '\'' +
                        ", resultCode='" + resultCode + '\'';
    }
}
