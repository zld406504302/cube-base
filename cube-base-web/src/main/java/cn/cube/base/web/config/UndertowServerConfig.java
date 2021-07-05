package cn.cube.base.web.config;

import cn.cube.base.core.service.IAppInfoService;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.web.facade.FacadeProcessor;
import cn.cube.base.web.handler.UndertowEncryptHttpHandler;
import cn.cube.base.web.handler.UndertowCommonHttpHandler;
import cn.cube.base.web.handler.HandlerProxy;
import cn.cube.base.web.interceptor.ILoginService;
import io.undertow.Handlers;
import io.undertow.server.HttpHandler;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.form.EagerFormParsingHandler;
import io.undertow.server.handlers.form.FormEncodedDataDefinition;
import io.undertow.server.handlers.form.FormParserFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
/**
 * Description:WebServerConfig
 * Author:zhanglida
 * Date:2020/11/5
 * Email:406504302@qq.com
 */
@Configuration
public class UndertowServerConfig {
    private static final Logger logger = LoggerUtils.getLogger(UndertowServerConfig.class);
    @Autowired
    private FacadeProcessor facadeProcessor;

    @Autowired
    private ILoginService loginService;

    @Autowired
    private IAppInfoService appInfoService;

    @Bean
    @ConfigurationProperties(prefix = "server.undertow")
    UndertowServletWebServerFactory embeddedUndertowWebFactory(@Value("${server.port}") Integer port) {

        UndertowServletWebServerFactory factory = new UndertowServletWebServerFactory();
        factory.setPort(port);
        factory.addDeploymentInfoCustomizers(deploymentInfo ->
        {
            deploymentInfo.addInitialHandlerChainWrapper(handler -> {
                PathHandler pathHandler = Handlers.path().addPrefixPath("/",handler);
                Map<String, HandlerProxy> apiContainer = facadeProcessor.getApiContainer();
                for(Map.Entry<String, HandlerProxy> entry : apiContainer.entrySet()){
                    String uri = entry.getKey();
                    HandlerProxy invocation = entry.getValue();
                    if(invocation.encrypt()) {
                        pathHandler.addPrefixPath(uri, addFormParsing(new UndertowEncryptHttpHandler(uri, invocation, loginService, appInfoService)));
                    }else {
                        pathHandler.addPrefixPath(uri, addFormParsing(new UndertowCommonHttpHandler(uri, invocation)));
                    }
                }
                return pathHandler;
                }
            );
        });

        return factory;
    }

    private static HttpHandler addFormParsing(final HttpHandler toWrap) {
        HttpHandler handler = toWrap;
        FormParserFactory factory = FormParserFactory.builder().addParser(new FormEncodedDataDefinition()).build();
        EagerFormParsingHandler formHandler = new EagerFormParsingHandler(factory);
        formHandler.setNext(handler);
        handler = formHandler;
        return handler;
    }


}
