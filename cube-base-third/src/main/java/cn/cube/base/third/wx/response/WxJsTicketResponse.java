package cn.cube.base.third.wx.response;

/**
 * Description:WxJsTicketResponse
 * Author:zhanglida
 * Date:2018/3/24
 * Email:406504302@qq.com
 */
public class WxJsTicketResponse extends WxBaseResponse {
    private String ticket;
    private Integer expires_in;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
