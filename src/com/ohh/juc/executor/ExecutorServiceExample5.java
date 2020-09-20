package com.ohh.juc.executor;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ExecutorServiceExample5 {

    public static void main(String[] args) throws InterruptedException {
        testGetQueue();
    }

    /**
     * 使用 getQueue 方法可以获取线程池的队列，可以使用 add 方法将任务添加到队列中，
     * 但是想要任务被执行，需要存在 active 的线程，否则任务不会被触发。
     */
    private static void testGetQueue() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        executorService.execute(() -> System.out.println("execute a mission."));
        TimeUnit.SECONDS.sleep(3);
        executorService.getQueue().add(() -> System.out.println("get Queue and add a mission."));
    }
}
