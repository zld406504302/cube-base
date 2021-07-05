package cn.cube.base.third.pay.request;

/**
 * Description:退款查询request
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class RefundQueryRequest extends PayRequest {
    private String out_trade_no;

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }
}
