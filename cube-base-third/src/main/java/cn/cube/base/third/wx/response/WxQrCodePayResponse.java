package cn.cube.base.third.wx.response;

/**
 * Description:微信扫码支付
 * Author:zhanglida
 * Date:2017/3/22
 * Email:406504302@qq.com
 */
public class WxQrCodePayResponse extends WxBasePayResponse {
    //二维码地址
    private String code_url="";

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }
}
