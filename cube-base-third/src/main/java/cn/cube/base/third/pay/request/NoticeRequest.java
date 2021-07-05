package cn.cube.base.third.pay.request;

import cn.cube.base.third.pay.PayChannel;
import cn.cube.base.third.pay.PayMethod;
import cn.cube.base.third.pay.PayPlatform;

import javax.validation.constraints.NotNull;

/**
 * Description:NoticeRequest
 * Author:zhanglida
 * Date:2019/11/21
 * Email:406504302@qq.com
 */
public class NoticeRequest {
    //支付平台
    @NotNull
    private PayChannel payChannel;
    //支付平台
    private PayPlatform payPlatform;
    //支付方式
    @NotNull
    private PayMethod payMethod;

    //回调数据
    @NotNull
    private String notice;

    public PayChannel getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(PayChannel payChannel) {
        this.payChannel = payChannel;
    }

    public PayPlatform getPayPlatform() {
        return payPlatform;
    }

    public void setPayPlatform(PayPlatform payPlatform) {
        this.payPlatform = payPlatform;
    }

    public PayMethod getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(PayMethod payMethod) {
        this.payMethod = payMethod;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
