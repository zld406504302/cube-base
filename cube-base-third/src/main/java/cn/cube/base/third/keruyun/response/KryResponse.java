package cn.cube.base.third.keruyun.response;

import lombok.Data;

/**
 * Description:BaseResponse
 * Author:zhanglida
 * Date:2021/3/19
 * Email:406504302@qq.com
 */
@Data
public class KryResponse<T> {
    private Long code ;
    private String message ;
    private String messageUuid;
    private T result;
}
