package cn.cube.base.demo.handler;

import cn.cube.base.web.handler.IMappingHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Description:TestController
 * Author:zhanglida
 * Date:2020/11/17
 * Email:406504302@qq.com
 */
@Component
public class TestHandler implements IMappingHandler {
    @RequestMapping("/handler/test")
    public String test() {
        return "ok";
    }
}
