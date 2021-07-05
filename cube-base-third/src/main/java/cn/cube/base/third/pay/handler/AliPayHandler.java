package cn.cube.base.third.pay.handler;//package cn.cube.base.pay.handler;
//
//import cn.cube.base.core.util.JsonUtil;
//import cn.cube.base.core.util.LoggerUtils;
//import cn.cube.base.pay.PayPlatform;
//import cn.cube.base.pay.PayStatus;
//import cn.cube.base.pay.api.ali.AliPayApi;
//import cn.cube.base.pay.api.ali.AliPayConstants;
//import cn.cube.base.pay.request.*;
//import cn.cube.base.pay.response.*;
//import cn.cube.base.pay.utils.PayLogPrinterUtil;
//import com.alipay.api.AlipayApiException;
//import com.alipay.api.AlipayResponse;
//import com.alipay.api.domain.*;
//import com.alipay.api.response.*;
//import org.slf4j.Logger;
//
///**
// * Description:支付宝支付handler
// * Author:zhanglida
// * Date:2017/4/19
// * Email:406504302@qq.com
// */
//public class AliPayHandler implements IPayHandler {
//    private static final Logger logger = LoggerUtils.getLogger(AliPayHandler.class);
//
////    @Override
////    public AliAppPayResponse appPay(PayCreateRequest request) {
////        AliAppPayResponse response = new AliAppPayResponse();
////
////        AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
////        model.setBody(request.getBody());
////        model.setSubject(request.getTitle());
////        model.setOutTradeNo(request.getStrInTradeNo());
////        model.setTotalAmount(request.getYuanAmount());
////        PayLogPrinterUtil.printRequest(logger, PayPlatform.ALI, "appPay",request.getAppId(), request.getUid(), JsonUtil.toJson(model));
////
////        try {
////
////            AlipayTradeAppPayResponse res = AliPayApi.appPay(request.getAppId(),model);
////            String body = JsonUtil.toJson(res);
////
////            PayLogPrinterUtil.printResponse(logger, PayPlatform.ALI, "appPay",request.getAppId(), request.getUid(), body);
////
////            initResponse(response, request, res, body);
////            response.setOrderStr(res.getBody());
////
////        } catch (AlipayApiException e) {
////            response.setError(e.getErrCode(), e.getErrMsg());
////            PayLogPrinterUtil.printError(logger, PayPlatform.ALI, "appPay", request.getAppId(),request.getUid(), e.getErrCode(), e.getErrMsg());
////        }
////        return response;
////    }
////
////    @Override
////    public PayResponse jsPay(PayCreateRequest request) {
////        throw new UnsupportedOperationException("支付宝js支付暂未开启");
////    }
////
////
////    @Override
////    public QrCodePayResponse qrCodePay(PayCreateRequest request) {
////        QrCodePayResponse response = new QrCodePayResponse();
////
////        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
////        model.setBody(request.getBody());
////        model.setSubject(request.getTitle());
////        model.setOutTradeNo(request.getStrInTradeNo());
////        model.setTotalAmount(request.getYuanAmount());
////        PayLogPrinterUtil.printRequest(logger, PayPlatform.ALI, "qrCodePay",request.getAppId(), request.getUid(), JsonUtil.toJson(model));
////        try {
////            AlipayTradePrecreateResponse res = AliPayApi.qrCodePay(request.getAppId(),model);
////            String body = JsonUtil.toJson(res);
////
////            PayLogPrinterUtil.printResponse(logger, PayPlatform.ALI, "qrCodePay", request.getAppId(),request.getUid(), body);
////
////            initResponse(response, request, res, body);
////            response.setCodeUrl(res.getQrCode());
////
////        } catch (AlipayApiException e) {
////            response.setError(e.getErrCode(), e.getErrMsg());
////            PayLogPrinterUtil.printError(logger, PayPlatform.ALI, "qrCodePay",request.getAppId(), request.getUid(), e.getErrCode(), e.getErrMsg());
////        }
////        return response;
////    }
////
////    @Override
////    public PayQueryResponse query(PayQueryRequest request) {
////        PayQueryResponse response = new PayQueryResponse();
////
////        AlipayTradeQueryModel model = new AlipayTradeQueryModel();
////        model.setOutTradeNo(request.getStrInTradeNo());
////        PayLogPrinterUtil.printRequest(logger, PayPlatform.ALI, "query",request.getAppId(), request.getUid(), JsonUtil.toJson(model));
////        try {
////            AlipayTradeQueryResponse res = AliPayApi.query(request.getAppId(),model);
////            String body = JsonUtil.toJson(res);
////
////            PayLogPrinterUtil.printResponse(logger, PayPlatform.ALI, "query",request.getAppId(), request.getUid(), body);
////
////            initResponse(response, request, res, body);
////            PayStatus status = parse(res.getTradeStatus());
////            response.setStatus(status);
////        } catch (AlipayApiException e) {
////            response.setError(e.getErrCode(), e.getErrMsg());
////            PayLogPrinterUtil.printError(logger, PayPlatform.ALI, "query",request.getAppId(), request.getUid(), e.getErrCode(), e.getErrMsg());
////        }
////
////        return response;
////    }
////
////    @Override
////    public PayCloseResponse close(PayCloseRequest request) {
////        PayCloseResponse response = new PayCloseResponse();
////
////        AlipayTradeCloseModel model = new AlipayTradeCloseModel();
////        model.setOutTradeNo(request.getStrInTradeNo());
////        PayLogPrinterUtil.printRequest(logger, PayPlatform.ALI, "close", request.getAppId(),request.getUid(), JsonUtil.toJson(model));
////
////        try {
////            AlipayTradeCloseResponse res = AliPayApi.close(request.getAppId(),model);
////            String body = JsonUtil.toJson(res);
////
////            PayLogPrinterUtil.printResponse(logger, PayPlatform.ALI, "close",request.getAppId(), request.getUid(), body);
////
////            initResponse(response, request, res, body);
////        } catch (AlipayApiException e) {
////            response.setError(e.getErrCode(), e.getErrMsg());
////            PayLogPrinterUtil.printError(logger, PayPlatform.ALI, "close",request.getAppId(), request.getUid(), e.getErrCode(), e.getErrMsg());
////        }
////
////        return response;
////    }
////
////    @Override
////    public RefundResponse refund(RefundRequest request) {
////        RefundResponse response = new RefundResponse();
////
////        AlipayTradeRefundModel model = new AlipayTradeRefundModel();
////        model.setOutTradeNo(request.getStrInTradeNo());
////        model.setRefundAmount(request.getYuanAmount());
////        model.setRefundReason(request.getReason());
////        PayLogPrinterUtil.printRequest(logger, PayPlatform.ALI, "refund",request.getAppId(), request.getUid(), JsonUtil.toJson(model));
////
////        try {
////            AlipayTradeRefundResponse res = AliPayApi.refund(request.getAppId(),model);
////            String body = JsonUtil.toJson(res);
////
////            PayLogPrinterUtil.printResponse(logger, PayPlatform.ALI, "refund",request.getAppId(), request.getUid(), body);
////
////            initResponse(response, request, res, body);
////        } catch (AlipayApiException e) {
////            response.setError(e.getErrCode(), e.getErrMsg());
////            PayLogPrinterUtil.printError(logger, PayPlatform.ALI, "refund",request.getAppId(), request.getUid(), e.getErrCode(), e.getErrMsg());
////        }
////
////        return response;
////    }
////
////    @Override
////    public RefundQueryResponse refundQuery(RefundQueryRequest request) {
////        RefundQueryResponse response = new RefundQueryResponse();
////
////        AlipayTradeFastpayRefundQueryModel model = new AlipayTradeFastpayRefundQueryModel();
////        model.setOutTradeNo(request.getStrInTradeNo());
////        PayLogPrinterUtil.printRequest(logger, PayPlatform.ALI, "refundQuery",request.getAppId(), request.getUid(), JsonUtil.toJson(model));
////
////        try {
////            AlipayTradeFastpayRefundQueryResponse res = AliPayApi.refundQuery(request.getAppId(),model);
////            String body = JsonUtil.toJson(res);
////
////            PayLogPrinterUtil.printResponse(logger, PayPlatform.ALI, "refundQuery",request.getAppId(), request.getUid(), body);
////
////            initResponse(response, request, res, body);
////        } catch (AlipayApiException e) {
////            response.setError(e.getErrCode(), e.getErrMsg());
////            PayLogPrinterUtil.printError(logger, PayPlatform.ALI, "refundQuery",request.getAppId(), request.getUid(), e.getErrCode(), e.getErrMsg());
////        }
////
////        return response;
////    }
////
////
////    private void initResponse(PayResponse payResponse, PayRequest request, AlipayResponse alipayResponse, String body) {
////        payResponse.setSuccess(alipayResponse.isSuccess());
////        payResponse.setErrorCode(alipayResponse.getSubCode());
////        payResponse.setErrorMsg(alipayResponse.getSubMsg());
////        payResponse.setInTradeNo(request.getInTradeNo());
////        payResponse.setUid(request.getUid());
////        payResponse.setPlatform(request.getPlatform());
////        payResponse.setBody(body);
////    }
////
////    /**
////     * 将支付订单状态转换为平台内部状态
////     *
////     * @param aliStatus
////     * @return
////     */
////    private PayStatus parse(String aliStatus) {
////        if (AliPayConstants.TradeStatus.WAIT_BUYER_PAY.value.equals(aliStatus)) {
////            return PayStatus.WAIT;
////        } else if (AliPayConstants.TradeStatus.TRADE_CLOSED.value.equals(aliStatus)) {
////            return PayStatus.CLOSE;
////        } else if (AliPayConstants.TradeStatus.TRADE_SUCCESS.value.equals(aliStatus)) {
////            return PayStatus.SUCCESS;
////        } else if (AliPayConstants.TradeStatus.TRADE_FINISHED.value.equals(aliStatus)) {
////            return PayStatus.CLOSE;
////        }
////        return PayStatus.WAIT;
////    }
////
////
////    @Override
////    public PayResponse jsPay(PayRequest request) {
////        return null;
////    }
////
////    @Override
////    public PayResponse payQuery(PayRequest request) {
////        return null;
////    }
////
////    @Override
////    public WithdrawResponse transferWallet(TransferWalletRequest request) {
////        return null;
////    }
////
////    @Override
////    public WithdrawQueryResponse transferWalletQuery(PayRequest request) {
////        return null;
////    }
//}
