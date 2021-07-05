package cn.cube.base.web.filter;

import cn.cube.base.core.entity.IAppInfo;
import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.security.Digests;
import cn.cube.base.core.service.IAppInfoService;
import cn.cube.base.core.util.*;
import cn.cube.base.web.ServerContext;
import cn.cube.base.web.protocol.request.ProtocolRequest;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import cn.cube.base.web.util.IpUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description:ProtocolInterceptor
 * Author:zhanglida
 * Date:2017/12/25
 * Email:406504302@qq.com
 */
public class WebProtocolFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerUtils.getLogger(WebProtocolFilter.class);

    private static final String EXCLUDE_URL = ".html";


    private IAppInfoService appInfoService;

    public WebProtocolFilter() {

    }

    public WebProtocolFilter(IAppInfoService appInfoService) {
        this.appInfoService = appInfoService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Long start = System.currentTimeMillis();
        CustResponseWrapper responseWrapper = new CustResponseWrapper(response);
        HttpServletRequest requestWrapper = request;
        //解析参数
        ProtocolRequest protocolRequest = convertRequest(request);
        //保存本地线程
        ServerContext.setProtocolRequest(protocolRequest);

        try {
            String uri = request.getRequestURI();
            if (isStaticResourceRequest(uri) || !protocolRequest.isProtocolRequest()) {
                filterChain.doFilter(requestWrapper, responseWrapper);
            } else {
                IAppInfo appInfo = appInfoService.getAppInfo(protocolRequest.getAppId());
                if (null == appInfo) {
                    ProtocolResponse res = new ProtocolResponse(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID);
                    doFail(response, res);
                    return;
                }
                String aesKey = appInfo.getAesKey();
                //校验签名
                checkSign(protocolRequest,appInfo.getSignKey());
                //解析业务参数
                Map<String, String> params = decryptDataToMap(protocolRequest, aesKey);
                //自定义request 和response
                requestWrapper = new CustRequestWrapper(request, params);
                filterChain.doFilter(requestWrapper, responseWrapper);
                protocolRequest.setData(JsonUtil.toJson(params));
            }
            response.getOutputStream().write(responseWrapper.getByteArrayOutputStream().toByteArray());

        } catch (BusinessException e) {
            logger.error("filter fail ,params:{} ", JsonUtil.toJson(ServerContext.getProtocolRequest()), e);
            ProtocolResponse res = new ProtocolResponse(e);
            doFail(response, res);
        } catch (Exception e) {
            logger.error("filter  fail ,params:{} ", JsonUtil.toJson(ServerContext.getProtocolRequest()), e);
            ProtocolResponse res = new ProtocolResponse(BaseBusinessErrorCode.SYSTEM_UNKNOWN_ERROR);
            doFail(response, res);
        } finally {
            Long end = System.currentTimeMillis();
            logger(requestWrapper, responseWrapper, end - start);
            ServerContext.removeProtocolRequest();
        }
    }

    private void doFail(HttpServletResponse response, ProtocolResponse res) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.setCharacterEncoding("UTF-8");
        OutputStream out = response.getOutputStream();
        out.write(JsonUtil.toJson(res).getBytes("UTF-8"));
        out.flush();
    }

    private ProtocolRequest convertRequest(HttpServletRequest request) {
        ProtocolRequest baseRequest = new ProtocolRequest();
        baseRequest.setAppId(request.getParameter("appId"));
        baseRequest.setUid(request.getParameter("uid"));
        baseRequest.setToken(request.getParameter("token"));
        baseRequest.setVersion(request.getParameter("version"));
        baseRequest.setSign(request.getParameter("sign"));
        baseRequest.setTimestamp(request.getParameter("timestamp"));
        baseRequest.setData(request.getParameter("data"));
        baseRequest.setPlatform(request.getParameter("platform"));

        String ipAddress = IpUtils.getIpAddress(request);
        baseRequest.setIp(ipAddress);

        return baseRequest;
    }

    private void logger(HttpServletRequest request, CustResponseWrapper response, Long cost) {
        ProtocolRequest protocolRequest = ServerContext.getProtocolRequest();
        logger.info("[uri:{}]  [status:{}] [ip:{}] [cost:{}] [request:{}] [response:{}]", request.getRequestURI(), response.getStatus(), protocolRequest.getIp(), String.valueOf(cost) + "ms", requestParam(request), responseParam(response));
    }

    private String requestParam(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        Map<String, String> map = Maps.newHashMap();
        parameterMap.forEach((key, values) -> {
            String value = (null != values) ? values[0] : "";
            map.put(key, value);
        });
        ProtocolRequest protocolRequest = ServerContext.getProtocolRequest();
        if (protocolRequest.isProtocolRequest()) {
            return JsonUtil.toJson(protocolRequest);
        }
        return JsonUtil.toJson(map);
    }

    private String responseParam(CustResponseWrapper response) {
        if (null == response) {
            return "";
        }
        return response.getTextContent();
    }

    public static void checkSign(ProtocolRequest protocolRequest,String key) {
        Map<String, String> params = new TreeMap<>();
        params.put("appId", protocolRequest.getAppId());
        params.put("uid", String.valueOf(protocolRequest.getUid()));
        params.put("token", protocolRequest.getToken());
        params.put("version", protocolRequest.getVersion());
        params.put("timestamp", protocolRequest.getTimestamp());
        params.put("data", protocolRequest.getData());
        params.put("platform", protocolRequest.getPlatform());

        StringBuilder paramBuilder = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String value = entry.getValue();
            if (value == null) {
                value = "";
            }
            paramBuilder.append(entry.getKey()).append("=").append(value).append("&");
        }

        String signSrc = paramBuilder.append("key=").append(key).toString();

        String md5Sign = "";
        try {
            byte[] result = Digests.md5(signSrc.getBytes());
            md5Sign = Base64Utils.encodeToUrlSafeString(result);
        } catch (IOException e) {
            logger.error("sign error, [signSrc:{}].", signSrc.substring(0, signSrc.lastIndexOf("&")));
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_SIGN_INVALID);
        }

        if (!md5Sign.equals(protocolRequest.getSign())) {
            logger.error("sign check error, [signSrc:{}][sign:{}][paramSign:{}].",
                    signSrc.substring(0, signSrc.lastIndexOf("&")), md5Sign, protocolRequest.getSign());
            throw new BusinessException(BaseBusinessErrorCode.REQUEST_SIGN_INVALID);
        }
    }

    public static String decryptDataToStr(ProtocolRequest protocolRequest, String key)  {
        String bizData = protocolRequest.getData();
        if (org.springframework.util.StringUtils.isEmpty(bizData)) {
            return "";
        }
        byte[] bytes = Base64Utils.decodeFromUrlSafeString(bizData);
        try {
            return CodecUtils.aesCbcDecode(bytes, key);
        } catch (Exception e) {
            logger.info("data decode fail data:{} ", protocolRequest.getData());
        }
        return "";
    }

    private Map<String, String> decryptDataToMap(ProtocolRequest pr, String key) {
        Map<String, String> map = Maps.newHashMap();
        String data = decryptDataToStr(pr, key);
        if (StringUtils.isEmpty(data)) {
            return map;
        }

        JSONObject jsonObject = JSON.parseObject(data);
        if (null == jsonObject) {
            return map;
        }
        jsonObject.keySet().forEach(k -> {
            map.put(k, jsonObject.getString(k));
        });

        return map;
    }

    private boolean isStaticResourceRequest(String url) {
        String reg = ".+(.html|.css|.js|.jpeg|.jpg|.png|.json|.ico|.txt)";
        Pattern pattern = Pattern.compile(reg);
        Matcher matcher = pattern.matcher(url);
        return matcher.find();
    }

}
