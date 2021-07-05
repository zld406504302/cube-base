package cn.cube.base.third.pay.response;

import cn.cube.base.third.wx.response.WxPayResponse;
import cn.cube.base.third.pay.request.PayRequest;

/**
 * Description:微信app支付response
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class WxMpPayResponse extends PayResponse implements IPayResponse {

    public WxMpPayResponse(){}
    public WxMpPayResponse (PayRequest request, WxPayResponse payRes){
        super(request,payRes);
    }
}
