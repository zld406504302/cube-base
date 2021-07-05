package cn.cube.base.core.util;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Description:线程池工具类
 * Author:zhanglida
 * Date:2017/4/10
 * Email:406504302@qq.com
 */
public class TaskExecutorUtil {
    private static final ThreadPoolExecutor executor;

    static {
        executor = new ThreadPoolExecutor(16, 32, 30, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(100));
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
    }

    public static void post(Runnable runnable) {
        executor.execute(runnable);
    }

}
