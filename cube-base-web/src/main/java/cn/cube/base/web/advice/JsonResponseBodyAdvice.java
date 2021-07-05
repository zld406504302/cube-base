package cn.cube.base.web.advice;

import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import org.slf4j.Logger;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * Description:Json Response 增强
 * 1：校验Response messageConvert
 * 2：预防controller return null 时丢失统一的响应结构
 * Author:zhanglida
 * Date:2017/7/3
 * Email:406504302@qq.com
 */
@ControllerAdvice
@RestController
public class JsonResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    private static final Logger logger = LoggerUtils.getLogger(JsonResponseBodyAdvice.class);

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        boolean isSupport = MappingJackson2HttpMessageConverter.class.isAssignableFrom(converterType);
        return isSupport;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        ProtocolResponse protocolResponse = new ProtocolResponse();
        ServletServerHttpRequest req= (ServletServerHttpRequest) request;
        String timestamp = req.getServletRequest().getParameter("timestamp");
        if (null == body) {
            return protocolResponse;
        } else if (body instanceof ProtocolResponse) {
            return body;
        } else {
            protocolResponse.setResult(body);
        }
        protocolResponse.setTimestamp(timestamp);
        return protocolResponse;
    }

}
