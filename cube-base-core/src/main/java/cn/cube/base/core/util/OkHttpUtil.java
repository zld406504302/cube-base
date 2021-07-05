package cn.cube.base.core.util;

import com.google.common.collect.Lists;
import okhttp3.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.poi.ss.formula.functions.T;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class OkHttpUtil {
    private static final Logger logger = LoggerUtils.getLogger(OkHttpUtil.class);
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static final MediaType XML = MediaType.parse("application/xml; charset=utf-8");
    private static final MediaType HTML = MediaType.parse("text/html; charset=utf-8");

    private static OkHttpClient okHttpClient;

    @Autowired
    public void setOkHttpClient(OkHttpClient okHttpClient) {
        OkHttpUtil.okHttpClient = okHttpClient;
    }

    /**
     * get 请求
     *
     * @param url 请求url地址
     * @return string
     */
    public static String doGet(String url) {
        return doGet(url, null, null);
    }

    /**
     * get 请求
     *
     * @param url    请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public static String doGet(String url, Map<String, String> params) {
        return doGet(url, params, null);
    }

    public static T doGet(String url, Map<String, String> params, Map<String, String> headers, Class<T> tclass) {
        String s = doGet(url, params, headers);
        return JsonUtil.toObj(s, tclass);
    }

    /**
     * get 请求
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     */
    public static String doGet(String url, Map<String, String> params, Map<String, String> headers) {
        Request request = getRequest(url, params, headers);
        return execute(request);
    }

    /**
     * post 请求
     *
     * @param url    请求url地址
     * @param params 请求参数 map
     * @return string
     */
    public static String doPost(String url, Map<String, String> params, Map<String, String> headers) {
        FormBody.Builder formBuilder = new FormBody.Builder();

        if (params != null && params.keySet().size() > 0) {
            for (String key : params.keySet()) {
                formBuilder.add(key, params.get(key));
            }
        }
        Request.Builder requestBuilder = new Request.Builder();
        if (!CollectionUtils.isEmpty(headers)) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestBuilder.url(url).post(formBuilder.build()).build();
        logger.info("do post request and url[{}]", url);

        return execute(request);
    }



    /**
     * post 请求, 请求数据为 json 的字符串
     *
     * @param url  请求url地址
     * @param json 请求数据, json 字符串
     * @return string
     */
    public static String doPostJson(String url, String json) {
        return exectePost(url, json, JSON);
    }

    /**
     * post 请求, 请求数据为 xml 的字符串
     *
     * @param url 请求url地址
     * @param xml 请求数据, xml 字符串
     * @return string
     */
    public static String doPostXml(String url, String xml) {
        return exectePost(url, xml, XML);
    }


    private static String exectePost(String url, String data, MediaType contentType) {
        RequestBody requestBody = RequestBody.create(contentType, data);
        Request request = new Request.Builder().url(url).post(requestBody).build();
        logger.info("request url:{} param:{}",url,data);
        String res = execute(request);
        logger.info("response url:{} param:{}",url,res);
        return res;
    }

    private static String execute(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return "";
    }

    private static Response executeWithResponse(Request request) {
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                return response;
            }
        } catch (Exception e) {
            logger.error(ExceptionUtils.getStackTrace(e));
        }
        return response;
    }


    /**
     * get 请求
     *
     * @param url     请求url地址
     * @param params  请求参数 map
     * @param headers 请求头字段 {k1, v1 k2, v2, ...}
     * @return string
     */
    public static Response doGetWithResponse(String url, Map<String, String> params, Map<String, String> headers) {
        Request request = getRequest(url, params, headers);
        return executeWithResponse(request);
    }

    @NotNull
    private static Request getRequest(String url, Map<String, String> params, Map<String, String> headers) {
        StringBuilder sb = new StringBuilder(url);
        if (params != null && params.keySet().size() > 0) {
            boolean firstFlag = true;
            for (String key : params.keySet()) {
                if (firstFlag) {
                    sb.append("?").append(key).append("=").append(params.get(key));
                    firstFlag = false;
                } else {
                    sb.append("&").append(key).append("=").append(params.get(key));
                }
            }
        }

        Request.Builder builder = new Request.Builder();
        if (headers != null && headers.size() > 0) {
            if (headers.size() % 2 == 0) {
                List<String> keys = Lists.newArrayList(headers.keySet());
                for (int i = 0; i < keys.size(); i = i + 2) {
                    String key = keys.get(i);
                    String val = headers.get(key);
                    builder.addHeader(key, val);
                }
            } else {
                logger.warn("headers's length[{}] is error.", headers.size());
            }

        }

        Request request = builder.url(sb.toString()).build();
        logger.info("do get request and url[{}]", sb.toString());
        return request;
    }


}
