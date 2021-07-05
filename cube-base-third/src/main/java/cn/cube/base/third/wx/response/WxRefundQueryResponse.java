package cn.cube.base.third.wx.response;

/**
 * Description:退款res
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class WxRefundQueryResponse extends WxBasePayResponse {
    private String refund_status = "";

    public String getRefund_status() {
        return refund_status;
    }

    public void setRefund_status(String refund_status) {
        this.refund_status = refund_status;
    }
}
