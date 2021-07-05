package cn.cube.base.third.keruyun.request;

import lombok.Data;

/**
 * Description:CustomerRequest
 * Author:zhanglida
 * Date:2021/3/21
 * Email:406504302@qq.com
 */
public class CustomerRequest {

    @Data
    public static class CustomerInfo extends BaseRequest{
        private Long customerId;
        private Long isNeedCredit;
    }
}
