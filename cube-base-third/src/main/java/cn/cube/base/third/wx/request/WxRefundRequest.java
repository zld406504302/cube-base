package cn.cube.base.third.wx.request;

/**
 * Description:退款
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class WxRefundRequest extends WxBasePayRequest {
    //退款单号 设置成和商户单号（out_trade_no）一致
    private String out_refund_no;
    //退款金额
    private String total_fee;
    //退款金额
    private String refund_fee ;

    public String getOut_refund_no() {
        return out_refund_no;
    }

    public void setOut_refund_no(String out_refund_no) {
        this.out_refund_no = out_refund_no;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getRefund_fee() {
        return refund_fee;
    }

    public void setRefund_fee(String refund_fee) {
        this.refund_fee = refund_fee;
    }
}
