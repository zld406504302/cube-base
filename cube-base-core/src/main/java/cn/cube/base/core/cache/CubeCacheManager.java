package cn.cube.base.core.cache;

import cn.cube.base.core.util.AopUtils;
import cn.cube.base.core.util.LoggerUtils;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.ArrayUtils;
import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.stereotype.Repository;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.*;

/**
 * Description:CacheConfiguration
 * Author:zhanglida
 * Date:2018/12/4
 * Email:406504302@qq.com
 */
public class CubeCacheManager extends RedisCacheManager implements ApplicationContextAware, InitializingBean {
    private static final Logger logger = LoggerUtils.getLogger(CubeCacheManager.class);
    private ApplicationContext applicationContext;
    private RedisCacheConfiguration defaultCacheConfig;
    Map<String, RedisCacheConfiguration> cacheConfigurationMap = Maps.newHashMap();

    public CubeCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
        this.defaultCacheConfig = defaultCacheConfiguration;
    }

    @Override
    protected Collection<RedisCache> loadCaches() {

        List<RedisCache> caches = new LinkedList<>();

        Map<String, Long> cacheDurationMap = parseCacheDuration();
        cacheDurationMap.forEach((key, value) -> {
            RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(defaultCacheConfig.getTtl()).serializeValuesWith(defaultCacheConfig.getValueSerializationPair());
            if (null != value) {
                redisCacheConfiguration.entryTtl(Duration.ofSeconds(value));
            }
            cacheConfigurationMap.put(key, redisCacheConfiguration);
        });

        for (Map.Entry<String, RedisCacheConfiguration> entry : cacheConfigurationMap.entrySet()) {
            caches.add(createRedisCache(entry.getKey(), entry.getValue()));
        }

        return caches;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private Map<String, Long> parseCacheDuration() {
        final Map<String, Long> cacheExpires = new HashMap<>();
        String[] beanNames = applicationContext.getBeanNamesForType(Object.class);
        for (String beanName : beanNames) {
            final Object obj = applicationContext.getBean(beanName);
            Repository service = AnnotationUtils.findAnnotation(obj.getClass(), Repository.class);
            if (null == service) {
                continue;
            }
            addCacheExpires(obj, cacheExpires);
        }
        return cacheExpires;
    }

    private void addCacheExpires(final Object obj, final Map<String, Long> cacheExpires) {
        Class clazz = obj.getClass();
        ReflectionUtils.doWithMethods(obj.getClass(), method -> {
            ReflectionUtils.makeAccessible(method);
            CacheExpire cacheExpire = findCacheDuration(clazz, method);
            Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
            Set<String> cacheNames = findCacheNames(obj, cacheable);
            for (String cacheName : cacheNames) {
                cacheExpires.put(cacheName, cacheExpire.value());
            }
        }, method -> null != AnnotationUtils.findAnnotation(method, Cacheable.class));
    }

    /**
     * CacheDuration标注的有效期，优先使用方法上标注的有效期
     *
     * @param clazz
     * @param method
     * @return
     */
    private CacheExpire findCacheDuration(Class clazz, Method method) {
        CacheExpire methodCacheDuration = AnnotationUtils.findAnnotation(method, CacheExpire.class);
        if (null != methodCacheDuration) {
            return methodCacheDuration;
        }
        CacheExpire classCacheDuration = AnnotationUtils.findAnnotation(clazz, CacheExpire.class);
        if (null != classCacheDuration) {
            return classCacheDuration;
        }
        return null;
    }

    private Set<String> findCacheNames(Object obj, Cacheable cacheable) {
        Object target = null;
        try {
            target = AopUtils.getTarget(obj);
        } catch (Exception e) {
            logger.error("get proxy target fail", e);
        }
        if (null != target) {
            return ArrayUtils.isEmpty(cacheable.value()) ? new HashSet(Arrays.asList(target.getClass().getName())) : new HashSet(Arrays.asList(cacheable.value()));
        }
        return new HashSet<>();
    }
}
