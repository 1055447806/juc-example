package com.wgy.juc.executor.executorService;

import java.util.concurrent.*;
import java.util.stream.IntStream;

public class ExecutorServiceExample3 {

    @SuppressWarnings("SpellCheckingInspection")
    public static void main(String[] args) throws InterruptedException {
//        test();
//        testAllowCoreThreadTimeOut();
//        testRemove();
//        testPrestartCoreThread();
//        testPrestartAllCoreThreads();
        testThreadPoolAdvice();
    }

    /**
     * 创建了一个 corePoolSize 为 5 的线程池，当任务执行完成后，线程池也不会销毁
     */
    private static void test() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        System.out.println("开始活动线程数 ActiveCount: " + executorService.getActiveCount());
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        TimeUnit.MILLISECONDS.sleep(20);
        System.out.println("提交任务后活动线程数 ActiveCount: " + executorService.getActiveCount());
    }

    /**
     * 通过 allowCoreThreadTimeOut 方法可以设置 corePoolThread 允许被回收，
     * 但要注意的是使用 allowCoreThreadTimeOut 方法时，需要保证 keepAliveTime 的值不为 0
     */
    private static void testAllowCoreThreadTimeOut() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(5);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);
        IntStream.range(0, 5).forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    /**
     * remove 方法可以移除等待队列中的线程。
     */
    private static void testRemove() throws InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        executorService.setKeepAliveTime(10, TimeUnit.SECONDS);
        executorService.allowCoreThreadTimeOut(true);
        IntStream.range(0, 2).forEach(i -> {
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("mission[" + i + "] is done.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        });
        TimeUnit.MILLISECONDS.sleep(20);
        Runnable r = () -> System.out.println("be removed!");
        executorService.execute(r); //因为被remove了，所以不会被执行
        TimeUnit.MILLISECONDS.sleep(20);
        executorService.remove(r);
    }

    /**
     * 当线程池初始化之后，active 线程的数量为 0，每次提交任务之后，active 线程的数量 +1，
     * 但是可以通过使用 prestartCoreThread 方法使核心线程预启动，
     * 每次调用 prestartCoreThread 方法，都会预启动一个核心线程，并返回 true
     * 当 active 的线程数量达到了核心线程数时，prestartCoreThread 方法不会再启动新线程并返回 false
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static void testPrestartCoreThread() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        System.out.println("初始化完成ActiveCount: " + executorService.getActiveCount());

        IntStream.range(0, 3).forEach(i -> {
            System.out.println(" prestartCoreThread: " + executorService.prestartCoreThread());
            System.out.println("ActiveCount: " + executorService.getActiveCount());
        });
    }

    /**
     * prestartAllCoreThreads 方法与 prestartCoreThreads 的区别是，
     * prestartAllCoreThreads 方法会启动所有coreThread，并返回启动数量。
     */
    @SuppressWarnings("SpellCheckingInspection")
    private static void testPrestartAllCoreThreads() {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
        System.out.println("初始化完成ActiveCount: " + executorService.getActiveCount());
        int startCount = executorService.prestartAllCoreThreads();
        System.out.println("启动了 " + startCount + " 个线程");
        System.out.println("ActiveCount: " + executorService.getActiveCount());
    }

    /**
     * 可以通过 beforeExecute 和 afterExecute 方法实现通知
     */
    private static void testThreadPoolAdvice() throws InterruptedException {
        ThreadPoolExecutor executorService = new MyThreadPoolExecutor(1, 2, 30, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1), (ThreadFactory) Thread::new, new ThreadPoolExecutor.AbortPolicy());
        executorService.execute(new MyRunnable(1) {
            @Override
            public void run() {
                System.out.println("running...");
            }
        });

        TimeUnit.SECONDS.sleep(3);

        executorService.execute(new MyRunnable(2) {
            @Override
            public void run() {
                //noinspection divzero,NumericOverflow
                System.out.println(1 / 0);
            }
        });
    }

    /**
     * 自定义任务类
     */
    private static abstract class MyRunnable implements Runnable {
        private final int no;

        protected MyRunnable(int no) {
            this.no = no;
        }

        public int getNo() {
            return no;
        }
    }

    /**
     * 实现了自定义通知的 ThreadPoolExecutor
     */
    private static class MyThreadPoolExecutor extends ThreadPoolExecutor {

        public MyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory, RejectedExecutionHandler handler) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
        }

        /**
         * 前置通知
         */
        @Override
        protected void beforeExecute(Thread t, Runnable r) {
            System.out.println("The mission[" + ((MyRunnable) r).getNo() + "] init.");
        }

        /**
         * 这个方法包含了后置通知和异常通知
         */
        @Override
        protected void afterExecute(Runnable r, Throwable t) {
            if (t == null) {
                System.out.println("The mission[" + ((MyRunnable) r).getNo() + "] execute successful.");
            } else {
                System.out.println("The mission[" + ((MyRunnable) r).getNo() + "] execute failed.");
            }
        }
    }
}
