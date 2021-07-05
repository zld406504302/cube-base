package cn.cube.base.demo.dao;

import cn.cube.base.core.common.ConfigProperties;
import cn.cube.base.core.config.DataSourceConfig;
import cn.cube.base.demo.BaseTest;
import cn.cube.base.demo.Main;
import cn.cube.base.demo.entity.UserInfo;
import org.apache.http.util.Asserts;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

/**
 * Description:TODO
 * Author:zhanglida
 * Date:2021/4/19
 * Email:406504302@qq.com
 */
@SpringBootTest(classes = {Main.class})
@ConfigurationPropertiesScan(basePackages = "cn.cube")
@Import({ConfigProperties.class, DataSourceConfig.class})
@ActiveProfiles("release-local")
public class UserInfoDaoTest extends BaseTest {

    @Autowired
    private UserInfoDao userInfoDao;
    @Test
    public void query(){
        UserInfo user = userInfoDao.select(1L);
        Asserts.notNull(user,"ok");
    }


}
