package cn.cube.base.third.pay.response;
import cn.cube.base.third.wx.response.WxPayResponse;
import cn.cube.base.third.pay.request.PayRequest;

/**
 * Description:提现response
 * Author:zhanglida
 * Date:2018/6/13
 * Email:406504302@qq.com
 */
public class WithdrawQueryResponse extends PayResponse {

    public WithdrawQueryResponse(){}
    public WithdrawQueryResponse (PayRequest request, WxPayResponse payRes) {
        super(request,payRes);
    }

    @Override
    public Object getPayData() {
        return null;
    }
}
