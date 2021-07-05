package cn.cube.base.core.dao;

import cn.cube.base.core.cache.BaseCache;
import cn.cube.base.core.cache.CacheExpire;
import cn.cube.base.core.cache.KeyParam;
import cn.cube.base.core.db.DB;
import cn.cube.base.core.entity.IdEntity;
import cn.cube.base.core.util.Beans;
import cn.cube.base.core.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Description:UserDao
 * Author:zhanglida
 * Date:2018/2/23
 * Email:406504302@qq.com
 */
@Repository
public class BaseIdDao<K, V extends IdEntity<K>> extends BaseCache<K, V> implements IBaseIdDao<K, V> {

    @Autowired
    private DB db;

    @Override
    @Cacheable
    @CacheExpire
    public V select(@KeyParam K id) {
        Class<V> vclass = Beans.getGenericType(getClass(), 1);
        return db.from(vclass).where("id", id).first(vclass);
    }

    @Override
    public List<V> selectList(List<K> ids) {

        List<V> cacheUsers = super.getObjects(ids);
        List<K> cacheUids = CollectionUtils.uniqValues(cacheUsers, V::getId);

        List<Long> diffUids = CollectionUtils.diff(ids, cacheUids);
        if (CollectionUtils.isEmpty(diffUids)) {
            return cacheUsers;
        }
        Class<V> vclass = Beans.getGenericType(getClass(), 1);
        List<V> users = db.from(vclass).in("id", diffUids).all(vclass);
        if (!CollectionUtils.isEmpty(users)) {
            cacheUsers.addAll(users);
            Map<K, V> map = CollectionUtils.toMap(users, V::getId);
            super.cacheObjects(map);
        }

        return cacheUsers;
    }

    @Override
    public int insert(V t) {
        return db.insert(t);
    }

    @Override
    @CacheEvict
    public int delete(@KeyParam(key = "id") V v) {
        return db.delete(v);
    }

    @Override
    @CacheEvict
    public int update(@KeyParam(key = "id") V v) {
        return db.update(v);
    }

    @Override
    @CacheEvict
    public int update(@KeyParam(key = "id")  V v, String... properties) {
        return db.update(v,properties);
    }

    @Override
    public DB getDB() {
        return this.db;
    }

    @Override
    public List<V> selectList(Map<String, Object> params) {
        Class<V> vclass = Beans.getGenericType(getClass(), 1);
        return db.from(vclass).where(params).all(vclass);
    }

    @Override
    public List<V> selectList(List<Long> valueList, String paramName) {
        Class<V> vclass = Beans.getGenericType(getClass(), 1);
        return db.from(vclass).in(paramName, valueList).all(vclass);
    }

}
