package cn.cube.base.third.pay.response;

/**
 * Description:支付宝app支付response
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class AliAppPayResponse extends PayResponse implements IPayResponse {

    private String orderStr;

    public String getOrderStr() {
        return orderStr;
    }

    public void setOrderStr(String orderStr) {
        this.orderStr = orderStr;
    }

    @Override
    public String toString() {
        return super.toString() + "AliAppPayResponse{" +
                "orderStr='" + orderStr + '\'' +
                '}';
    }


    @Override
    public Object getPayData() {
        return null;
    }
}
