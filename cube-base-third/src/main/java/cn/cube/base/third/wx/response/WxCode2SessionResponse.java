package cn.cube.base.third.wx.response;

/**
 * Description:微信用户会话信息
 * Author:zhanglida
 * Date:2020/8/11
 * Email:406504302@qq.com
 */
public class WxCode2SessionResponse extends  WxBaseResponse {
    private String openid;
    private String session_key;
    private String unionid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
