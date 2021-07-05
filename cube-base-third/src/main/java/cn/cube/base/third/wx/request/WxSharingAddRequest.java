package cn.cube.base.third.wx.request;

import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.third.wx.Signature;

/**
 * Description:WxSharingAddRequest
 * Author:zhanglida
 * Date:2020/9/9
 * Email:406504302@qq.com
 */
public class WxSharingAddRequest extends  WxBasePayRequest{
    private String receiver;


    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String openId) {
        Receiver receiver = new Receiver(openId);
        this.receiver = JsonUtil.toJson(receiver);
    }

    public static class Receiver{
        private String type = "PERSONAL_OPENID";
        private String account;
        private String relation_type="PARTNER";

        public Receiver(String account) {
            this.account = account;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getRelation_type() {
            return relation_type;
        }

        public void setRelation_type(String relation_type) {
            this.relation_type = relation_type;
        }
    }

    @Override
    public void sign(String key) {
        String sign = Signature.getSign(key, generateSignParam(),Signature.SIGN_TYPE_HMAC256);
        setSign(sign);
    }
}
