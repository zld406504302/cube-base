package cn.cube.base.third.wx;

import cn.cube.base.core.util.*;
import cn.cube.base.third.pay.PayPlatform;
import cn.cube.base.third.wx.response.*;
import cn.cube.base.third.wx.config.WxConfig;
import cn.cube.base.third.wx.request.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thoughtworks.xstream.XStream;
import org.apache.commons.codec.digest.DigestUtils;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Description:WxApiUtils
 * Author:zhanglida
 * Date:2018/3/24
 * Email:406504302@qq.com
 */
@Component
public class WxApi {
    private static final Logger logger = LoggerUtils.getLogger(WxApi.class);
    private static WxConfig wxConfig;

    @Autowired
    public void setWxConfig(WxConfig wxConfig) {
        WxApi.wxConfig = wxConfig;
    }


    /**
     * 授权地址
     *
     * @param redirectUrl
     * @return
     */
    public static String getAuthUrl(String appId, String redirectUrl) {
        WxConfig.WxProperties config = wxConfig.getMiniConfig(appId);
        return StringUtils.format(WxUrl.OPEN_AUTH_URL, config.getAppId(), redirectUrl);
    }

    /**
     * token 认证
     * 服务token 而非 接口token
     *
     * @param request
     * @return
     */
    public static String checkToken(String appId, WxTokenCheckRequest request) {
        WxConfig.WxProperties config = wxConfig.getMiniConfig(appId);
        String token = config.getToken();
        String[] str = {token, request.getTimestamp(), request.getNonce()};
        Arrays.sort(str);
        String bigStr = str[0] + str[1] + str[2];

        DigestUtils.sha1Hex(bigStr);
        String digest = DigestUtils.sha1Hex(bigStr);
        if (digest.equals(request.getSignature())) {
            return request.getNonce();
        }
        return "";
    }

    /**
     * 获取访问token
     *
     * @param code
     * @return
     */
    public static WxAccessTokenResponse getMpAccessToken(String appId, String code) {
        WxConfig.WxProperties config = wxConfig.getMiniConfig(appId);
        String url = StringUtils.format(WxUrl.OPEN_TOKEN_URL, config.getAppId(), config.getSecret(), code);
        return getWxAccessTokenResponse(url, WxAccessTokenResponse.class);
    }

    /**
     * 获取访问token
     *
     * @param code
     * @return
     */
    public static WxAccessTokenResponse getMiniAccessToken(String appId, String code) {
        WxConfig.WxProperties config = wxConfig.getMiniConfig(appId);
        String url = StringUtils.format(WxUrl.OPEN_TOKEN_URL, config.getAppId(), config.getSecret(), code);
        return getWxAccessTokenResponse(url, WxAccessTokenResponse.class);
    }


    /**
     * 获取会话信息
     *
     * @param code
     * @return
     */
    public static WxCode2SessionResponse getCode2Session(String appId, String code) {
        WxConfig.WxProperties config = wxConfig.getMiniConfig(appId);
        String url = StringUtils.format(WxUrl.MP_SESSION_URL, config.getAppId(), config.getSecret(), code);
        return getWxAccessTokenResponse(url, WxCode2SessionResponse.class);
    }


    /**
     * 获取公众平台访问token
     *
     * @return
     */
    public static WxAccessTokenResponse getMpAccessToken(String appId) {
        WxConfig.WxProperties config = wxConfig.getMiniConfig(appId);
        String url = StringUtils.format(WxUrl.MP_TOKEN_URL, config.getAppId(), config.getSecret());
        WxAccessTokenResponse s = getWxAccessTokenResponse(url, WxAccessTokenResponse.class);
        return s;
    }


    private static <T> T getWxAccessTokenResponse(String url, Class<T> t) {
        try {
            printReq(url);
            String s = HttpClientUtils.doRequest(url);
            printRes(s);
            return JsonUtil.toObj(s, t);
        } catch (IOException e) {
            printErr(url, e);
        }
        return null;
    }


    /**
     * 获取访问js-ticket
     *
     * @param accessToken
     * @return
     */
    public static WxJsTicketResponse getJsTicket(String accessToken) {
        String url = StringUtils.format(WxUrl.JS_TICKET, accessToken);
        try {
            printReq(url);
            String s = HttpClientUtils.doRequest(url);
            printRes(s);
            return JsonUtil.toObj(s, WxJsTicketResponse.class);
        } catch (IOException e) {
            printErr(url, e);
        }
        return null;
    }

    /**
     * 获取微信用户详情
     *
     * @param openId
     * @param token
     * @return
     */
    public static WxUserResponse getUserInfo(String openId, String token) {
        String url = StringUtils.format(WxUrl.INFO_URL, openId, token);
        try {
            printReq(url);
            String s = HttpClientUtils.doRequest(url);
            printRes(s);
            return JsonUtil.toObj(s, WxUserResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
            printErr(url, e);
        }
        return null;
    }

    /**
     * JS支付
     *
     * @param req
     * @return
     */
    public static WxUnifiedPayResponse jsPay(String appId, PayPlatform payPlatform, WxUnifiedPayRequest req) throws Exception {
        WxPayClient client = WxPayClientHolder.client(payPlatform, appId);
        setCommonParams(client, req);
        if (StringUtils.isEmpty(req.getNotify_url())) {
            req.setNotify_url(client.getConfig().getNotifyUrl());
        }
        req.setTrade_type(WxPayConstants.TradeType.JSAPI.name());
        req.sign(client.getConfig().getKey());
        String response = client.sendPost(WxUrl.UNIFIED_ORDER_URI, req);
        WxUnifiedPayResponse res = getObjectFromXML(response, WxUnifiedPayResponse.class);
        res.setAppId(client.getConfig().getAppId());
        res.setKey(client.getConfig().getKey());

        return res;
    }

    /**
     * JS支付
     * @param request
     * @return
     */
    public static WxRefundResponse mpRefund(String appId, PayPlatform payPlatform, WxRefundRequest request) throws Exception {
        WxPayClient client = WxPayClientHolder.client(payPlatform, appId);
        setCommonParams(client, request);
        request.sign(client.getConfig().getKey());
        String response = client.sendPost(WxUrl.REFUND_URI, request);
        WxRefundResponse res = getObjectFromXML(response, WxRefundResponse.class);

        return res;
    }

    /**
     * 分账添加
     * @param request
     * @return
     */
    public static WxPayResponse addSharing(String appId, PayPlatform payPlatform, WxSharingAddRequest request) throws Exception {
        WxPayClient client = WxPayClientHolder.client(payPlatform, appId);
        setCommonParams(client, request);
        request.sign(client.getConfig().getKey());
        String response = client.sendPost(WxUrl.SHAREING_ADD_URI, request);
        WxPayResponse res = getObjectFromXML(response, WxPayResponse.class);

        return res;
    }

    /**
     * 分账请求
     * @param request
     * @return
     */
    public static WxPayResponse reqSharing(String appId, PayPlatform payPlatform, WxSharingReqRequest request) throws Exception {
        WxPayClient client = WxPayClientHolder.client(payPlatform, appId);
        setCommonParams(client, request);
        request.sign(client.getConfig().getKey());
        String response = client.sendPost(WxUrl.SHAREING_REQ_URI, request);
        WxPayResponse res = getObjectFromXML(response, WxPayResponse.class);

        return res;
    }

    /**
     * 订单查询
     *
     * @return
     */
    public static WxPayQueryResponse payQuery(String appId, WxPayQueryRequest req) throws Exception {
        WxPayClient client = WxPayClientHolder.client(PayPlatform.MP, appId);
        setCommonParams(client, req);
        req.sign(client.getConfig().getKey());
        String responseString = client.sendPost(WxUrl.ORDER_QUERY_URI, req);
        WxPayQueryResponse scanPayResData = getObjectFromXML(responseString, WxPayQueryResponse.class);
        return scanPayResData;
    }

    /**
     * 转账到零钱
     *
     * @return
     */
    public static WxTransferWalletResponse transferWallet(String appId, WxTransferWalletRequest req) throws Exception {
        WxPayClient request = WxPayClientHolder.client(PayPlatform.OPEN, appId);
        setCommonParams(request, req);
        req.sign(request.getConfig().getKey());
        String responseString = request.sendPost(WxUrl.TRANSFER_WALLET_URI, req);
        WxTransferWalletResponse transferWalletRes = getObjectFromXML(responseString, WxTransferWalletResponse.class);
        return transferWalletRes;
    }

    /**
     * 转账到零钱查询
     *
     * @return
     */
    public static WxTransferWalletQueryResponse transferWalletQuery(String appId, WxTransferWalletQueryRequest req) throws Exception {
        WxPayClient request = WxPayClientHolder.client(PayPlatform.OPEN, appId);
        setCommonParams(request, req);
        req.sign(request.getConfig().getKey());
        String responseString = request.sendPost(WxUrl.TRANSFER_WALLET_QYERT_URI, req);
        WxTransferWalletQueryResponse transferWalletRes = getObjectFromXML(responseString, WxTransferWalletQueryResponse.class);
        return transferWalletRes;
    }

    private static void setCommonParams(WxPayClient request, WxBasePayRequest req) {
        req.setAppid(request.getConfig().getAppId());
        req.setMch_id(request.getConfig().getMchId());
        req.setNonce_str(StringUtils.randomStringByLength(request.getConfig().getNoticeStrLength()));
    }

    /**
     * 异步通知签名确认
     *
     * @param noticeContent 微信通知xml内容
     * @return true:眼前成功 false:验签失败
     */
    public static boolean checkSign(String key, String noticeContent) {
        boolean result = Signature.checkIsSignValidFromResponseString(key, noticeContent);
        return result;
    }

    public static InputStream getStringStream(String sInputString) {
        ByteArrayInputStream tInputStringStream = null;
        if (sInputString != null && !sInputString.trim().equals("")) {
            tInputStringStream = new ByteArrayInputStream(sInputString.getBytes());
        }
        return tInputStringStream;
    }

    public static String inputStreamToString(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

    public static <T> T getObjectFromXML(String xml, Class<T> tClass) {
        XStream xStreamForResponseData = new XStream();
        XStream.setupDefaultSecurity(xStreamForResponseData);
        xStreamForResponseData.allowTypes(new Class[]{tClass});
        xStreamForResponseData.alias("xml", tClass);
        xStreamForResponseData.ignoreUnknownElements();
        xStreamForResponseData.setClassLoader(tClass.getClassLoader());
        return (T) xStreamForResponseData.fromXML(xml);
    }

    private static void printReq(String url) {
        logger.info("wx-req:{}", url);
    }

    private static void printRes(String res) {
        logger.info("wx-res:{}", res);
    }

    private static void printErr(String url, Exception e) {
        logger.error("wx-err: url:{}", url, e);
    }


    public static WxPhoneResponse getWxPhoneResponse(WxDecryptRequest request) {
        try {
            WxPhoneResponse wxPhoneResponse = decryptData(request.getEncryptedData(), request.getSessionKey(), request.getIvData(), WxPhoneResponse.class);
            return wxPhoneResponse;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T decryptData(String encryptedData, String sessionKey, String ivData, Class<T> t) throws Exception {
        String result = encrypted(encryptedData, sessionKey, ivData);

        return JsonUtil.toObj(result, t);
    }

    @Nullable
    private static String encrypted(String encryptedData, String sessionKey, String ivData) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
        SecretKeySpec key = new SecretKeySpec(Base64Utils.decodeFromString(sessionKey), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec iv = new IvParameterSpec(Base64Utils.decodeFromString(ivData));
        cipher.init(Cipher.DECRYPT_MODE, key, iv);
        return StringUtils.newStringUtf8(cipher.doFinal(Base64Utils.decodeFromString(encryptedData)));
    }

    public static JSONObject decryptData(String encryptedData, String sessionKey, String ivData) throws Exception {
        String result = encrypted(encryptedData, sessionKey, ivData);
        return JSON.parseObject(result);
    }

}
