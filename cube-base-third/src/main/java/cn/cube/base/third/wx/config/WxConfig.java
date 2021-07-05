package cn.cube.base.third.wx.config;

import cn.cube.base.core.util.Beans;
import cn.cube.base.core.util.DateUtils;
import cn.cube.base.third.pay.PayChannel;
import cn.cube.base.third.pay.PayPlatform;
import cn.cube.base.third.pay.dao.PayConfigDao;
import cn.cube.base.third.pay.entity.PayConfig;
import com.google.common.collect.Lists;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Description:WxConfig
 * Author:zhanglida
 * Date:2019/6/5
 * Email:406504302@qq.com
 */
@Data
public class WxConfig {
    private Integer refreshInterval;
    private Integer refreshVersion;
    ///开放平台支付配置
    private List<WxProperties> opens = Lists.newArrayList();
    //公众平台支付配置
    private List<WxProperties> mps = Lists.newArrayList();

    //小程序支付配置
    private List<WxProperties> minis = Lists.newArrayList();

    private PayConfigDao payConfigDao;

    public WxConfig(PayConfigDao payConfigDao) {
        this.payConfigDao = payConfigDao;
        load();
    }

    public void load() {
        List<PayConfig> payConfigList = payConfigDao.selectList();
        for (PayConfig config : payConfigList) {
            if (PayChannel.WX.value != config.getChannel()) {
                continue;
            }
            WxProperties item = new WxProperties();
            Beans.copy(config, item);
            if (PayPlatform.OPEN.value == config.getPlatform()) {
                opens.add(item);
            }
            if (PayPlatform.MP.value == config.getPlatform()) {
                mps.add(item);
            }
            if (PayPlatform.MINI.value == config.getPlatform()) {
                minis.add(item);
            }
        }
    }

    public WxProperties getMiniConfig(String appId) {
        Map<String, WxProperties> collect = minis.stream().collect(Collectors.toMap(WxProperties::getApp, (t) -> t, (key1, key2) -> key1));
        return collect.get(appId);
    }

    public List<WxProperties> getMinis() {
        return minis;
    }

    public void setMinis(List<WxProperties> minis) {
        this.minis = minis;
    }

    public List<WxProperties> getOpens() {
        return opens;
    }

    public void setOpens(List<WxProperties> opens) {
        this.opens = opens;
    }

    public List<WxProperties> getMps() {
        return mps;
    }

    public void setMps(List<WxProperties> mps) {
        this.mps = mps;
    }

    public static class WxProperties implements IWxProperties {
        private String app;
        private String appId;
        private String secret;
        private String token;
        private String accredit;
        private String mchId;
        private String certPath;
        private String certPassword;
        private String key;
        private String notifyUrl;
        //支付订单有效分钟数
        private int timeout = 30;

        public String getApp() {
            return app;
        }

        public void setApp(String app) {
            this.app = app;
        }

        public String getAppId() {
            return appId;
        }

        public void setAppId(String appId) {
            this.appId = appId;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccredit() {
            return accredit;
        }

        public void setAccredit(String accredit) {
            this.accredit = accredit;
        }

        @Override
        public Date getExpireTime() {
            Date now = new Date();
            now = DateUtils.addMinutes(now, this.timeout);
            return now;
        }

        @Override
        public String getMchId() {
            return mchId;
        }

        public void setMchId(String mchId) {
            this.mchId = mchId;
        }

        @Override
        public String getCertPath() {
            return certPath;
        }

        public void setCertPath(String certPath) {
            this.certPath = certPath;
        }

        @Override
        public String getCertPassword() {
            return certPassword;
        }

        @Override
        public int getNoticeStrLength() {
            return noticeStrLength;
        }

        public void setCertPassword(String certPassword) {
            this.certPassword = certPassword;
        }

        @Override
        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        @Override
        public String getNotifyUrl() {
            return notifyUrl;
        }

        public void setNotifyUrl(String notifyUrl) {
            this.notifyUrl = notifyUrl;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }
    }

    public interface IWxProperties {

        String getApp();

        //随机串长度
        int noticeStrLength = 32;

        //通知地址
        String getNotifyUrl();

        String getKey();

        //微信分配的公众号ID（开通公众号之后可以获取到）
        String getAppId();

        //微信支付分配的商户号ID（开通公众号的微信支付功能之后可以获取到）
        String getMchId();

        //HTTPS证书的本地路径
        String getCertPath();

        //HTTPS证书密码，默认密码等于商户号MCHID
        String getCertPassword();

        int getNoticeStrLength();

        //过期日期
        Date getExpireTime();

    }
}
