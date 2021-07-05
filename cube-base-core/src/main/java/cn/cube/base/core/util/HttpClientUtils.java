package cn.cube.base.core.util;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

public class HttpClientUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    public static final int SOCKET_TIMEOUT = 5000;
    public static final int CONNECT_TIMEOUT = 5000;
    private static CloseableHttpClient httpClient;
    private static final PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();

    public static synchronized CloseableHttpClient getHttpClient() {
        if (httpClient == null) {
            ConnectionConfig connectionConfig = ConnectionConfig.custom()
                    .setCharset(Consts.UTF_8).build();

            cm.setMaxTotal(200);
            cm.setDefaultMaxPerRoute(50);
            cm.setDefaultMaxPerRoute(50);
            cm.setDefaultConnectionConfig(connectionConfig);
            httpClient = HttpClients.custom().setConnectionManager(cm).build();

        }
        return httpClient;
    }

    public static String doPostJson(String url, String postData, NameValuePair... headers) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setEntity(new StringEntity(postData, ContentType.APPLICATION_JSON));

        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);
        if (headers != null) {
            for (NameValuePair header : headers) {
                httpPost.addHeader(header.getName(), header.getValue());
            }
        }

        String content;
        try (CloseableHttpResponse response = getHttpClient().execute(httpPost)) {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return content;
    }

    public static String doPostTextPlain(String url, String postData) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);
        httpPost.setEntity(new StringEntity(postData, ContentType.TEXT_PLAIN));

        String content;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return content;
    }

    public static String doPostXmlPlain(String url, String postData, Map<String, String> headParams) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);
        StringEntity entity = new StringEntity(postData, "utf-8");
        entity.setContentType(ContentType.TEXT_XML.getMimeType());
        httpPost.setEntity(entity);
        if (null != headParams && headParams.size() > 0) {
            for (Map.Entry<String, String> entry : headParams.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                httpPost.addHeader(key, value);
            }
        }
        String content;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return content;
    }


    public static String doRequest(String urlWithParams) throws IOException {
        HttpGet httpget = new HttpGet(urlWithParams);
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(CONNECT_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT).setSocketTimeout(SOCKET_TIMEOUT).build();
        httpget.setConfig(requestConfig);

        CloseableHttpClient httpclient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String jsonStr = EntityUtils.toString(entity);
            return jsonStr;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    public static String doRequest(String url, Map<?, ?> param, int timeout) throws IOException {
        URI uri = getUri(url, param);
        HttpGet httpget = new HttpGet(uri);
        // 配置请求的超时设置
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(timeout)
                .setConnectTimeout(timeout).setSocketTimeout(timeout).build();
        httpget.setConfig(requestConfig);

        CloseableHttpClient httpclient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpget);
            HttpEntity entity = response.getEntity();
            String jsonStr = getString(entity);
            return jsonStr;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    private static String getString(HttpEntity entity) throws IOException {
        return EntityUtils.toString(entity);
    }

    public static String doPost(String url, Map<String, String[]> params) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);

        List<NameValuePair> paramsList = new ArrayList<>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String[]> entry : params.entrySet()) {
                for (String value : entry.getValue()) {
                    paramsList.add(new BasicNameValuePair(entry.getKey(), value));
                }
            }
        }
        httpPost.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));

        String content;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return content;
    }

    public static String doGet(String url) throws IOException {
        CloseableHttpClient httpClient = getHttpClient();
        HttpGet httpPost = new HttpGet(url);

        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);

        String content;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return content;
    }

    public static String doPostFormData(String url, Map<String, String> params)
            throws Exception {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost post = getHttpPost(url, params);
        String content;
        try (CloseableHttpResponse response = httpClient.execute(post)) {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return content;
    }

    private static HttpPost getHttpPost(String url, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPost post = new HttpPost(url);
        List<BasicNameValuePair> paramsList = new ArrayList<>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                paramsList.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }
        RequestConfig requestConfig = getRequestConfig();
        post.setConfig(requestConfig);

        post.setEntity(new UrlEncodedFormEntity(paramsList, "UTF-8"));
        return post;
    }

    public static String doPostFile(String url, Map<String, String> params, File file) throws IOException {
        CloseableHttpClient httpClient = HttpClientUtils.getHttpClient();
        HttpPost httpPost = new HttpPost(url);

        RequestConfig requestConfig = getRequestConfig();
        httpPost.setConfig(requestConfig);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.setCharset(Charset.forName("utf-8"));
        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        builder.addPart("file", new FileBody(file));
        if (null != params) {
            params.forEach((key, value) -> {
                builder.addTextBody(key, value);
            });
        }
        HttpEntity entity = builder.build();
        httpPost.setEntity(entity);

        String content;
        try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
            content = EntityUtils.toString(response.getEntity(), "UTF-8");
        }
        return content;
    }

    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
                    .setSocketTimeout(SOCKET_TIMEOUT).setConnectTimeout(CONNECT_TIMEOUT).build();
    }

    public static URI getUri(String url, Map<?, ?> params) {
        URI uri = null;
        try {
            URIBuilder urib = new URIBuilder(url);
            if (params != null) {
                Iterator<?> iterator = params.keySet().iterator();
                while (iterator.hasNext()) {
                    String p = (String) iterator.next();
                    Object pv = params.get(p);
                    if (pv instanceof Integer) {
                        urib.setParameter(p, pv.toString());
                    } else if (pv instanceof Long) {
                        urib.setParameter(p, pv.toString());
                    } else if (pv instanceof Date) {
                        urib.setParameter(p, pv.toString());
                    } else {
                        urib.setParameter(p, (String) pv);
                    }

                }
            }
            uri = urib.build();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return uri;
    }
}
