package cn.cube.base.third.pay.request;

/**
 * Description:企业支付到零钱
 * Author:zhanglida
 * Date:2018/5/22
 * Email:406504302@qq.com
 */
public class TransferWalletRequest extends PayRequest  {

    private String desc;
    private String spbill_create_ip="127.0.0.1";

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
}
