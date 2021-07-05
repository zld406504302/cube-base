package cn.cube.base.third.pay.request;

import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.StringUtils;
import cn.cube.base.third.pay.PayMethod;
import cn.cube.base.third.pay.exception.PaymentErrorCode;

import java.math.BigDecimal;

/**
 * Description:app支付创建request
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class PayCreateRequest extends PayRequest {
    //商品id
    private Long goodsId;
    //支付金额 单位：分
    private Integer amount;
    //订单标题
    private String title;
    //订单描述
    private String body;
    //支付方式
    private PayMethod method;
    //支付用户ip
    private String ip;

    //openId (jspapi支付必传)
    private String openId;


    public PayMethod getMethod() {
        return method;
    }

    public void setMethod(PayMethod payMethod) {
        this.method = payMethod;
    }

    public Integer getAmount() {
        return amount;
    }

    public String getPointAmount() {
        return amount.toString();
    }

    public String getYuanAmount() {
        BigDecimal decimal = BigDecimal.valueOf(amount);
        decimal = decimal.divide(BigDecimal.valueOf(100), 2,BigDecimal.ROUND_DOWN);
        return decimal.toString();
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public static void main(String[] args) {
        System.out.println(BigDecimal.valueOf(1).divide(BigDecimal.valueOf(100), 2,BigDecimal.ROUND_DOWN));
    }
    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    @Override
    public void check() {
        super.check();

        if (null == goodsId) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR, "goodsId 不能为空");
        }

        if (StringUtils.isEmpty(title)) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR, "title 不能为空");
        }

        if (null == method) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR, "payMethod 不能为空");
        }

        if (null == amount) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR, "amount 不能为空");
        }

        if (amount <= 0) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR, "amount 必须大于0");
        }

    }

    @Override
    public String toString() {
        return "{" + super.toString() +
                ",goodsId=" + goodsId +
                ", amount='" + amount + '\'' +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", method=" + method +
                '}';
    }
}
