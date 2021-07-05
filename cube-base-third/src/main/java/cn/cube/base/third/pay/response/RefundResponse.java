package cn.cube.base.third.pay.response;

import cn.cube.base.third.pay.request.PayRequest;
import cn.cube.base.third.wx.response.WxPayResponse;

/**
 * Description:退款Response
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class RefundResponse extends PayResponse implements IPayResponse {
    public RefundResponse(){}
    public RefundResponse(PayRequest request, WxPayResponse payRes) {
        super(request,payRes);
    }
    @Override
    public Object getPayData() {
        return null;
    }
}
