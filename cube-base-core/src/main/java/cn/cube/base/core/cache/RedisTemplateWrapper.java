package cn.cube.base.core.cache;

import cn.cube.base.core.util.CollectionUtils;
import com.google.common.collect.Lists;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RedisTemplateWrapper {

    private RedisTemplate redisTemplate;

    public RedisTemplateWrapper(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public <T> T get(String key) {
        return (T) this.redisTemplate.opsForValue().get(key);
    }

    public <T> T getAndSet(String key, T value, Long expire) {
        Object result = this.redisTemplate.opsForValue().getAndSet(key, value);
        expire(key, expire);
        return (T) result;
    }

    public <T> List<T> multiGet(List<String> keys) {
        List<T> tList = (List<T>) this.redisTemplate.opsForValue().multiGet(keys);
        if (CollectionUtils.isEmpty(tList)) {
            return Lists.newArrayList();
        }
        return tList;
    }

    public <T> void set(String key, T value, Long expire) {
        this.redisTemplate.opsForValue().set(key, value);
        expire(key, expire);
    }

    public <T> void set(String key, T value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    public <T> boolean setIfAbsent(String key, T value) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    public <T> boolean setIfAbsent(String key, T value, Long timeout) {
        return this.redisTemplate.opsForValue().setIfAbsent(key, value, timeout, TimeUnit.SECONDS);
    }

    public Long incr(String key) {
        return this.redisTemplate.opsForValue().increment(key, 1);
    }

    public Long incrBy(String key, Integer delta) {
        return this.redisTemplate.opsForValue().increment(key, delta);
    }


    public void hset(String key, String field, String value) {
        this.redisTemplate.opsForHash().put(key, field, value);
    }

    public <T> void hset(String key, String field, T value, Long expire) {
        this.redisTemplate.opsForHash().put(key, field, value);
        expire(key, expire);
    }

    public <T, F> T hget(String key, F field) {
        return (T) this.redisTemplate.opsForHash().get(key, field);
    }

    public void hdel(String key, Object... fields) {
        this.redisTemplate.opsForHash().delete(key, fields);
    }

    public <T, F> List<T> hmget(String key, List<F> fields) {
        HashOperations<String, F, T> operation = this.redisTemplate.opsForHash();
        return operation.multiGet(key, fields);
    }

    public <T, F> void hmset(String key, Map<F, T> map) {
        HashOperations<String, F, T> operation = this.redisTemplate.opsForHash();
        operation.putAll(key, map);
    }

    public Long getExpire(String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public Long getExpire(String key, TimeUnit timeUnit) {
        return this.redisTemplate.getExpire(key, timeUnit);
    }

    public void zadd(String key, String value, double score) {
        this.redisTemplate.opsForZSet().add(key, value, score);
    }

    public void zadd(String key, Set<ZSetOperations.TypedTuple<String>> tuples) {
        this.redisTemplate.opsForZSet().add(key, tuples);
    }

    public <T> List<T> zrange(String key, long start, long end) {
        Set<T> range = this.redisTemplate.opsForZSet().range(key, start, end);
        return Lists.newArrayList(range);
    }

    public <T> List<T> zrevrangeByScore(String key, double max, double min, int offset, int count, Class<T> tClass) {
        Set<ZSetOperations.TypedTuple<T>> typedTuples = this.redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, min, max, offset, count);
        List<T> result = Lists.newArrayList();
        typedTuples.forEach(tTypedTuple -> {
            T value = tTypedTuple.getValue();
            result.add(value);
        });
        return result;
    }

    public <T>  boolean lpush(String key, T value) {
        return this.redisTemplate.opsForList().leftPush(key, value) > 0;
    }

    public <T> T lpop(String key) {
        return (T) this.redisTemplate.opsForList().leftPop(key);
    }

    public <T> T lrange(String key, long start, long end) {
        return (T) this.redisTemplate.opsForList().range(key, start, end);
    }

    public void trim(String key, long start, long end) {
        this.redisTemplate.opsForList().trim(key, start, end);
    }

    public boolean delete(String key) {
        return this.redisTemplate.delete(key);
    }

    public boolean expire(String key, Long expire) {
        return this.redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }


}
