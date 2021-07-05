package cn.cube.base.third.pay.dao;

import cn.cube.base.core.cache.RedisTemplateWrapper;
import cn.cube.base.core.db.DB;
import cn.cube.base.third.pay.entity.PayConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Description:PayConfigDao
 * Author:zhanglida
 * Date:2021/1/18
 * Email:406504302@qq.com
 */
@Repository
public class PayConfigDao {
    @Autowired
    private DB db;

    @Autowired
    private RedisTemplateWrapper redisTemplateWrapper;

    public List<PayConfig> selectList() {
//        String key = this.getClass().getName() + "." + this.getClass().getSimpleName();
//        List<PayConfig> result = redisTemplateWrapper.get(key);
//        if (!CollectionUtils.isEmpty(result)) {
//            return result;
//        }
//        result = db.from(PayConfig.class).where("enable", 1).all(PayConfig.class);
//        if (!CollectionUtils.isEmpty(result)) {
//            redisTemplateWrapper.set(key, result);
//        }
//        return result;
        return db.from(PayConfig.class).where("enable", 1).all(PayConfig.class);
    }
}
