package cn.cube.base.third.wx.request;

/**
 * Description:token校验请求
 * Author:zhanglida
 * Date:2018/9/25
 * Email:406504302@qq.com
 */
public class WxTokenCheckRequest {
    /**
     * 签名
     */
    String signature ;
    /**
     * 随机字符串
     */
    String echostr ;
    /**
     * 时间戳
     */
    String timestamp  ;
    /**
     * 随机数
     */
    String nonce  ;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }
}
