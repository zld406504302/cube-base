package cn.cube.base.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:2021/5/20
 * Email:zhanglida@chinabr.net
 */
@RestController
public class TestController {

    @RequestMapping("/spring-boot")
    public String greeting() {
        return "OK";
    }
}
