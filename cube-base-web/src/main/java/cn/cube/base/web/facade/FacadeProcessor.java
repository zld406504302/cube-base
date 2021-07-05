package cn.cube.base.web.facade;

import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.web.annotation.RequestSafety;
import cn.cube.base.web.handler.HandlerProxy;
import cn.cube.base.web.handler.IMappingHandler;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * api 实际处理类
 *
 * Created by Administrator on 2017/3/16.
 */
@Component
public class FacadeProcessor {
    private static final Logger logger = LoggerUtils.getLogger(FacadeProcessor.class);

    private Map<String, HandlerProxy> apiContainer = new HashMap<>();

    @Autowired
    public void setHandlers(List<IMappingHandler> handlers) {
        if (handlers == null || handlers.isEmpty()) {
            throw new RuntimeException("No handler is provided.");
        }
        for (IMappingHandler handler : handlers) {
            Method[] methods = handler.getClass().getMethods();
            RequestMapping annotation = AnnotationUtils.findAnnotation(handler.getClass(), RequestMapping.class);
            String classMapping = getClassMapping(annotation);
            for (Method method : methods) {
                RequestMapping operation = method.getAnnotation(RequestMapping.class);
                RequestSafety handlerParam = method.getAnnotation(RequestSafety.class);
                if (operation == null) {
                    continue;
                }
                if (operation.value() == null || operation.value().length ==0 ){
                    continue;
                }
                String methodMapping = operation.value()[0];
                String mapping = classMapping+methodMapping;
                HandlerProxy apiInvocation = new HandlerProxy(handler, method,handlerParam);
                if (apiContainer.putIfAbsent(mapping, apiInvocation) != null) {
                    logger.error("Api method[{}] already existed",mapping);
                    throw new RuntimeException("Api method[" + mapping + "] already existed.");
                }
            }
        }
    }


    private String getClassMapping(RequestMapping annotation){
        if(null == annotation || null == annotation.value() || annotation.value().length == 0){
            return "";
        }
        return annotation.value()[0];
    }
    public Map<String, HandlerProxy> getApiContainer() {
        return apiContainer;
    }
}
