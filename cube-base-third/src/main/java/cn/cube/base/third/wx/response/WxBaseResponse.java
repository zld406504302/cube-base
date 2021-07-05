package cn.cube.base.third.wx.response;

import cn.cube.base.core.util.StringUtils;

/**
 * Description:WxBaseResponse
 * Author:zhanglida
 * Date:2018/3/31
 * Email:406504302@qq.com
 */
public class WxBaseResponse {
    private String errcode ;
    private String errmsg ;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean isSuccess(){
        return StringUtils.isEmpty(errcode) || "0".equals(errcode);
    }
}
