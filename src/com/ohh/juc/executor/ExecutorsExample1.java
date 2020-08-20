package com.ohh.juc.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * newCachedThreadPool:
 * <p>
 * 初始值为0，最大值为Integer.MAX_VALUE，workQueue的大小为0，
 * 每次加入新任务时就ActiveCount就增加1，当空闲时间超过60秒时，
 * 线程被回收，当全部线程被回收后，线程池自动销毁
 * <p>
 * public static ExecutorService newCachedThreadPool() {
 * return new ThreadPoolExecutor(0, Integer.MAX_VALUE,
 * 60L, TimeUnit.SECONDS,
 * new SynchronousQueue<Runnable>());
 * }
 */
public class ExecutorsExample1 {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        System.out.println("executorService.getQueue().size() = " + executorService.getQueue().size());
        System.out.println("executorService.getActiveCount() = " + executorService.getActiveCount());
        for (int i = 0; i < 100; i++) {
            System.out.println("=================================");
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " is execute!");
                sleep(10);
            });
            System.out.println("executorService.getActiveCount() = " + executorService.getActiveCount());
        }
    }

    private static void sleep(long l) {
        try {
            TimeUnit.SECONDS.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
