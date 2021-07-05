package cn.cube.base.third.wx.response;

/**
 * Description:微信用户手机号
 * Author:zhanglida
 * Date:2020/8/11
 * Email:406504302@qq.com
 */
public class WxPhoneResponse {
    private String phoneNumber;
    private String purePhoneNumber;
    private String countryCode;
    private String appid;

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPurePhoneNumber() {
        return purePhoneNumber;
    }

    public void setPurePhoneNumber(String purePhoneNumber) {
        this.purePhoneNumber = purePhoneNumber;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
