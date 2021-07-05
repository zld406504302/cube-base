package cn.cube.base.demo.dao;

import cn.cube.base.core.cache.KeyParam;
import cn.cube.base.core.dao.BaseDao;
import cn.cube.base.core.db.DB;
import cn.cube.base.demo.entity.AppInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * Description:AppInfoDao
 * Author:zhanglida
 * Date:2020/3/16
 * Email:406504302@qq.com
 */
@Repository
public class AppInfoDao extends BaseDao<AppInfo> {
    @Autowired
    private DB db;

    @Cacheable
    public AppInfo select(@KeyParam String appId) {
        return db.from(AppInfo.class).where("app_id", appId).first(AppInfo.class);
    }
}
