package cn.cube.base.third.pay.config;

import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.third.wx.WxPayClientHolder;
import cn.cube.base.third.wx.config.WxConfig;
import org.slf4j.Logger;

/**
 * Description:PayRefreshConfig
 * Author:zhanglida
 * Date:2021/1/24
 * Email:406504302@qq.com
 */
public class PayRefreshConfig implements Runnable {
    private static final Logger logger = LoggerUtils.getLogger(PayRefreshConfig.class);
    private WxConfig wxConfig;
    private WxPayClientHolder wxPayClientHolder;
    private Integer refreshVersion;

    public PayRefreshConfig(WxConfig wxConfig, WxPayClientHolder wxPayClientHolder) {
        this.wxConfig = wxConfig;
        this.wxPayClientHolder = wxPayClientHolder;
        this.refreshVersion = wxConfig.getRefreshVersion();
    }

    @Override
    public void run() {
        if (wxConfig.getRefreshInterval() > 0) {
            while (true) {
                try {
                    Thread.sleep(wxConfig.getRefreshInterval());
                    if (wxConfig.getRefreshVersion() != this.refreshVersion) {
                        this.refreshVersion = wxConfig.getRefreshVersion();
                        wxConfig.load();
                        wxPayClientHolder.load();
                    }
                } catch (Exception e) {
                    logger.error("pay config refresh fail", e);
                }

            }
        }
    }
}
