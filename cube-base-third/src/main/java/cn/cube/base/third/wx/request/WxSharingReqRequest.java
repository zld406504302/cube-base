package cn.cube.base.third.wx.request;

import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.third.wx.Signature;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;

/**
 * Description:WxSharingRequest
 * Author:zhanglida
 * Date:2020/9/9
 * Email:406504302@qq.com
 */
@Data
public class WxSharingReqRequest extends WxBasePayRequest {
    private String receivers;
    private String out_order_no;
    private String transaction_id;

    public void setReceiver(String openId, Integer amount, String desc) {
        Receiver receiver = new Receiver();
        receiver.setAccount(openId);
        receiver.setAmount(amount);
        receiver.setDescription(desc);
        List<Receiver> params = Lists.newArrayList();
        params.add(receiver);
        this.receivers = JsonUtil.toJson(params);
    }

    public static class Receiver {
        private String type = "PERSONAL_OPENID";
        private String relation_type = "PARTNER";
        private String account;
        private Integer amount = 0;
        private String description;

        public Integer getAmount() {
            return amount;
        }

        public void setAmount(Integer amount) {
            this.amount = amount;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
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
