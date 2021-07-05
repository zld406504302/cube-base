package cn.cube.base.third.keruyun;

import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.core.util.OkHttpUtil;
import cn.cube.base.core.util.StringUtils;
import cn.cube.base.third.keruyun.request.BaseRequest;
import cn.cube.base.third.keruyun.request.OrderRequest;
import cn.cube.base.third.keruyun.request.ShowcodeRequest;
import cn.cube.base.third.keruyun.response.*;
import com.alibaba.fastjson.TypeReference;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Description:KryApi
 * Author:zhanglida
 * Date:2021/3/19
 * Email:406504302@qq.com
 */
public class KryApi {
    private static final Logger logger = LoggerUtils.getLogger(KryApi.class);
    private static String HOST = "https://openapi.keruyun.com";
    private static String PARAM_PATTERN = "?appKey={}&shopIdenty={}&version={}&timestamp={}&sign={}";
    private static String ORDER_LIST_URL = HOST + "/open/v1/data/order/export2" + PARAM_PATTERN;
    private static String ORDER_INFO_URL = HOST + "/open/v1/data/order/exportDetail" + PARAM_PATTERN;
    private static String TOKEN_URL = HOST + "/open/v1/token/get";
    private static String CUSTOMER_INFO_URL = HOST + "/open/v1/crm/getCustomerDetailById"+ PARAM_PATTERN;
    private static String SHOWCODE_URL = HOST + "/open/v1/wallet/showCode"+ PARAM_PATTERN;

    public static KryResponse<TokenResponse> getToken(BaseRequest request) {
        String sign = getSignForToken(request);
        request.setSign(sign);
        Map<String, String> param = JsonUtil.toMap(request);
        String s = OkHttpUtil.doGet(TOKEN_URL, param);

        return JsonUtil.toObj(s, new TypeReference<KryResponse<TokenResponse>>() {
        });
    }

    public static KryResponse<Pager<OrderResponse.OrderInfo>> getOrderList(OrderRequest.OrderList request) {

        String sign = getSignForCommon(request);
        request.setSign(sign);
        String url = getUrl(ORDER_LIST_URL, request);
        String s = OkHttpUtil.doPostJson(url, JsonUtil.toJson(request));

        return JsonUtil.toObj(s, new TypeReference<KryResponse<Pager<OrderResponse.OrderInfo>>>() {
        });
    }

    public static KryResponse<List<OrderResponse.OpenOrderExportDetailVO>> getOrderInfoList(OrderRequest.OrderInfoList request) {
        String sign = getSignForCommon(request);
        request.setSign(sign);
        String url = getUrl(ORDER_INFO_URL, request);
        String s = OkHttpUtil.doPostJson(url, JsonUtil.toJson(request));

        return JsonUtil.toObj(s, new TypeReference<KryResponse<List<OrderResponse.OpenOrderExportDetailVO>>>() {
        });
    }

    public static KryResponse<CustomerResponse.CustomerInfo> getCustomerInfo(BaseRequest request) {
        String sign = getSignForCommon(request);
        request.setSign(sign);
        String url = getUrl(CUSTOMER_INFO_URL, request);
        String s = OkHttpUtil.doPostJson(url, JsonUtil.toJson(request));

        return JsonUtil.toObj(s, new TypeReference<KryResponse<CustomerResponse.CustomerInfo>>() {});
    }

    public static KryResponse<ShowcodeResponse> getShowcode(ShowcodeRequest request){
        String sign = getSignForCommon(request);
        request.setSign(sign);
        String url = getUrl(SHOWCODE_URL, request);
        String s = OkHttpUtil.doPostJson(url, JsonUtil.toJson(request));
        return JsonUtil.toObj(s, new TypeReference<KryResponse<ShowcodeResponse>>() {});
    }

    /**
     * @param
     * @throws
     * @Description: 获取token时签名验证（只在获取token时调用一次）
     */
    public static String getSignForToken(BaseRequest request) {
        Map<String, Object> map = getSignParamMap(request);
        StringBuilder sortedParams = new StringBuilder();
        map.entrySet().stream().forEachOrdered(paramEntry -> sortedParams.append(paramEntry.getKey()).append(paramEntry.getValue()));
        sortedParams.append(request.getAppSecret());
        return getSign(sortedParams.toString());
    }

    /**
     * @param
     * @throws
     * @Description: 获取token时签名验证（只在获取token时调用一次）
     */
    public static String getSignForCommon(BaseRequest request) {
        Map<String, Object> map = getSignParamMap(request);
        StringBuilder sortedParams = new StringBuilder();
        map.entrySet().stream().forEachOrdered(paramEntry -> sortedParams.append(paramEntry.getKey()).append(paramEntry.getValue()));
        sortedParams.append(request.getToken());
        return getSign(sortedParams.toString());
    }

    @NotNull
    private static Map<String, Object> getSignParamMap(BaseRequest request) {
        Map<String, Object> map = new TreeMap<>();
        map.put("appKey", request.getAppKey());
        map.put("shopIdenty", request.getShopIdenty());
        map.put("version", request.getVersion());
        map.put("timestamp", request.getTimestamp());
        return map;
    }


    private static String getUrl(String url, BaseRequest request) {
        return StringUtils.format(url, request.getAppKey(), String.valueOf(request.getShopIdenty()), request.getVersion(), String.valueOf(request.getTimestamp()), request.getSign());
    }

    /**
     * @param
     * @return String
     * @throws NoSuchAlgorithmException
     * @Description: SHA256加密字符串
     */
    private static String getSign(String signSrc) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(signSrc.getBytes());
            byte[] byteBuffer = messageDigest.digest();
            StringBuffer strHexString = new StringBuffer();
            for (int i = 0; i < byteBuffer.length; i++) {
                String hex = Integer.toHexString(0xff & byteBuffer[i]);
                if (hex.length() == 1) {
                    strHexString.append('0');
                }
                strHexString.append(hex);
            }
            logger.info("signsrc:{}", signSrc);
            String sign = strHexString.toString();
            logger.info("sign:{}", sign);
            return sign;
        } catch (Exception e) {
            logger.error("sign fail param:{}", signSrc, e);
        }
        return "";
    }

}
