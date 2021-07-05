package cn.cube.base.third.wx.response;


import cn.cube.base.core.util.StringUtils;

/**
 * Description:支付相应共有参数
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class WxPayResponse {
    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL= "FAIL";
    //返回码
    private String return_code = "";
    //失败原因
    private String return_msg = "";
    //系统错误码
    private String err_code;
    //系统错误原因
    private String err_code_des;
    //签名
    private String sign;
    //公众号id
    private String appid;
    //商户id
    private String mch_id;
    //随机字符串
    private String nonce_str;
    private String result_code;

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return StringUtils.isEmpty(return_msg) ? err_code_des : return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public boolean isSuccess() {
        return SUCCESS.equals(this.return_code) && SUCCESS.equals(this.result_code);
    }


    /**
     * 支付宝内部订单状态
     */
    private enum TradeStatus {
        USERPAYING("支付中", "USERPAYING"),
        CLOSED("已关闭", "CLOSED"),
        SUCCESS("支付成功", "SUCCESS"),
        REFUND("转入退款", "REFUND"),
        FAIL("支付失败", "PAYERROR"),;
        private String name;
        private String value;

        TradeStatus(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
