package cn.cube.base.third.pay.request;

import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.third.pay.PayMethod;
import cn.cube.base.third.pay.exception.PaymentErrorCode;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Description:退款request
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class RefundRequest extends PayRequest {
    //退款金额 单位：分
    private Integer amount;
    //退款原因
    private String reason;

    //支付方式
    private PayMethod method;

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getPointAmount() {
        return amount.toString();
    }

    public PayMethod getMethod() {
        return method;
    }

    public void setMethod(PayMethod method) {
        this.method = method;
    }

    public String getYuanAmount() {
        BigDecimal decimal = BigDecimal.valueOf(amount);
        decimal = decimal.divide(BigDecimal.valueOf(100), 2, RoundingMode.DOWN);
        return decimal.toString();
    }

    @Override
    public void check() {
        super.check();

        if (null == amount) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR, "amount 必须为数字");
        }
    }
}
