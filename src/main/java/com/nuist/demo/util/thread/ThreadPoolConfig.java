package com.nuist.demo.util.thread;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class
ThreadPoolConfig {
    /**
     * 核心线程数
     */
    private static final int corePoolSize = 120;
    /**
     * 最大线程数
     */
    private static final int maxPoolSize = 400;

    /**
     * 多余线程最大空闲时间
     */
    private static final int keepAlive = 100;
    /**
     * 线程池缓冲队列
     *  如果不手动指定容量，默认为Integer.MAX_VALUE，也就是无界队列。
     */


    private static final BlockingQueue poolQueue = new LinkedBlockingQueue(256);

    private static volatile ThreadPoolExecutor poolExecutor;

    private ThreadPoolConfig(){}

    public static ThreadPoolExecutor getInstance(){
        if (poolExecutor == null){
            synchronized (ThreadPoolConfig.class){
                if (poolExecutor == null){
                    poolExecutor = new ThreadPoolExecutor(corePoolSize,
                            maxPoolSize,
                            keepAlive,
                            TimeUnit.SECONDS,
                            poolQueue,
                            new ThreadPoolExecutor.DiscardOldestPolicy());
                }
            }
        }
        return poolExecutor;
    }

}
