package cn.cube.base.demo.config;

import cn.cube.base.core.service.IAppInfoService;
import cn.cube.base.web.filter.WebProtocolFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:FilterConfig
 * Author:zhanglida
 * Date:2018/2/26
 * Email:406504302@qq.com
 */
@Configuration
public class FilterConfig {

    @Autowired
    IAppInfoService appInfoService;

    @Bean
    public WebProtocolFilter getWebProtocolFilter(){
        return new WebProtocolFilter(appInfoService);
    }


    @Bean
    public FilterRegistrationBean webLoggerFilterBean(WebProtocolFilter protocolFilter) {

        List<String> urlPatterns = new ArrayList<>();
        urlPatterns.add("/*");

        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(protocolFilter);
        registrationBean.setName("webProtocolFilter");
        registrationBean.setUrlPatterns(urlPatterns);

        return registrationBean;
    }

}
