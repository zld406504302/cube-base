package cn.cube.base.third.keruyun.request;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Description:BaseRequest
 * Author:zhanglida
 * Date:2021/3/19
 * Email:406504302@qq.com
 */
@Data
public class BaseRequest {
    private String appKey;
    @JSONField(serialize = false)
    private String appSecret;
    private Long shopIdenty;
    private String version = "1.0";
    //时间戳,单位:秒
    private Long timestamp;
    private String token;
    private String sign;

    public BaseRequest() {
        this.timestamp = System.currentTimeMillis() / 1000;
    }

}
