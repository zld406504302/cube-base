package cn.cube.base.third.keruyun.request;

import lombok.Data;

/**
 * Description:ShowcodeRequest
 * Author:zhanglida
 * Date:2021/4/19
 * Email:406504302@qq.com
 */
@Data
public class ShowcodeRequest extends BaseRequest{
    private String authCode;
    private String outTradeNo;
    private Long  payFee=0L;
    private String payOrg="ORG_WEIXINPAY";
    private String productDesc;
}
