package cn.cube.base.third.wx.request;

/**
 * Description:微信解密请求
 * Author:zhanglida
 * Date:2020/8/11
 * Email:406504302@qq.com
 */
public class WxDecryptRequest {
    private String encryptedData;
    private String sessionKey;
    private String ivData;
    private String code ;

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getIvData() {
        return ivData;
    }

    public void setIvData(String ivData) {
        this.ivData = ivData;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
