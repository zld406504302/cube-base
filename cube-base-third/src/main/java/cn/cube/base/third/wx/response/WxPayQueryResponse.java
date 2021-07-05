package cn.cube.base.third.wx.response;

import java.util.Map;

/**
 * Description:订单查询
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
public class WxPayQueryResponse extends WxPayResponse implements IWxPayResponse{

    private String trade_state = "";

    public String getTrade_state() {
        return trade_state;
    }

    public void setTrade_state(String trade_state) {
        this.trade_state = trade_state;
    }

    @Override
    public Map<String, String> getPayParam() {
        return null;
    }
}
