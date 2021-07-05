package cn.cube.base.third.ali;

/**
 * Description:支付宝静态变量
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class AliPayConstants {
    /**
     * 通知类型
     */
    public enum NotifyType {
        trade_status_sync,
    }

    /**
     * 支付宝内部订单状态
     */
    public enum TradeStatus {
        WAIT_BUYER_PAY("等待买家付款", "WAIT_BUYER_PAY"),
        TRADE_CLOSED("交易关闭", "TRADE_CLOSED"),
        TRADE_SUCCESS("支付成功", "TRADE_SUCCESS"),
        TRADE_FINISHED("交易结束", "TRADE_FINISHED"),;
        public final String name;
        public final String value;

        TradeStatus(String name, String value) {
            this.name = name;
            this.value = value;
        }

    }

}
