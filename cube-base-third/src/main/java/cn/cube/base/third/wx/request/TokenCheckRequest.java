package cn.cube.base.third.wx.request;

import cn.cube.base.core.util.MD5;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Description:TokenCheckRequest
 * Author:zhanglida
 * Date:2018/3/31
 * Email:406504302@qq.com
 */
public class TokenCheckRequest {
    private String signature;
    private String timestamp;
    private String nonce;
    private String echostr;

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
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

    public String getEchostr() {
        return echostr;
    }

    public void setEchostr(String echostr) {
        this.echostr = echostr;
    }

    public boolean checkSign(String token) throws NoSuchAlgorithmException {
        String[] str = {token, timestamp, nonce};
        Arrays.sort(str);
        String content = str[0] + str[1] + str[2];
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(content.getBytes());
        String sign = MD5.byteArrayToHexString(digest);
        // 确认请求来至微信
        return sign.equals(signature);
    }
}
