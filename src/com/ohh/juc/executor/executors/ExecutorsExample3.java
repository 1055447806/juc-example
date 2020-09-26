package com.ohh.juc.executor.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * newSingleThreadExecutor:
 * <p>
 * 初始值为1，最大值也为1，workQueue的容量为Integer.MAX_VALUE，
 * 只有一个线程，当有新任务时，将任务加入到workQueue中，
 * 线程被不会自动销毁，需要显式的shutdown
 * <p>
 * return new FinalizableDelegatedExecutorService
 * (new ThreadPoolExecutor(1, 1,
 * 0L, TimeUnit.MILLISECONDS,
 * new LinkedBlockingQueue<Runnable>()));
 */
public class ExecutorsExample3 {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + " execute!");
                sleep(1);
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
