package cn.cube.base.third.pay.handler;

import cn.cube.base.core.util.DateUtils;
import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.third.pay.PayChannel;
import cn.cube.base.third.pay.PayStatus;
import cn.cube.base.third.pay.exception.PaymentErrorCode;
import cn.cube.base.third.pay.request.*;
import cn.cube.base.third.pay.response.*;
import cn.cube.base.third.pay.utils.PayLogPrinterUtil;
import cn.cube.base.third.wx.*;
import cn.cube.base.third.wx.request.*;
import cn.cube.base.third.wx.response.*;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Description:微信支付Handler
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
@Component
public class WxPayHandler implements IPayHandler {
    private static final Logger logger = LoggerUtils.getLogger(WxPayHandler.class);

    @Override
    public PayResponse jsPay(PayRequest request) {
        WxMpPayResponse response = new WxMpPayResponse();
        PayLogPrinterUtil.printRequest(logger, PayChannel.WX, "jsPay",request.getAppId(), request.getUid(), JsonUtil.toJson(request));
        WxUnifiedPayRequest req = new WxUnifiedPayRequest();
        req.setOpenid(request.getOpenid());
        req.setOut_trade_no(request.getStrInTradeNo());
        req.setTotal_fee(String.valueOf(request.getAmount()));
        req.setBody(request.getBody());
        req.setNotify_url(request.getNotifyUrl());
        if (request.getProfitSharing()) {
            req.setProfit_sharing(WxUnifiedPayRequest.PROFIT_SHARING_Y);
        }
        WxPayClient client = WxPayClientHolder.client(request.getPayPlatform(), request.getAppId());
        Date expireTime = client.getConfig().getExpireTime();
        req.setTime_expire(DateUtils.format(expireTime,"yyyyMMddHHmmss"));

        try {
            WxUnifiedPayResponse res = WxApi.jsPay(request.getAppId(),request.getPayPlatform(), req);
            String body = JsonUtil.toJson(res);
            PayLogPrinterUtil.printResponse(logger, PayChannel.WX, "jsPay",request.getAppId(), request.getUid(), body);
            response = new WxMpPayResponse(request, res);
            response.setPayData(res.getPayParam());
        } catch (Exception e) {
            response.setError(PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
            PayLogPrinterUtil.printError(logger, PayChannel.WX, "jsPay",request.getAppId(), request.getThdUid(), PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
        }
        return response;
    }

    @Override
    public PayResponse payQuery(PayRequest request) {
        PayResponse response = new PayResponse();
        PayLogPrinterUtil.printRequest(logger, PayChannel.WX, "payQuery",request.getAppId(), request.getUid(), JsonUtil.toJson(request));
        WxPayQueryRequest req = new WxPayQueryRequest();
        req.setOut_trade_no(request.getStrInTradeNo());
        try {
            WxPayQueryResponse res = WxApi.payQuery(request.getAppId(), req);
            String body = JsonUtil.toJson(res);
            PayLogPrinterUtil.printResponse(logger, PayChannel.WX, "payQuery",request.getAppId(), request.getUid(), body);
            response = new PayResponse(request, res);
        } catch (Exception e) {
            response.setError(PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
            PayLogPrinterUtil.printError(logger, PayChannel.WX, "payQuery",request.getAppId(), request.getThdUid(), PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
        }
        return response;
    }

    @Override
    public WithdrawResponse transferWallet(TransferWalletRequest request) {
        WithdrawResponse response = new WithdrawResponse();
        PayLogPrinterUtil.printRequest(logger, PayChannel.WX, "transferWallet",request.getAppId(), request.getUid(), JsonUtil.toJson(request));
        WxTransferWalletRequest req = new WxTransferWalletRequest();
        req.setAmount(request.getAmount());
        req.setDesc(request.getDesc());
        req.setOpenid(request.getOpenid());
        req.setPartner_trade_no(String.valueOf(request.getInTradeNo()));
        req.setSpbill_create_ip(request.getSpbill_create_ip());
        try {
            WxTransferWalletResponse res = WxApi.transferWallet(request.getAppId(),req);
            String body = JsonUtil.toJson(res);
            PayLogPrinterUtil.printResponse(logger, PayChannel.WX, "transferWallet",request.getAppId(), request.getUid(), body);
            response = new WithdrawResponse(request, res);

        } catch (Exception e) {
            response.setError(PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
            PayLogPrinterUtil.printError(logger, PayChannel.WX, "transferWallet",request.getAppId(), request.getUid(), PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
        }
        return response;
    }

    @Override
    public WithdrawQueryResponse transferWalletQuery(PayRequest request) {
        WithdrawQueryResponse response = new WithdrawQueryResponse();
        WxTransferWalletQueryRequest req = new WxTransferWalletQueryRequest();
        req.setPartner_trade_no(request.getStrInTradeNo());

        PayLogPrinterUtil.printRequest(logger, PayChannel.WX, "transferWalletQuery",request.getAppId(), request.getUid(), JsonUtil.toJson(request));

        try {
            WxTransferWalletQueryResponse res = WxApi.transferWalletQuery(request.getAppId(),req);
            String body = JsonUtil.toJson(res);
            PayLogPrinterUtil.printResponse(logger, PayChannel.WX, "transferWalletQuery",request.getAppId(), request.getUid(), body);
            response = new WithdrawQueryResponse(request,res);

        } catch (Exception e) {
            response.setError(e.getMessage());
            PayLogPrinterUtil.printError(logger, PayChannel.WX, "transferWalletQuery",request.getAppId(), request.getUid(), PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
        }
        return response;
    }

    @Override
    public RefundResponse refund(RefundRequest request) {
        RefundResponse response = new RefundResponse();
        WxRefundRequest req = new WxRefundRequest();
        req.setOut_trade_no(request.getStrInTradeNo());
        req.setOut_refund_no(request.getStrInTradeNo());
        req.setTotal_fee(String.valueOf(request.getAmount()));
        req.setRefund_fee(req.getTotal_fee());
        PayLogPrinterUtil.printRequest(logger, PayChannel.WX, "refund",request.getAppId(), request.getUid(), JsonUtil.toJson(request));
        try {
            WxRefundResponse res = WxApi.mpRefund(request.getAppId(),request.getPayPlatform(),req);
            String body = JsonUtil.toJson(res);
            PayLogPrinterUtil.printResponse(logger, PayChannel.WX, "refund",request.getAppId(), request.getUid(), body);
            response = new RefundResponse(request,res);

        } catch (Exception e) {
            response.setError(e.getMessage());
            PayLogPrinterUtil.printError(logger, PayChannel.WX, "refund",request.getAppId(), request.getUid(), PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
        }
        return response;
    }

    @Override
    public PayResponse sharingAdd(SharingAddRequest request) {
        PayResponse response = new PayResponse();
        WxSharingAddRequest req = new WxSharingAddRequest();
        req.setReceiver(request.getOpenid());
        PayLogPrinterUtil.printRequest(logger, PayChannel.WX, "sharingAdd",request.getAppId(), request.getUid(), JsonUtil.toJson(request));
        try {
            WxPayResponse res = WxApi.addSharing(request.getAppId(),request.getPayPlatform(),req);
            String body = JsonUtil.toJson(res);
            PayLogPrinterUtil.printResponse(logger, PayChannel.WX, "sharingAdd",request.getAppId(), request.getUid(), body);
            response = new PayResponse(request,res);

        } catch (Exception e) {
            response.setError(e.getMessage());
            PayLogPrinterUtil.printError(logger, PayChannel.WX, "sharingAdd",request.getAppId(), request.getUid(), PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
        }
        return response;
    }

    @Override
    public PayResponse sharingReq(SharingReqRequest request) {
        PayResponse response = new PayResponse();
        WxSharingReqRequest req = new WxSharingReqRequest();
        req.setOut_order_no(request.getStrInTradeNo());
        req.setReceiver(request.getOpenid(),request.getAmount(),request.getBody());
        req.setTransaction_id(request.getTransactionId());
        PayLogPrinterUtil.printRequest(logger, PayChannel.WX, "sharingReq",request.getAppId(), request.getUid(), JsonUtil.toJson(request));
        try {
            WxPayResponse res = WxApi.reqSharing(request.getAppId(),request.getPayPlatform(),req);
            String body = JsonUtil.toJson(res);
            PayLogPrinterUtil.printResponse(logger, PayChannel.WX, "sharingReq",request.getAppId(), request.getUid(), body);
            response = new PayResponse(request,res);

        } catch (Exception e) {
            response.setError(e.getMessage());
            PayLogPrinterUtil.printError(logger, PayChannel.WX, "sharingReq",request.getAppId(), request.getUid(), PaymentErrorCode.PAY_ERROR.getCode() + "", e.getMessage());
        }
        return response;
    }

}
