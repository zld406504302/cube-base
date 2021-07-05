package cn.cube.base.third.pay;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:支付方式
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public enum PayMethod {
    QR_CODE("二维码支付", 1),
    APP("APP支付", 2),
    JS("JS支付", 4),
    WITHDRAW("提现", 6),
    ;
    private String name;
    private Integer value;

    static Map<Integer, PayMethod> map = new HashMap<>();

    static {
        for (PayMethod method : PayMethod.values()) {
            map.put(method.value, method);
        }
    }

    PayMethod(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public static PayMethod valueOf(Integer value) {
        return map.get(value);
    }

}