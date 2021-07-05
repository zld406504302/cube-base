package cn.cube.base.web.handler;

import cn.cube.base.core.exception.BaseBusinessErrorCode;
import cn.cube.base.core.exception.BusinessException;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.web.annotation.RequestSafety;
import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Description:HandlerProxy
 * 具体请求url对应的处理proxy
 * Author:zhanglida
 * Date:2020/8/25
 * Email:406504302@qq.com
 */
public class HandlerProxy {
    private static final Logger logger = LoggerUtils.getLogger(HandlerProxy.class);

    private Object target;
    private Method method;
    private RequestSafety handlerParam;

    public HandlerProxy(Object target, Method method, RequestSafety handlerParam) {
        this.target = target;
        this.method = method;
        this.handlerParam = handlerParam;
    }

    public Object invoke(String bizData) throws InvocationTargetException, IllegalAccessException {
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length == 0) {
            return method.invoke(target);
        }

        Object param;
        try {
            param = JSON.parseObject(bizData, parameterTypes[0]);
        } catch (Exception e) {
            logger.error("biz_data convert error, [biz_data:{}][classType:{}].", bizData, parameterTypes[0].getTypeName(), e);
            throw new RuntimeException(new BusinessException(BaseBusinessErrorCode.REQUEST_PARAMS_INVALID));
        }
        return method.invoke(target, param);
    }

    public Method getMethod() {
        return method;
    }

    public boolean auth() {
        if (null == handlerParam) {
            return false;
        }
        return handlerParam.auth();
    }

    public boolean encrypt() {
        if (null == handlerParam) {
            return false;
        }
        return handlerParam.encrypt();
    }
}
