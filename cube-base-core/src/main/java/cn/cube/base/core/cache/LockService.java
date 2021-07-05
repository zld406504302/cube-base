package cn.cube.base.core.cache;

import cn.cube.base.core.util.LoggerUtils;
import cn.cube.base.core.util.NumberUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 分布式锁处理类，处理分布式锁机制
 * <p>
 * 目前实现使用 redis 做分布式锁
 * <p>
 * Created by Administrator on 2017/3/22.
 */
@Service
public final class LockService {
    private static final Logger logger = LoggerUtils.getLogger(LockService.class);

    @Autowired
    protected RedisTemplateWrapper redisWrapper;

    private static final String REDIS_VALUE_NIL = "nil";

    /**
     * 尝试获取 分布式锁
     *
     * @param uniqueKey
     * @return
     */
    public final boolean tryLock(String uniqueKey) {
        int loopCount = 5;
        for (int i = 1; i <= loopCount; i++) {
            boolean isLock = tryRedisLock(uniqueKey);
            logger.info("try lock status[{}], loop[{}],uniqueKey[{}].", isLock, i, uniqueKey);
            if (isLock) {
                return true;
            }
            if (i == loopCount) {
                logger.warn("try lock failed, loop[{}],uniqueKey[{}].", i, uniqueKey);
                return false;
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                logger.error("try lock error, loop[{}].", i, e);
            }
        }
        return false;
    }

    /**
     * redis 分布式锁实现机制参考 redis setnx 命令描述
     *
     * @param uniqueKey
     * @return
     */
    private boolean tryRedisLock(String uniqueKey) {
        String lockKey = uniqueKey;
        try {
            boolean success = redisWrapper.setIfAbsent(lockKey, lockKey, lockExpiredTimeValue());
            if (success) {
                String lockValue = redisWrapper.get(lockKey);
                if (!REDIS_VALUE_NIL.equals(lockValue)) {
                    long checkTime = NumberUtils.toLong(lockValue) - System.currentTimeMillis();
                    if (checkTime > 0) {
                        return false;
                    }
                }
                String lockedTime = redisWrapper.getAndSet(lockKey, lockKey, lockExpiredTimeValue());
                if (!REDIS_VALUE_NIL.equals(lockedTime)) {
                    long checkTime = NumberUtils.toLong(lockedTime) - System.currentTimeMillis();
                    if (checkTime > 0) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("tryLock error, lockKey[{}].", lockKey, e);
        }
        return true;
    }

    /**
     * 释放 分布式 Redis锁
     *
     * @param uniqueKey
     * @return
     */
    public final boolean unlock(String uniqueKey) {
        String lockKey = uniqueKey;
        redisWrapper.delete(lockKey);
        return true;
    }

    private Long lockExpiredTimeValue() {
        // 分布式锁，锁10*1000+1毫秒
        return (System.currentTimeMillis() + 10 * 1000 + 1)/1000;
    }

}
