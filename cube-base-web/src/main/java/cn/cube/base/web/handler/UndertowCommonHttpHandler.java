package cn.cube.base.web.handler;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.*;
import cn.cube.base.web.ServerContext;
import cn.cube.base.web.protocol.request.ProtocolRequest;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import cn.cube.base.web.util.IpUtils;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.server.handlers.form.FormData;
import io.undertow.server.handlers.form.FormDataParser;
import org.slf4j.Logger;

import java.util.Deque;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
/**
 * Description:CommonHttpHandler
 * Author:zhanglida
 * Date:2020/11/26
 * Email:406504302@qq.com
 */
public class UndertowCommonHttpHandler implements HttpHandler {
    private  final Logger logger = LoggerUtils.getLogger(UndertowCommonHttpHandler.class);

    private HandlerProxy apiInvocation;
    private String method;
    public UndertowCommonHttpHandler(String method, HandlerProxy apiInvocation) {
        this.method = method;
        this.apiInvocation = apiInvocation;
    }

    @Override
    public void handleRequest(HttpServerExchange exchange) {
        FormData data = exchange.getAttachment(FormDataParser.FORM_DATA);
        long inTime = System.currentTimeMillis();
        Map<String, Deque<String>> params = exchange.getQueryParameters();
        Map<String, Object> map = toMap(data, params);
        ProtocolRequest request = convertRequest(exchange, map);
        ProtocolResponse response = handleApiMethod(request);
        String responseStr = JsonUtil.toJson(response);
        long cost = System.currentTimeMillis() - inTime;
        String ip = IpUtils.getIpAddress(exchange);
        request.setIp(ip);
        getLogger().info("[uri:{}]  [ip:{}] [cost:{}] [request:{}] [response:{}]", method, request.getIp(), cost + "ms", JsonUtil.toJson(request), responseStr);
        exchange.getResponseSender().send(responseStr);
    }

    /**
     * api 接口处理入口
     *
     * @param protocolRequest
     * @return
     */
    public ProtocolResponse handleApiMethod(ProtocolRequest protocolRequest) {
        ProtocolResponse response = new ProtocolResponse();

        // 获取 method
        if (StringUtils.isEmpty(this.method)) {
            // method 为空
            response.setProtocolError(BaseBusinessErrorCode.REQUEST_API_IS_NULL);
            return response;
        }

        // 查找method函数
        if (apiInvocation == null) {
            response.setProtocolError(BaseBusinessErrorCode.REQUEST_API_IS_NULL);
            return response;
        }

        try {

            // 设置线程变量 - 协议请求
            ServerContext.setProtocolRequest(protocolRequest);

            Object result = apiInvocation.invoke(protocolRequest.getData());
            response.setResult(result);
            response.setProtocolError(BaseBusinessErrorCode.NO_ERROR);

        } catch (Throwable t) {
            handleError(protocolRequest, response, t);
        } finally {
            /** 删除线程变量 */
            ServerContext.removeProtocolRequest();
        }

        return response;
    }

    public void handleError(ProtocolRequest protocolRequest, ProtocolResponse response, Throwable t) {
        Throwable cause = t.getCause();
        if (cause != null) {
            t = cause;
        }

        if (t instanceof BusinessException) {
            response.setCode(((BusinessException) t).getCode());
            response.setMsg(((BusinessException) t).getMsg());
            response.setResult(((BusinessException) t).getResult());
        } else {
            response.setProtocolError(BaseBusinessErrorCode.SYSTEM_UNKNOWN_ERROR);
            getLogger().error("Method[{}] invoke error [request:{}]",
                    this.method, JSON.toJSONString(protocolRequest), t);
        }
    }



    public Map<String, Object> toMap(FormData data, Map<String, Deque<String>> map) {
        if (CollectionUtils.isEmpty(map) && null == data) {
            return null;
        }

        Map<String, Object> params = Maps.newHashMap();
        if (null != data) {
            Iterator<String> iterator = data.iterator();
            while (iterator.hasNext()) {
                String key = iterator.next();
                params.put(key, data.getFirst(key).getValue());
            }
        }

        Set<String> keys = map.keySet();
        for (String key : keys) {
            String value = map.get(key).getFirst();
            params.put(key, value);
        }
        return params;

    }

    public ProtocolRequest convertRequest(HttpServerExchange exchange, Map<String, Object> params) {
        if (CollectionUtils.isEmpty(params)) {
            return new ProtocolRequest();
        }
        return JsonUtil.toObj(JsonUtil.toJson(params), ProtocolRequest.class);
    }

    public HandlerProxy getApiInvocation() {
        return apiInvocation;
    }

    public String getMethod() {
        return method;
    }

    public  Logger getLogger() {
        return logger;
    }
}
