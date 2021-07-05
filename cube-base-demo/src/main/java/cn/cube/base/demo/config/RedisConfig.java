package cn.cube.base.demo.config;

import cn.cube.base.core.cache.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created on 2017/09/26.
 */

@Configuration
@EnableCaching(proxyTargetClass = true)
public class RedisConfig extends CacheConfig {

    private CacheManager cacheManager;


    @ConfigurationProperties(prefix = "spring.redis")
    @Bean
    public RedisStandaloneConfiguration getRedisStandaloneConfiguration(){
        return new RedisStandaloneConfiguration();
    }

    @ConfigurationProperties(prefix = "spring.redis.jedis.pool")
    @Bean
    public JedisPoolConfig getJedisPoolConfig(){
        return new JedisPoolConfig();
    }

    @Bean
    public RedisConnectionFactory getRedisConnectionFactory(RedisStandaloneConfiguration configuration,JedisPoolConfig jedisPoolConfig){
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        JedisClientConfiguration jedisClientConfiguration = builder.usePooling().poolConfig(jedisPoolConfig).build();
        JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(configuration,jedisClientConfiguration);
        return jedisConnectionFactory;
    }

    @Autowired
    public void setCacheManager(RedisConnectionFactory factory) {
        RedisCacheConfiguration defaultCacheConfig = getRedisCacheConfiguration();
        cacheManager = new CubeCacheManager(RedisCacheWriter.nonLockingRedisCacheWriter(factory),defaultCacheConfig);
    }

    @Override
    public CacheResolver cacheResolver() {
        return new CubeCacheResolver(cacheManager);
    }

    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory factory, RedisSerializer fastJson2JsonRedisSerializer) {
        StringRedisTemplate template = new StringRedisTemplate(factory);
        template.setValueSerializer(fastJson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    public RedisTemplateWrapper redisTemplateWrapper(RedisTemplate redisTemplate) {
        return new RedisTemplateWrapper(redisTemplate);
    }

    @Bean
    @SuppressWarnings("rawtypes")
    public RedisSerializer fastJson2JsonRedisSerializer() {
        return new FastJson2JsonRedisSerializer<>(Object.class);
    }

}
