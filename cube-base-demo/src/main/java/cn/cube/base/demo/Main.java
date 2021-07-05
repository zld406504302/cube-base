package cn.cube.base.demo;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.cloudfoundry.servlet.CloudFoundryActuatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.health.HealthContributorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.web.server.ManagementContextAutoConfiguration;
import org.springframework.boot.autoconfigure.context.PropertyPlaceholderAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.autoconfigure.ConfigurationPropertiesRebinderAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshEndpointAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Import;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Description:Main
 * Author:zhanglida
 * Date:2019/12/19
 * Email:406504302@qq.com
 */
@Configuration
@Import({
        ConfigurationPropertiesRebinderAutoConfiguration.class,
        PropertyPlaceholderAutoConfiguration.class,
        EndpointAutoConfiguration.class,
        ManagementContextAutoConfiguration.class,
        HealthContributorAutoConfiguration.class,
        DispatcherServletAutoConfiguration.class,
        ThymeleafAutoConfiguration.class,
        RefreshAutoConfiguration.class,
        RefreshEndpointAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        cn.cube.base.core.config.DataSourceConfig.class,
        WebEndpointAutoConfiguration.class,
        CloudFoundryActuatorAutoConfiguration.class,
})
@EnableWebMvc
@ComponentScan(value = "cn.cube")
@EnableAspectJAutoProxy(exposeProxy = true)
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /**
     * 监听SpringBoot 启动完毕 开始部署Vertx
     *
     * @param event
     */
    @EventListener
    public void deployVertx(ApplicationReadyEvent event) {

        DeploymentOptions options = new DeploymentOptions().setWorker(true);
        options.setWorkerPoolSize(2);
        options.setInstances(2);
        options.setWorkerPoolName("verticle-pool");
        Vertx vertx = Vertx.vertx();
        //部署vertx
        vertx.deployVerticle("cn.cube.base.demo.VerticleMain",options);

        //vertx.deployVerticle("cn.cube.retail.VerticleMain");
    }
}
