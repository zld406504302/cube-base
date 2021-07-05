package cn.cube.base.third.pay.response;

import cn.cube.base.third.pay.PayStatus;

/**
 * Description:支付查询Response
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class PayQueryResponse extends PayResponse {
    private PayStatus status ;

    public PayStatus getStatus() {
        return status;
    }

    public void setStatus(PayStatus status) {
        this.status = status;
    }
}
