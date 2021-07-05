package cn.cube.base.third.wx.request;

import cn.cube.base.core.util.DateUtils;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.core.util.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;

import java.util.Date;

/**
 * Description:WxJsTicketSignResponse
 * Author:zhanglida
 * Date:2018/9/27
 * Email:406504302@qq.com
 */
public class WxJsTicketSignRequest {
    private static final Logger logger = LoggerUtils.getLogger(WxJsTicketSignRequest.class);
    private String noncestr="Wm3WZYTPz0wzccnW";
    private String jsapi_ticket;
    private String timestamp="1414587457";
    private String url ;

    public String getNoncestr() {
        return noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getJsapi_ticket() {
        return jsapi_ticket;
    }

    public void setJsapi_ticket(String jsapi_ticket) {
        this.jsapi_ticket = jsapi_ticket;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


    public String sign(){
        String content = StringUtils.format("jsapi_ticket={}&noncestr={}&timestamp={}&url={}",jsapi_ticket,noncestr,timestamp,url);
        String sign = DigestUtils.sha1Hex(content);
        return sign;
    }


    public static void main(String[] args) {
//        //jsapi_ticket=sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg&noncestr=Wm3WZYTPz0wzccnW&timestamp=1414587457&url=http://mp.weixin.qq.com?params=value
        WxJsTicketSignRequest request = new WxJsTicketSignRequest();
        request.setUrl("http://mp.weixin.qq.com?params=value");
        request.setJsapi_ticket("sM4AOVdWfPE4DxkXGEs8VMCPGGVi4C3VM0P37wVUCFvkVAy_90u5h9nbSlYy3-Sl-HhTdfl2fzFy1AOcHKP7qg");
        request.setTimestamp("1414587457");
        request.setNoncestr("Wm3WZYTPz0wzccnW");
        System.out.println(request.sign());

        Date date = DateUtils.parseDate(DateUtils.format(new Date(), DateUtils.DATE_TIME_FORMAT),DateUtils.DATE_TIME_FORMAT);
        System.out.println(date.getTime()+"");


    }
}
