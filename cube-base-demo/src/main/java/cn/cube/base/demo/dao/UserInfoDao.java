package cn.cube.base.demo.dao;

import cn.cube.base.core.cache.KeyParam;
import cn.cube.base.core.dao.BaseIdDao;
import cn.cube.base.core.db.DB;
import cn.cube.base.demo.entity.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Repository;

/**
 * Description:UserDao
 * Author:zhanglida
 * Date:2018/2/23
 * Email:406504302@qq.com
 */
@Repository
public class UserInfoDao extends BaseIdDao<Long, UserInfo> {

    @Autowired
    private DB db;

    @CacheEvict
    public int updateName(@KeyParam Long id, String name) {
        return db.update(UserInfo.class,"nick",name,"id = ? ",id);
    }
}
