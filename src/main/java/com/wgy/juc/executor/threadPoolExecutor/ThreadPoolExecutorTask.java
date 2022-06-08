package com.wgy.juc.executor.threadPoolExecutor;

import java.util.List;
import java.util.concurrent.*;

/**
 * shutdown方法会在当前执行的和队列中的任务结束后关闭线程池，
 * awaitTermination方法会阻塞线程直到线程池中所有线程被关闭
 * <p>
 * shutdownNow方法会中断所有工作线程，并排干blockingQueue再返回
 */
public class ThreadPoolExecutorTask {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = new ThreadPoolExecutor(10, 20, 15, TimeUnit.SECONDS, new ArrayBlockingQueue<>(10), (ThreadFactory) Thread::new, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 20; i++) {
            executorService.execute(() -> {
                sleep(10, TimeUnit.SECONDS);
                System.out.println(Thread.currentThread().getName() + ": is finished");
            });
        }
        /*
        //shutdown
        executorService.shutdown();
        executorService.awaitTermination(60, TimeUnit.SECONDS);
        */

        //shutdownNow
        List<Runnable> runnableList = executorService.shutdownNow();
        System.out.println("executor call shutdown...");
        System.out.println("runnableList.size() = " + runnableList.size());
    }

    private static void sleep(long l, TimeUnit t) {
        try {
            t.sleep(l);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " be interrupt!");
        }
    }
}
