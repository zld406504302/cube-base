package cn.cube.base.third.keruyun.request;

import lombok.Data;

import java.util.List;

/**
 * Description:OrderRequest
 * Author:zhanglida
 * Date:2021/3/19
 * Email:406504302@qq.com
 */
public class OrderRequest {

    @Data
    public static class OrderList extends BaseRequest{
        private Long startTime;
        private Long endTime;
        private Integer timeType = 2;
        private Integer pageNo = 1;
        private Integer pageSize = 50;
    }


    @Data
    public static class OrderInfoList extends BaseRequest{
        private List<Long> ids;
    }

}
