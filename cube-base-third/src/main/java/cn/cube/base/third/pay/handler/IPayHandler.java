package cn.cube.base.third.pay.handler;


import cn.cube.base.third.pay.PayPlatform;
import cn.cube.base.third.pay.request.*;
import cn.cube.base.third.pay.response.PayResponse;
import cn.cube.base.third.pay.response.RefundResponse;
import cn.cube.base.third.pay.response.WithdrawQueryResponse;
import cn.cube.base.third.pay.response.WithdrawResponse;

/**
 * Description:支付handler
 * Author:zhanglida
 * Date:2017/4/19
 * Email:406504302@qq.com
 */
public interface IPayHandler {

    /**
     * 公众号支付
     *
     * @param request
     * @return
     */
    PayResponse jsPay(PayRequest request);

    /**
     * 支付查询
     *
     * @param request
     * @return
     */
    PayResponse payQuery(PayRequest request);

    /**
     * 企业付款到零钱
     */
    WithdrawResponse transferWallet(TransferWalletRequest request);

    /**
     * 企业付款到零钱查询
     */
    WithdrawQueryResponse transferWalletQuery(PayRequest request);

    /**
     * 公众平台退款
     * @param request
     * @return
     */
    RefundResponse refund(RefundRequest request);


    /**
     * 分账添加
     * @param request
     * @return
     */
    PayResponse  sharingAdd(SharingAddRequest request);

    /**
     * 分账请求
     * @param request
     * @return
     */
    PayResponse  sharingReq(SharingReqRequest request);

}