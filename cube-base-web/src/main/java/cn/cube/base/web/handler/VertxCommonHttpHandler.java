package cn.cube.base.web.handler;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.*;
import cn.cube.base.web.ServerContext;
import cn.cube.base.web.protocol.request.ProtocolRequest;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import cn.cube.base.web.util.IpUtils;
import com.alibaba.fastjson.JSON;
import io.undertow.server.HttpServerExchange;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;
import org.slf4j.Logger;

import java.util.Map;

/**
 * Description:CommonHttpHandler
 * Author:zhanglida
 * Date:2020/11/26
 * Email:406504302@qq.com
 */
public class VertxCommonHttpHandler implements Handler<RoutingContext> {
    private  final Logger logger = LoggerUtils.getLogger(VertxCommonHttpHandler.class);

    private HandlerProxy apiInvocation;
    private String method;
    public VertxCommonHttpHandler(String method, HandlerProxy apiInvocation) {
        this.method = method;
        this.apiInvocation = apiInvocation;
    }
    @Override
    public void handle(RoutingContext event) {
        ProtocolRequest request = convertRequest(event.request());
        long inTime = System.currentTimeMillis();
        ProtocolResponse response = handleApiMethod(request);
        String responseStr = JsonUtil.toJson(response);
        long cost = System.currentTimeMillis() - inTime;
        String ip = request.getIp();
        getLogger().info("[uri:{}]  [ip:{}] [cost:{}] [request:{}] [response:{}]", method, request.getIp(), cost + "ms", JsonUtil.toJson(request), responseStr);
        event.response().end(JsonUtil.toJson(responseStr));
    }

    private ProtocolRequest convertRequest(HttpServerRequest request) {
        ProtocolRequest baseRequest = new ProtocolRequest();
        baseRequest.setAppId(request.getParam("appId"));
        baseRequest.setUid(request.getParam("uid"));
        baseRequest.setToken(request.getParam("token"));
        baseRequest.setVersion(request.getParam("version"));
        baseRequest.setSign(request.getParam("sign"));
        baseRequest.setTimestamp(request.getParam("timestamp"));
        baseRequest.setData(request.getParam("data"));
        baseRequest.setPlatform(request.getParam("platform"));
        String ipAddress = IpUtils.getIpAddress(request);
        baseRequest.setIp(ipAddress);

        return baseRequest;
    }

    /**
     * api ??????????????????
     *
     * @param protocolRequest
     * @return
     */
    public ProtocolResponse handleApiMethod(ProtocolRequest protocolRequest) {
        ProtocolResponse response = new ProtocolResponse();

        // ?????? method
        if (StringUtils.isEmpty(this.method)) {
            // method ??????
            response.setProtocolError(BaseBusinessErrorCode.REQUEST_API_IS_NULL);
            return response;
        }

        // ??????method??????
        if (apiInvocation == null) {
            response.setProtocolError(BaseBusinessErrorCode.REQUEST_API_IS_NULL);
            return response;
        }

        try {

            // ?????????????????? - ????????????
            ServerContext.setProtocolRequest(protocolRequest);

            Object result = apiInvocation.invoke(protocolRequest.getData());
            response.setResult(result);
            response.setProtocolError(BaseBusinessErrorCode.NO_ERROR);

        } catch (Throwable t) {
            handleError(protocolRequest, response, t);
        } finally {
            /** ?????????????????? */
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
