package cn.cube.base.third.wx;


import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.third.pay.PayPlatform;
import cn.cube.base.third.wx.config.WxConfig;
import com.google.common.collect.Maps;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:WxPayClientHolder
 * Author:zhanglida
 * Date:2017/11/3
 * Email:406504302@qq.com
 */
public class WxPayClientHolder {
    private static final Logger logger = LoggerUtils.getLogger(WxPayClientHolder.class);
    private static Map<PayPlatform, Map<String, WxPayClient>> clients = new HashMap<>();

    private WxConfig wxConfig;
    public WxPayClientHolder(WxConfig wxConfig) {
        this.wxConfig = wxConfig;
        this.load();
    }

    public static WxPayClient client(PayPlatform platform, String appId) {
        Map<String, WxPayClient> appPayClient = clients.get(platform);
        if (null == appPayClient || !appPayClient.containsKey(appId)) {
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID, appId + " 支付连接未初始化");
        }
        WxPayClient wxPayClient = appPayClient.get(appId);
        return wxPayClient;
    }

    public void load() {
        wxConfig.getOpens().forEach(config -> cacheWxPayClient(PayPlatform.OPEN, config));
        wxConfig.getMps().forEach(config -> cacheWxPayClient(PayPlatform.MP, config));
        wxConfig.getMinis().forEach(config -> cacheWxPayClient(PayPlatform.MINI, config));
    }

    private void cacheWxPayClient(PayPlatform platform, WxConfig.IWxProperties config) {
        if (!clients.containsKey(platform)) {
            Map<String, WxPayClient> map = Maps.newHashMap();
            clients.put(platform, map);
        }
        logger.info("WxPayClient init platform:{} param:{}", platform, JsonUtil.toJson(config));
        clients.get(platform).put(config.getApp(), new WxPayClient(config));
    }
}
