package cn.cube.base.third.wx.response;

/**
 * Description:转账到零钱
 * Author:zhanglida
 * Date:2018/5/22
 * Email:406504302@qq.com
 */
public class WxTransferWalletResponse extends WxPayResponse {


    @Override
    public boolean isSuccess() {
        if (super.isSuccess() && SUCCESS.equals(this.getResult_code())) {
            return true;
        }
        return false;
    }


}
