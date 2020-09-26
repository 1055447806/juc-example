package com.ohh.juc.executor.executorService;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 拒绝策略演示
 */
public class ExecutorServiceExample2 {
    public static void main(String[] args) throws InterruptedException {
//        testAbortPolicy();
//        testDiscardPolicy();
//        testCallerRunsPolicy();
        testDiscardOldestPolicy();
    }

    /**
     * 使用AbortPolicy，会抛出RejectedExecutionException异常
     */
    @SuppressWarnings("DuplicatedCode")
    private static void testAbortPolicy() throws InterruptedException {
        ExecutorService executorService =
                new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> System.out.println("abort policy"));

        System.out.println("all of mission executed");
    }

    /**
     * 使用DiscardPolicy，任务被直接丢弃，不建议使用
     */
    @SuppressWarnings("DuplicatedCode")
    private static void testDiscardPolicy() throws InterruptedException {
        ExecutorService executorService =
                new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.DiscardPolicy());
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> System.out.println("discard policy"));

        System.out.println("all of mission executed");
    }

    /**
     * 使用CallerRunsPolicy，如果线程池没有被关闭，则由提交任务的线程代替线程池来执行
     */
    @SuppressWarnings("DuplicatedCode")
    private static void testCallerRunsPolicy() throws InterruptedException {
        ExecutorService executorService =
                new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 3; i++) {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> {
            System.out.println("callerRuns policy");
            System.out.println("mission is running in [" + Thread.currentThread().getName() + "] thread.");
        });

        System.out.println("all of mission executed");
    }

    /**
     * 使用DiscardOldestPolicy，当有新的任务提交的时候，丢弃队列最前面的（下一个即将被执行的）任务，再提交新任务
     */
    @SuppressWarnings("DuplicatedCode")
    private static void testDiscardOldestPolicy() throws InterruptedException {
        ExecutorService executorService =
                new ThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(1), Thread::new, new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            executorService.execute(() -> {
                try {
                    System.out.println("mission[" + finalI + "] is running");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }

        TimeUnit.SECONDS.sleep(1);

        executorService.execute(() -> System.out.println("discard oldest policy"));

        System.out.println("all of mission executed");
    }
}
