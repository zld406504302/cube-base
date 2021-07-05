package cn.cube.base.web.interceptor;

import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.web.ServerContext;
import cn.cube.base.web.annotation.RequestSafety;
import cn.cube.base.web.protocol.request.ProtocolRequest;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Description:WebLoggerFilter
 * Author:zhanglida
 * Date:2017/12/25
 * Email:406504302@qq.com
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerUtils.getLogger(LoginInterceptor.class);

    @Autowired
    private ILoginService loginService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HandlerMethod h = null;
        if (handler instanceof HandlerMethod) {
            h = (HandlerMethod) handler;
        }
        if (null == h) {
            return true;
        }
        RequestSafety loginAuth = h.getMethodAnnotation(RequestSafety.class);
        if (null == loginAuth) {
            return true;
        }

        ProtocolRequest protocolRequest = ServerContext.getProtocolRequest();
        long uid = protocolRequest.getUid();
        String token = protocolRequest.getToken();
        try {
            loginService.check(uid, token);
            return true;
        } catch (BusinessException e) {
            logger.error("token check failure", e);
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            OutputStream out = response.getOutputStream();
            ProtocolResponse res = new ProtocolResponse(e);
            out.write(JsonUtil.toJson(res).getBytes("UTF-8"));
            out.flush();
        }
        return false;
    }
}
