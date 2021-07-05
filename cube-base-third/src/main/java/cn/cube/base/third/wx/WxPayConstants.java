package cn.cube.base.third.wx;

/**
 * Description:微信支付常量
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class WxPayConstants {
    /**
     * 支付宝内部订单状态
     */
    public enum TradeStatus {
        USERPAYING("支付中", "USERPAYING"),
        CLOSED("已关闭", "CLOSED"),
        SUCCESS("支付成功", "SUCCESS"),
        REFUND("转入退款", "REFUND"),
        FAIL("支付失败", "PAYERROR"),;
        public final String name;
        public final String value;

        TradeStatus(String name, String value) {
            this.name = name;
            this.value = value;
        }
    }

    /**
     * 退款状态
     */
    public enum RefundStatus {
        SUCCESS("退款成功", "SUCCESS"),
        REFUNDCLOSE("退款关闭", "REFUNDCLOSE"),
        NOTSURE("未确定", "NOTSURE"),
        PROCESSING("退款处理中", "PROCESSING"),
        CHANGE("退款异常", "CHANGE"),;
        public final String name;
        public final String value;

        RefundStatus(String name, String value) {
            this.name = name;
            this.value = value;
        }

    }

    /**
     * Description:支付方式
     * Author:zhanglida
     * Date:2017/4/19
     * Email:406504302@qq.com
     */
    public enum PayMethod {
        APP,NATIVE,
    }


    //支付类型
    public enum TradeType {
        APP,NATIVE,JSAPI
    }
}
