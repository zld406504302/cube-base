package cn.cube.base.demo;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:2020/2/18
 * Email:406504302@qq.com
 */

import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Main.class)
@ConfigurationPropertiesScan(basePackages ="cn.cube" )
public class BaseTest {

}
