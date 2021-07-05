package cn.cube.base.third.wx.request;

/**
 * Description:微信pay req
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public interface IWxBasePayRequest {
    /**
     * 生成请求签名
     */
    void sign(String key);
}
