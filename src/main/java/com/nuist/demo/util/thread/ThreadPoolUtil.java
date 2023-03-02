package com.nuist.demo.util.thread;

import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolUtil {
    private static ThreadPoolExecutor poolExecutor = ThreadPoolConfig.getInstance();

    public static boolean submit(Callable callable) throws ExecutionException, InterruptedException {
        Future<Boolean> future = poolExecutor.submit(callable);
        Boolean result = future.get();
        return result;
    }


    public static void submit(Runnable runnable) throws ExecutionException, InterruptedException {
        poolExecutor.execute(runnable);
    }

    public static void shutdown(){
        poolExecutor.shutdown();
    }

    public static boolean awaitTerminal(long timer) throws InterruptedException {
       return poolExecutor.awaitTermination(timer, TimeUnit.SECONDS);
    }

}
