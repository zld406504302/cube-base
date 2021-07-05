package cn.cube.base.third.pay.response;

/**
 * Description:二维码支付response
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class QrCodePayResponse extends PayResponse implements IPayResponse {
    //二维码地址
    private String codeUrl;

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }


    @Override
    public Object getPayData() {
        return null;
    }
}
