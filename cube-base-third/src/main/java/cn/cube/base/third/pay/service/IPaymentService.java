package cn.cube.base.third.pay.service;

import cn.cube.base.third.pay.request.*;
import cn.cube.base.third.pay.response.NoticeResponse;
import cn.cube.base.third.pay.response.PayResponse;
import cn.cube.base.third.pay.response.RefundResponse;

/**
 * Description:IPaymentService
 * Author:zhanglida
 * Date:2020/3/7
 * Email:406504302@qq.com
 */
public interface IPaymentService {
    NoticeResponse notice(NoticeRequest request);
    PayResponse create(PayRequest request);
    RefundResponse refund(RefundRequest request);
    PayResponse sharingAdd(SharingAddRequest request);
    PayResponse sharingReq(SharingReqRequest request);

}
