package cn.cube.base.third.wx.request;

/**
 * Description:企业转账到零钱
 * Author:zhanglida
 * Date:2018/5/22
 * Email:406504302@qq.com
 */
public class WxTransferWalletRequest extends WxBasePayRequest {
    private String mch_appid;
    private String mchid;
    //商户订单号 必须为数字
    private String partner_trade_no;
    private String openid;
    private String check_name="NO_CHECK";
    private Integer amount= 0;
    private String desc;
    private String spbill_create_ip ;

    public String getMch_appid() {
        return mch_appid;
    }

    public void setMch_appid(String mch_appid) {
        this.mch_appid = mch_appid;
    }

    public String getMchid() {
        return mchid;
    }

    public void setMchid(String mchid) {
        this.mchid = mchid;
    }

    public String getPartner_trade_no() {
        return partner_trade_no;
    }

    public void setPartner_trade_no(String partner_trade_no) {
        this.partner_trade_no = partner_trade_no;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getCheck_name() {
        return check_name;
    }

    public void setCheck_name(String check_name) {
        this.check_name = check_name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    @Override
    public void setMch_id(String mch_id) {
        this.mchid = mch_id;
    }

    @Override
    public void setAppid(String appid) {
        this.mch_appid = appid;
    }
}
