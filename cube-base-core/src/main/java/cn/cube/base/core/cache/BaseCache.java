package cn.cube.base.core.cache;

import cn.cube.base.core.util.CollectionUtils;
import cn.cube.base.core.util.DateUtils;
import org.apache.commons.collections4.MapUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Description:BaseDao
 * Author:zhanglida
 * Date:2018/3/21
 * Email:406504302@qq.com
 */
public abstract class BaseCache<K, V> {
    public final Long CACHE_TIMES = 2 * DateUtils.ONE_DAY_SECONDS;
    @Autowired
    private RedisTemplateWrapper redisTemplate;

    public void cacheObjects(Map<K, V> kvMap) {
        if (MapUtils.isEmpty(kvMap)) {
            return;
        }
        kvMap.forEach((k, v) -> {
            String key = cacheKey(this.getClass(), k);
            redisTemplate.set(key, v);
            redisTemplate.expire(key,CACHE_TIMES);
        });
    }

    public List<V> getObjects(List<K> kList) {
        if (CollectionUtils.isEmpty(kList)) {
            return Lists.newArrayList();
        }
        List<V> vList = Lists.newArrayList();
        kList.forEach(k -> {
            String key = cacheKey(this.getClass(), k);
            V v = redisTemplate.get(key);
            if (v != null) {
                vList.add(v);
            }
        });
        return vList;
    }

    /**
     * 删除缓存
     *
     * @param k
     */
    public void deleteCache(K k) {
        String key = cacheKey(this.getClass(), k);
        redisTemplate.delete(key);
    }

    private String cacheKey(Class<?> vClazz, K key) {
        return getClassFullName(vClazz) + "::" + key;
    }

    private String getClassFullName(Class<?> vClazz) {
        return vClazz.getPackage().getName() + "." + vClazz.getSimpleName();
    }

    public List<K> unCacheList(List<K> kList, List<V> cacheList, Function<? super V, ? extends K> mapper) {
        List<K> cacheKList = CollectionUtils.uniqValues(cacheList, mapper);

        List<K> unCacheList = Lists.newArrayList();
        for (K k : kList) {
            if (!cacheKList.contains(k)) {
                unCacheList.add(k);
            }
        }
        return unCacheList;
    }

}
