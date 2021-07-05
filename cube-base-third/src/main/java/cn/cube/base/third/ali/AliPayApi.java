package cn.cube.base.third.ali;


import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Description:支付宝支付util
 * <p>
 * 该工具类提供了 app支付订单创建 扫码支付订单创建 订单查询 订单关闭 退款  退款查询 6个方法
 * 该工具类需配合{@link AliPayConfigure}一起使用,请在系统.properties文件中
 * 设置对应的配置参数
 * </p>
 *
 * <h3>关键字段描述</h3>
 * <ul>
 * <li>trade_no 支付宝订单号</li>
 * <li>out_trade_no 商户订单号</li>
 * <li>notify_url通知地址</li>
 * <li>total_amount 支付金额单位为元，精确到2位小数</li>
 * <li>timeout_express 支付订单超时时间</li>
 * </ul>
 * Author:zhanglida
 * Date:2017/3/21
 * Email:406504302@qq.com
 */
public class AliPayApi {

    /**
     * 生成app支付订单
     *
     * <h3>必填参数</h3>
     * out_trade_no 商户订单号
     * total_amount 订单金额，单位元 精确到2位小数
     * subject 订单标题
     *
     * <h3>非必填参数</h3>
     * body 订单描述
     *
     * @param model app支付订单所需参数
     * @return AlipayTradeAppPayResponse
     * @throws AlipayApiException
     */
    public static AlipayTradeAppPayResponse appPay(String appId, AlipayTradeAppPayModel model) throws AlipayApiException {
        AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
        request.setNotifyUrl(AliPayConfigure.getNotifyUrl());
        model.setTimeoutExpress(AliPayConfigure.getTimeOut());
        request.setBizModel(model);
        AlipayTradeAppPayResponse response = client.sdkExecute(request);
        return response;
    }

    /**
     * 生成扫描支付的二维码支付订单
     *
     * <h3>参数必填</h3>
     * out_trade_no 商户订单号
     * total_amount 订单金额，单位元 精确到2位小数
     * subject 订单标题
     *
     * @param model 扫码支付请求参数
     * @return AlipayTradePrecreateResponse
     * @throws AlipayApiException
     */
    public static AlipayTradePrecreateResponse qrCodePay(String appId, AlipayTradePrecreateModel model) throws AlipayApiException {
        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        request.setNotifyUrl(AliPayConfigure.getNotifyUrl());
        model.setTimeoutExpress(AliPayConfigure.getTimeOut());
        request.setBizModel(model);
        AlipayTradePrecreateResponse response = client.execute(request);
        return response;
    }

    /**
     * 订单查询
     *
     * out_trade_no 和 trade_no 不能同时为空
     *
     * @param model 订单查询model
     * @return AlipayTradePrecreateResponse
     * @throws AlipayApiException
     */
    public static AlipayTradeQueryResponse query(String appId, AlipayTradeQueryModel model) throws AlipayApiException {
        AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
        request.setBizModel(model);
        AlipayTradeQueryResponse response = client.execute(request);
        return response;
    }

    /**
     * 订单关闭
     *
     * out_trade_no 和 trade_no 不能同时为空
     *
     * @param model 订单查询model
     * @return AlipayTradePrecreateResponse
     * @throws AlipayApiException
     */
    public static AlipayTradeCloseResponse close(String appId, AlipayTradeCloseModel model) throws AlipayApiException {
        AlipayTradeCloseRequest request = new AlipayTradeCloseRequest();
        request.setBizModel(model);
        AlipayTradeCloseResponse response = client.execute(request);
        return response;
    }

    /**
     * 退款
     *
     * trade_no 和 out_trade_no 不能同时为空
     * refund_amount 退款金额（不能大于实际订单金额）
     *
     * @param model 订单查询model
     * @return AlipayTradePrecreateResponse
     * @throws AlipayApiException
     */
    public static AlipayTradeRefundResponse refund(String appId, AlipayTradeRefundModel model) throws AlipayApiException {
        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();
        request.setBizModel(model);
        AlipayTradeRefundResponse response = client.execute(request);
        return response;
    }

    /**
     * 退款查询
     *
     * trade_no 和 out_trade_no 不能同时为空
     *
     * @param model 订单查询model
     * @return AlipayTradeFastpayRefundQueryModel
     * @throws AlipayApiException
     */
    public static AlipayTradeFastpayRefundQueryResponse refundQuery(String appId,AlipayTradeFastpayRefundQueryModel model) throws AlipayApiException {
        AlipayTradeFastpayRefundQueryRequest request = new AlipayTradeFastpayRefundQueryRequest();
        request.setBizModel(model);
        AlipayTradeFastpayRefundQueryResponse response = client.execute(request);
        return response;
    }

    /**
     * 异步通知签名确认
     *
     * @param requestParams 支付宝通知请求参数
     * @return true:眼前成功 false:验签失败
     * @throws AlipayApiException
     */
    public static boolean checkSign(Map requestParams) throws AlipayApiException {
        Map<String, String> params = new HashMap<>();
        for (Iterator iterator = requestParams.keySet().iterator(); iterator.hasNext(); ) {
            String name = (String) iterator.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean flag = AlipaySignature.rsaCheckV1(params, AliPayConfigure.getPublicKey(), AliPayConfigure.getCharset(), AliPayConfigure.getSignType());
        return flag;
    }

    private static AlipayClient client = new DefaultAlipayClient(AliPayConfigure.getGateway(), AliPayConfigure.getAppId(), AliPayConfigure.getPrivateKey(), "json", AliPayConfigure.getCharset(), AliPayConfigure.getPublicKey(), AliPayConfigure.getSignType());



}
