package com.ohh.juc.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * newFixedThreadPool:
 * <p>
 * 初始值为n，最大值也为n，workQueue的容量为Integer.MAX_VALUE，
 * 线程数量固定，当有新任务时，将任务加入到workQueue中，
 * 线程被不会自动销毁，需要显式的shutdown
 * <p>
 * return new ThreadPoolExecutor(nThreads, nThreads,
 * 0L, TimeUnit.MILLISECONDS,
 * new LinkedBlockingQueue<Runnable>());
 */
public class ExecutorsExample2 {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println("=================================");
                System.out.println(Thread.currentThread().getName() + " is execute!");
                System.out.println("executorService.getQueue().size() = " + executorService.getQueue().size());
                System.out.println("executorService.getActiveCount() = " + executorService.getActiveCount());
                sleep(10);
            });
        }
        executorService.shutdown();
    }

    private static void sleep(long l) {
        try {
            TimeUnit.SECONDS.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
