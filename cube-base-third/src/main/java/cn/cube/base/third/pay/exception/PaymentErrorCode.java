package cn.cube.base.third.pay.exception;

import cn.cube.base.core.exception.IBusinessError;

import java.util.HashMap;
import java.util.Map;

/**
 * 错误码定义类
 * <p>
 * 1000 - 9999 为系统错误码
 */
public enum PaymentErrorCode implements IBusinessError {
    /**
     * 10700-10799 第三方支付相关错误码
     **/
    PAY_ERROR(10700, "支付失败"),
    PAY_PARAM_ERROR(10701, "支付参数错误[{}]"),
    PAY_ORDER_CLOSE_ERROR(10702, "支付订单关闭失败"),
    PAY_ORDER_IS_ABSENT(10703, "支付订单不存在"),
    PAY_ORDER_IS_CLOSED(10704, "支付订单已关闭"),
    PAY_ALI_NOTICE_GET_PARAM_ERROR(10705, "支付宝支付通知参数获取错误"),
    PAY_WX_NOTICE_GET_PARAM_ERROR(10706, "微信支付通知参数获取错误"),
    PAY_GOODS_ID_IS_NULL(10707, "商品编号不能为空"),
    PAY_ORDER_IS_EXPIRED(10708, "支付订单已过期"),
    PAY_REFUND_FAIL(10709, "退款失败"),
    PAY_SHARING_FAIL(10801, "分账失败"),
    /** END 10700-10799 第三方支付相关错误码 */

    /**
     * 12000-12099 活动
     */
    WITHDRAW_AMOUNT_IS_SHORT(12000, "提现金额不足"),
    WITHDRAW_NOT_BIND_USER(12001,"请绑定微信用户"),
    WITHDRAW_MIN_AMOUNT(12002, "最少提现{}元"),
    WITHDRAW_AMOUNT_LIMIT(12003, "提现金额超限"),
    WITHDRAW_COMPANY_AMOUNT_IS_SHORT(12004, "商家余额不足"),
    WITHDRAW_FAIL(12005, "提现失败"),
    PAY_ACCOUNT_BAN(12006, "非实名用户账号不可发放"),
    WITHDRAW_REACH_MAX_WITHDRAW_AMOUNT(12007, "今日提现额度已达上限，请明天再来"),
    PAY_OPENID_NOT_MATCH(12009, "openid与商户appid不匹配"),
    PAY_FAIL(12010, "支付失败"),

    ;

    PaymentErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


    private static Map<Integer, PaymentErrorCode> errorMap = new HashMap<>();

    static {
        for (PaymentErrorCode pec : PaymentErrorCode.values()) {
            if (errorMap.containsKey(pec.code)) {
                throw new RuntimeException("Protocol error code[" + pec.code + "] already existed.");
            }
            errorMap.put(pec.getCode(), pec);
        }
    }

}
