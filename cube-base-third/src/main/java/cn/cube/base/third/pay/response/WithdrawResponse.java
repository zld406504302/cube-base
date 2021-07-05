package cn.cube.base.third.pay.response;

import cn.cube.base.third.wx.response.WxTransferWalletResponse;
import cn.cube.base.third.pay.request.TransferWalletRequest;

/**
 * Description:提现response
 * Author:zhanglida
 * Date:2018/6/13
 * Email:406504302@qq.com
 */
public class WithdrawResponse extends PayResponse {

    public static final String NOTENOUGH = "NOTENOUGH";
    public static final String AMOUNT_LIMIT = "AMOUNT_LIMIT";


    public WithdrawResponse(){}
    public WithdrawResponse(TransferWalletRequest request, WxTransferWalletResponse res) {
        super(request,res);
    }

    public boolean isNotEnough() {
        return NOTENOUGH.equals(this.getErrorCode());
    }

    public boolean isAmountLimit() {
        return AMOUNT_LIMIT.equals(this.getErrorCode());
    }


    @Override
    public Object getPayData() {
        return null;
    }
}
