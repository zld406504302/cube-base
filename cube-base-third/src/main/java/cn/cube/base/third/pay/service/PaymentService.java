package cn.cube.base.third.pay.service;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.*;
import cn.cube.base.third.pay.adapter.PayAdapter;
import cn.cube.base.third.pay.dao.UserPayOrderDao;
import cn.cube.base.third.pay.entity.UserPayOrder;
import cn.cube.base.third.pay.exception.PaymentErrorCode;
import cn.cube.base.third.pay.handler.IPayHandler;
import cn.cube.base.third.pay.request.*;
import cn.cube.base.third.pay.response.NoticeResponse;
import cn.cube.base.third.pay.response.PayResponse;
import cn.cube.base.third.pay.response.RefundResponse;
import cn.cube.base.third.pay.response.WxMpPayResponse;
import cn.cube.base.third.wx.WxApi;
import cn.cube.base.third.wx.WxPayClientHolder;
import cn.cube.base.third.wx.XMLParser;
import cn.cube.base.third.wx.request.WxPayNoticeRequest;
import cn.cube.base.third.wx.response.WxPayNoticeResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Description:支付通知service
 * Author:zhanglida
 * Date:2017/4/21
 * Email:406504302@qq.com
 */
@Service
public class PaymentService implements IPaymentService {
    public static final Logger logger = LoggerUtils.getLogger(PaymentService.class);

    @Autowired
    private UserPayOrderDao userPayOrderDao;

    @Override
    public PayResponse create(PayRequest request) {
        ValidatorUtil.validate(request);

        UserPayOrder userPayOrder = newUserPayOrder(request);
        userPayOrderDao.insert(userPayOrder);
        request.setInTradeNo(userPayOrder.getId());

        IPayHandler handler = PayAdapter.handler(request);
        WxMpPayResponse response = (WxMpPayResponse) handler.jsPay(request);
        if (response.isSuccess()) {
            return response;
        } else {
            doFail(request, userPayOrder, handler, response);
        }
        return response;
    }

    @Override
    public RefundResponse refund(RefundRequest request) {
        ValidatorUtil.validate(request);

        UserPayOrder select = userPayOrderDao.select(request.getInTradeNo());
        if (null == select) {
            throw new BusinessException(PaymentErrorCode.PAY_ORDER_IS_ABSENT);
        }

        if (select.getStatus() != UserPayOrder.STATUS_SUCCESS) {
            throw new BusinessException(PaymentErrorCode.PAY_REFUND_FAIL);
        }
        request.setAmount(select.getAmount());

        int result = userPayOrderDao.update(select.getId(), UserPayOrder.STATUS_REFUND, UserPayOrder.STATUS_SUCCESS);
        if (result <= 0) {
            throw new BusinessException(PaymentErrorCode.PAY_REFUND_FAIL);
        }
        IPayHandler handler = PayAdapter.handler(request);
        return handler.refund(request);

    }

    @Override
    public PayResponse sharingAdd(SharingAddRequest request) {
        ValidatorUtil.validate(request);

        UserPayOrder select = userPayOrderDao.select(request.getInTradeNo());
        if (null == select) {
            throw new BusinessException(PaymentErrorCode.PAY_ORDER_IS_ABSENT);
        }

        if (select.getStatus() != UserPayOrder.STATUS_SUCCESS) {
            throw new BusinessException(PaymentErrorCode.PAY_SHARING_FAIL);
        }
        request.setAmount(select.getAmount());

        IPayHandler handler = PayAdapter.handler(request);
        return handler.sharingAdd(request);
    }

    @Override
    public PayResponse sharingReq(SharingReqRequest request) {
        ValidatorUtil.validate(request);

        UserPayOrder select = userPayOrderDao.select(request.getInTradeNo());
        if (null == select) {
            throw new BusinessException(PaymentErrorCode.PAY_ORDER_IS_ABSENT);
        }

        if (select.getStatus() != UserPayOrder.STATUS_SUCCESS) {
            throw new BusinessException(PaymentErrorCode.PAY_SHARING_FAIL);
        }
        request.setTransactionId(select.getTradeNo());

        IPayHandler handler = PayAdapter.handler(request);
        return handler.sharingReq(request);
    }

    private UserPayOrder newUserPayOrder(PayRequest request) {
        UserPayOrder userPayOrder = new UserPayOrder();
        Beans.copy(request, userPayOrder);
        userPayOrder.setChannel(request.getPayChannel().value);
        userPayOrder.setPlatform(request.getPayPlatform().value);
        userPayOrder.setShare(request.getProfitSharing());
        return userPayOrder;
    }

    private void doFail(PayRequest request, UserPayOrder inOrder, IPayHandler handler, WxMpPayResponse response) {
        if (response.isUnknowFail()) {
            logger.warn("jspay fail uid:{} cause:isUnknowFail", inOrder.getUid());
            PayResponse queryResponse = handler.payQuery(request);
            if (queryResponse.isSuccess()) {
                response.setSuccess(true);
                response.setError("", "");
                return;
            }
        }
        if (!response.isSuccess()) {
            inOrder.setStatus(UserPayOrder.STATUS_FAILURE);
            inOrder.setError(response.getErrorMsg());
            userPayOrderDao.update(inOrder);
        }
    }


    @Override
    public NoticeResponse notice(NoticeRequest request) {
        ValidatorUtil.validate(request);
        NoticeResponse res = new NoticeResponse();
        res.setNoticeReply(WxPayNoticeResponse.success());
        String streamString = (String) request.getNotice();
        logger.info("payment-notice wxpay param:{}", JsonUtil.toJson(request));
        Map<String, String> map = XMLParser.getMapFromXML(streamString);
        String tradeNo = map.get("out_trade_no");
        if (StringUtils.isEmpty(tradeNo)) {
            logger.error("payment-notice wxpay out_trade_no is null");
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, "订单号为空");
        }
        long tradeNoL = Long.parseLong(tradeNo);
        res.setInTradeNo(tradeNoL);
        UserPayOrder payOrder = userPayOrderDao.select(tradeNoL);
        if (null == payOrder) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, "订单[" + tradeNoL + "]不存在");
        }
        String key = WxPayClientHolder.client(request.getPayPlatform(), payOrder.getAppId()).getConfig().getKey();

        UserPayOrder param = new UserPayOrder();
        param.setId(tradeNoL);
        param.setStatus(UserPayOrder.STATUS_SUCCESS);
        boolean success = WxApi.checkSign(key, streamString);
        if (!success) {
            logger.error("payment-notice wxpay out_trade_no[{}] is check sign fail", tradeNo);
            param.setStatus(UserPayOrder.STATUS_FAILURE);
            param.setError("验证签名失败");
        }
        WxPayNoticeRequest req = WxPayNoticeRequest.newInstance(streamString);
        logger.info("payment-notice wxpay WxPayNoticeRequest[{}]", JsonUtil.toJson(req));
        if (null == req) {
            logger.error("payment-notice wxpay out_trade_no[{}] notice extract fail", tradeNo);
            param.setStatus(UserPayOrder.STATUS_FAILURE);
            param.setError("请求内容解析失败");
        } else if (!req.isSuccess()) {
            logger.error("payment-notice wxpay out_trade_no[{}] notice fail,cause[{}]", tradeNo, req.getErr_code_des());
            param.setStatus(UserPayOrder.STATUS_FAILURE);
            param.setError(req.getErr_code_des());
        }
        param.setTradeNo(req.getTransaction_id());
        userPayOrderDao.update(param);
        if (!StringUtils.isEmpty(param.getError())) {
            res.setNoticeReply(WxPayNoticeResponse.fail(param.getError()));
        }
        logger.info("payment-notice wxpay notice result:{}", JsonUtil.toJson(res));
        return res;
    }


}
