package cn.cube.base.third.wx.response;

/**
 * Description:转账到零钱
 * Author:zhanglida
 * Date:2018/5/22
 * Email:406504302@qq.com
 */
public class WxTransferWalletQueryResponse extends WxPayResponse {
    public static final String PROCESSING= "PROCESSING";

    private String status ;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean isSuccess() {
        if (super.isSuccess() && SUCCESS.equals(this.getResult_code())) {
            return true;
        }
        return false;
    }

    public boolean isProcessing(){
        return PROCESSING.equals(this.status);
    }


}
