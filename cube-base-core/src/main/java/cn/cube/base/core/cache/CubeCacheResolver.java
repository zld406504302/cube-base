package cn.cube.base.core.cache;

import cn.cube.base.core.util.CollectionUtils;
import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.CacheOperationInvocationContext;
import org.springframework.cache.interceptor.SimpleCacheResolver;

import java.util.Collection;
import java.util.Set;

/**
 * Description:CacheResolver
 * Author:zhanglida
 * Date:2018/12/5
 * Email:406504302@qq.com
 */
public class CubeCacheResolver extends SimpleCacheResolver {

    public CubeCacheResolver(){
        super();
    }
    public CubeCacheResolver(CacheManager cacheManager) {
        super(cacheManager);
    }

    @Override
    protected Collection<String> getCacheNames(CacheOperationInvocationContext<?> context) {
        Set<String> cacheNames = context.getOperation().getCacheNames();
        if (CollectionUtils.isEmpty(cacheNames)){
            String cacheName = context.getTarget().getClass().getName();
            cacheNames.add(cacheName);
        }
        return cacheNames;
    }
}
