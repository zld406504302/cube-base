package cn.cube.base.third.pay.adapter;

import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.third.pay.PayChannel;
import cn.cube.base.third.pay.exception.PaymentErrorCode;
import cn.cube.base.third.pay.handler.IPayHandler;
import cn.cube.base.third.pay.handler.WxPayHandler;
import cn.cube.base.third.pay.request.PayRequest;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Description:支付适配器
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public class PayAdapter {
    private static final Map<PayChannel, IPayHandler> payHandlers = Maps.newHashMap();
    static {
        payHandlers.put(PayChannel.WX, new WxPayHandler());
    }
    public static IPayHandler handler(PayRequest payRequest) {

        if (null == payRequest) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR, "request不能为空");
        }
        payRequest.check();

        IPayHandler payHandler = payHandlers.get(payRequest.getPayChannel());
        if (null == payHandler) {
            throw new BusinessException(PaymentErrorCode.PAY_PARAM_ERROR,  "payPlatform 不能为空");
        }
        return payHandler;
    }
}
