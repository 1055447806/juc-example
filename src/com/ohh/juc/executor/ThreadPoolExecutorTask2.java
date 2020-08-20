package com.ohh.juc.executor;

import java.util.concurrent.*;

/**
 * 如果线程中没有对中断进行捕获，那么shutdown方法和shutdownNow方法就无法关闭线程，
 * 此时可以通过在ThreadFactory中将线程设置为守护线程解决
 */
public class ThreadPoolExecutorTask2 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10),
                r -> {
                    Thread t = new Thread(r);
                    t.setDaemon(true);
                    return t;
                }, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 1; i++) {
            executorService.submit(() -> {
                while (true) {

                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        System.out.println("sequence start");
    }
}
