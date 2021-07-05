package cn.cube.base.third.wx.request;

/**
 * Description:企业转账到零钱
 * Author:zhanglida
 * Date:2018/5/22
 * Email:406504302@qq.com
 */
public class WxTransferWalletQueryRequest extends WxBasePayRequest {
    //商户订单号 必须为数字
    private String partner_trade_no;

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }
}
