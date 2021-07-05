package cn.cube.base.core.cache;

import cn.cube.base.core.util.Beans;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Map;

/**
 * Created on 2017/09/26.
 */

public class CacheConfig extends CachingConfigurerSupport {
    private final String KEY_SEPARATOR = ":";

    @Override
    public KeyGenerator keyGenerator() {
        return (o, method, objects) -> {
            StringBuilder sb = new StringBuilder();
            //sb.append(o.getClass().getName());
            Map<Integer, KeyParam> paramKeyMap = getParamKeyMap(method.getParameterAnnotations());
            boolean haveCacheAnnotation = haveCacheAnnotation(method);
            if (haveCacheAnnotation && paramKeyMap.size() == 0) {
                throw new IllegalArgumentException("KeyParam not set");
            }
            for (int i = 0; i < objects.length; i++) {
                KeyParam keyParam = paramKeyMap.get(i);
                if (null == keyParam) {
                    continue;
                }
                String key = keyParam.key();
                Object paramObj = objects[i];
                Object v = null;
                if (!StringUtils.isEmpty(key)) {
                    v = Beans.get(paramObj, key);
                } else {
                    v = paramObj;
                }

                if (null == v) {
                    throw new IllegalArgumentException("cache key value can't be null");
                }

                if (i == 0 ) {
                    sb.append(v);
                } else {
                    sb.append(KEY_SEPARATOR).append(v);
                }
            }
            return sb.toString();
        };
    }

    private Map<Integer, KeyParam> getParamKeyMap(Annotation[][] parameterAnnotations) {
        Map<Integer, KeyParam> keyParamMap = Maps.newHashMap();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] parameterAnnotation = parameterAnnotations[i];
            for (Annotation annotation : parameterAnnotation) {
                KeyParam keyParam = AnnotationUtils.getAnnotation(annotation, KeyParam.class);
                if (null != keyParam) {
                    keyParamMap.put(i, keyParam);
                }
            }
        }
        return keyParamMap;
    }

    @Bean
    public RedisCacheConfiguration getRedisCacheConfiguration() {
        FastJson2JsonRedisSerializer jackson2JsonRedisSerializer = new FastJson2JsonRedisSerializer(Object.class);
//        ObjectMapper om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
//        jackson2JsonRedisSerializer.setObjectMapper(om);

        RedisSerializationContext.SerializationPair<Object> pair = RedisSerializationContext.SerializationPair
                .fromSerializer(jackson2JsonRedisSerializer);
        return RedisCacheConfiguration.defaultCacheConfig().disableCachingNullValues()
                .entryTtl(Duration.ofHours(24)).serializeValuesWith(pair);
    }

    private boolean haveCacheAnnotation(Method method) {
        Cacheable cacheable = AnnotationUtils.findAnnotation(method, Cacheable.class);
        if (null != cacheable) {
            return true;
        }
        CachePut cachePut = AnnotationUtils.findAnnotation(method, CachePut.class);
        if (null != cachePut) {
            return true;
        }
        CacheEvict cacheEvict = AnnotationUtils.findAnnotation(method, CacheEvict.class);
        if (null != cacheEvict) {
            return true;
        }
        return false;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return new CubeCacheErrorHandler();
    }


}
