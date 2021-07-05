package cn.cube.base.third.pay.response;

import cn.cube.base.third.wx.response.WxPayNoticeResponse;

/**
 * Description:PayNoticeResponse
 * Author:zhanglida
 * Date:2019/11/21
 * Email:406504302@qq.com
 */
public class NoticeResponse {
    private Long inTradeNo;
    private String noticeReply ;

    public Long getInTradeNo() {
        return inTradeNo;
    }

    public void setInTradeNo(Long inTradeNo) {
        this.inTradeNo = inTradeNo;
    }

    public String getNoticeReply() {
        return noticeReply;
    }

    public void setNoticeReply(String noticeReply) {
        this.noticeReply = noticeReply;
    }
}
