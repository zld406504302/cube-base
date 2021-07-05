package cn.cube.base.third.wx;

/**
 * Description:微信配置
 * Author:zhanglida
 * Date:2018/9/25
 * Email:406504302@qq.com
 */
public interface WxUrl {
    String OPEN_AUTH_URL = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={}&redirect_uri={}&response_type=code&scope=snsapi_userinfo&state=STATE&connect_redirect=1#wechat_redirect";
    //String OPEN_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";
    String OPEN_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={}&secret={}&code={}&grant_type=authorization_code";

    String INFO_URL = "https://api.weixin.qq.com/sns/userinfo?openid={}&access_token={}&lang=zh_CN";
    String JS_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token={}&type=jsapi";
    String MP_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={}&secret={}";

    String MP_SESSION_URL = "https://api.weixin.qq.com/sns/jscode2session?appid={}&secret={}&js_code={}&grant_type=authorization_code";

    /**接口url **/
    //订单关闭
    String ORDER_CLOSE_URI = "https://api.mch.weixin.qq.com/pay/closeorder";
    //申请退款
    String REFUND_URI  = "https://api.mch.weixin.qq.com/secapi/pay/refund";
    //订单查询
    String ORDER_QUERY_URI = "https://api.mch.weixin.qq.com/pay/orderquery";

    //统一下单接口
    String UNIFIED_ORDER_URI = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //退款查询
    String REFUND_QUERY_URI  = "https://api.mch.weixin.qq.com/pay/refundquery";

    //付款到零钱
    String TRANSFER_WALLET_URI  = "https://api.mch.weixin.qq.com/mmpaymkttransfers/promotion/transfers";

    //付款到零钱查询
    String TRANSFER_WALLET_QYERT_URI="https://api.mch.weixin.qq.com/mmpaymkttransfers/gettransferinfo";

    //获取微信ACCESS_TOKEN
    String ACCESS_TOKEN_URI  = "https://api.weixin.qq.com/sns/oauth2/access_token";

    //获取微信USERINFO
    String USER_INFO_URI  = "https://api.weixin.qq.com/sns/userinfo";

    //添加分账
    String SHAREING_ADD_URI  = "https://api.mch.weixin.qq.com/pay/profitsharingaddreceiver";
    //请求分账
    String SHAREING_REQ_URI = "https://api.mch.weixin.qq.com/secapi/pay/profitsharing";
    //分账回退
    String SHAREING_RETURN_URI = "https://api.mch.weixin.qq.com/secapi/pay/profitsharingreturn";

}
