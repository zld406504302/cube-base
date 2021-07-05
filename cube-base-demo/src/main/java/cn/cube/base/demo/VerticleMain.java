package cn.cube.base.demo;

import cn.cube.base.core.util.JsonUtil;
import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.web.protocol.response.ProtocolResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;
import org.slf4j.Logger;

/**
 * Description:VerticleMain
 * Author:zhanglida
 * Date:2021/1/23
 * Email:zhanglida@chinabr.net
 */
public class VerticleMain extends AbstractVerticle {
    private static final Logger logger = LoggerUtils.getLogger(VerticleMain.class);


    private Integer counter = 0;
    @Override
    public void start() throws Exception {
        super.start();
        //路由
        Router router = Router.router(vertx);
        //编写一个get方法 并且输出 "success" 字符串
        router.get("/api/get1").handler(handler -> {
            logger.info("xxxget1xxx");
            ProtocolResponse res = new ProtocolResponse();
            res.setResult("ok");
            int round = Math.round(10);
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.response().end(JsonUtil.toJson(res));
        });

        //编写一个get方法 并且输出 "success" 字符串
        router.get("/api/get2").handler(handler -> {
            logger.info("xxxget2xxx");
            ProtocolResponse res = new ProtocolResponse();
            res.setResult("ok");
            handler.response().end(JsonUtil.toJson(res));
        });

        //start listen port
        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router).listen(8888, handler -> {
            logger.info("vertx run port : [{}] run state : [{}]", 8888, handler.succeeded());
        });
    }
}