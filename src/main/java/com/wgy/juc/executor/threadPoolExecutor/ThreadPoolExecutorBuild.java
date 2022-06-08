package com.wgy.juc.executor.threadPoolExecutor;

import java.util.concurrent.*;

/**
 * CorePoolSize: 初始值
 * MaximumPoolSize: 最大值
 * 当queueSize满了的时候activeCount的数量增加，
 * 当queueSize满了的时候，并且activeCount的数量已经达到MaximumPoolSize，再提交的任务会执行拒绝策略RejectedExecutionHandler
 * 当线程数量大于CorePoolSize，且存在空闲线程时，在空闲keepAliveTime的时间之后，空闲线程会被回收。
 */
public class ThreadPoolExecutorBuild {
    public static void main(String[] args) {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) buildThreadPoolExecutor();
        int activeCount = -1;
        int queueSize = -1;
        while (true) {
            int currentActiveCount = executorService.getActiveCount();
            int currentQueueSize = executorService.getQueue().size();
            if (activeCount != currentActiveCount || queueSize != currentQueueSize) {
                System.out.println("--------");
                System.out.println("executorService.getCorePoolSize() = " + executorService.getCorePoolSize());
                System.out.println("executorService.getMaximumPoolSize() = " + executorService.getMaximumPoolSize());
                System.out.println("executorService.getActiveCount() = " + currentActiveCount);
                System.out.println("executorService.getQueue().size() = " + currentQueueSize);
                activeCount = currentActiveCount;
                queueSize = currentQueueSize;
                System.out.println("--------");
            }
        }
    }

    private static ExecutorService buildThreadPoolExecutor() {
        ExecutorService executorService = new ThreadPoolExecutor(1, 2, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(1), (ThreadFactory) Thread::new, new ThreadPoolExecutor.AbortPolicy());
        System.out.println("The ThreadPoolExecutor created!");
        sleep(1, TimeUnit.SECONDS);
        executorService.execute(() -> sleep(5, TimeUnit.SECONDS));
        executorService.execute(() -> sleep(5, TimeUnit.SECONDS));
        executorService.execute(() -> sleep(5, TimeUnit.SECONDS));
        return executorService;
    }

    private static void sleep(long l, TimeUnit t) {
        try {
            String name = Thread.currentThread().getName();
            if (!name.equals("main")) {
                System.out.println(name + ": is running...");
            }
            t.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
