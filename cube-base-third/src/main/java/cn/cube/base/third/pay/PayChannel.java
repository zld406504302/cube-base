package cn.cube.base.third.pay;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Description:支付渠道
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public enum PayChannel {
    WX("微信", 1),
    ALI("支付宝", 2),
    ;
    public final String name;
    public final int value;

    private static Map<Integer, PayChannel> map = Maps.newHashMap();

    static {
        for (PayChannel platform : PayChannel.values()) {
            map.put(platform.value, platform);
        }
    }

    PayChannel(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public static PayChannel valueOf(Integer value) {
        return map.get(value);
    }
}
