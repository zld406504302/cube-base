package cn.cube.base.web.protocol.request;

import cn.cube.base.core.util.NumberUtils;
import cn.cube.base.core.util.StringUtils;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Description:ProtocolRequest
 * Author:zhanglida
 * Date:2017/12/24
 * Email:406504302@qq.com
 */
@Data
public class ProtocolRequest {
    public static final String IP_DEFAULT ="127.0.0.1";
    private String appId;
    private String uid;
    private String version;
    private String timestamp;
    private String data;
    private String ip;
    private String token;
    private String sign;
    private String platform;
    private Integer merchant ;

    public String getIp() {
        return StringUtils.isEmpty(ip)? IP_DEFAULT :ip;
    }

    public long getUid() {
        long uidL = NumberUtils.toLong(this.uid);
        return uidL;
    }

    @JSONField(serialize = false)
    public boolean isProtocolRequest() {
        if (!StringUtils.isEmpty(appId)) {
            return true;
        }
        return false;
    }

    public String getRealIp() {
        String ip = getIp();
        if (StringUtils.isEmpty(ip)) {
            return "";
        }
        if (!ip.contains(",")) {
            return ip;
        }

        String[] ips = ip.split(",");
        if (ips.length > 0) {
            return ips[0];
        }
        return ip;
    }

}
