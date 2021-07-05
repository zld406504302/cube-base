package cn.cube.base.third.pay;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Description:支付状态
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public enum PayStatus {
    WAIT("等待支付", 1),
    SUCCESS("已支付成功", 2),
    FAIL("支付失败", 3),
    CLOSE("订单关闭", 4),
    NEED_REFUND("支付订单需退款", 5),
    REFUNDED("支付订单已退款", 6),;
    public final String name;
    public final Integer value;

    private static Map<Integer, PayStatus> status = Maps.newHashMap();

    static {
        for (PayStatus payStatus : PayStatus.values()) {
            status.put(payStatus.value, payStatus);
        }
    }

    PayStatus(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static PayStatus valueOf(Integer value) {
        return status.get(value);
    }

    public static List<Integer> canRefundStatus() {
        List<Integer> status = Lists.newArrayList();
        status.add(PayStatus.SUCCESS.value);
        status.add(PayStatus.CLOSE.value);

        return status;
    }

}