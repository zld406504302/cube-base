package cn.cube.base.third.pay;

/**
 * Description:支付平台
 * Author:zhanglida
 * Date:2019/11/19
 * Email:406504302@qq.com
 */
public enum PayPlatform {
    OPEN(1,"开放平台支付"), MP(2,"公众号支付"), MINI(3,"小程序支付");
    public final Integer value;
    public final String desc;
    PayPlatform(Integer value,String desc) {
        this.value = value;
        this.desc = desc;
    }
}
