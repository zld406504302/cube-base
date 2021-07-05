package cn.cube.base.third.wx.request;

/**
 * Description:统一下单请求
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class WxUnifiedPayRequest extends WxBasePayRequest {
    public static final String PROFIT_SHARING_Y = "Y";
    //商品描述
    private String body = "";
    //标价金额(分)
    private String total_fee = "";
    //终端ip
    private String spbill_create_ip = "127.0.0.1";
    //交易类型
    private String trade_type = "";
    //openid
    private String openid;
    //过期时间
    private String time_expire;
    //是否分账
    private String profit_sharing;

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProfit_sharing() {
        return profit_sharing;
    }

    public void setProfit_sharing(String profit_sharing) {
        this.profit_sharing = profit_sharing;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }
    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

}
