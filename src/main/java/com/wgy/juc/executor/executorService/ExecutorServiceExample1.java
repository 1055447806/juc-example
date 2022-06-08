package com.wgy.juc.executor.executorService;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class ExecutorServiceExample1 {
    public static void main(String[] args) throws InterruptedException {
//        isShutdown();
//        isTerminated();
//        executeRunnableError();
        executeRunnableTask();
    }

    /**
     * 当调用shutdown方法之后，立即变为shutdown状态，
     * 此时不能再继续用提交新任务，否则会被驳回，并抛出RejectedException
     */
    private static void isShutdown() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(executorService.isShutdown());
        executorService.shutdown();
        System.out.println(executorService.isShutdown());
        executorService.execute(() -> System.out.println("I will be execute after shutdown?"));
    }

    /**
     * 当调用shutdown方法之后，立即变为shutdown状态，
     * 但是只有等待所有线程池关闭后，才会变为terminated状态
     */
    private static void isTerminated() {
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        executorService.shutdown();
        System.out.println("executorService.isShutdown() = " + executorService.isShutdown());
        System.out.println("executorService.isTerminated() = " + executorService.isTerminated());
        System.out.println("((ThreadPoolExecutor)executorService).isTerminating() = " + ((ThreadPoolExecutor) executorService).isTerminating());
    }

    /**
     * ExecutorService 在执行的过程中出现了错误时，
     * 可以通过ThreadFactory对出现错误的线程进行处理
     */
    private static void executeRunnableError() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.range(1, 10).forEach(i -> executorService.execute(() -> {
            //noinspection divzero,NumericOverflow
            System.out.println(1 / 0);
        }));
        executorService.shutdown();
        executorService.awaitTermination(9999, TimeUnit.MINUTES);
        System.out.println("ExecutorService is terminated");
    }

    private static class MyThreadFactory implements ThreadFactory {
        private final AtomicInteger SEQ = new AtomicInteger();

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("MyThread - " + SEQ.getAndIncrement());
            t.setUncaughtExceptionHandler((thread, cause) -> {
                System.out.println(thread.getName() + " execute failed");
                cause.printStackTrace();
                System.out.println("----------------------------------------");
            });
            return t;
        }
    }

    /**
     * 使用ThreadFactory对线程进行处理的时候会有一些缺点，
     * 比如无法获取thread中的runnable，导致如果runnable中
     * 有想要获取的信息，就会获取不到。
     * 通过自定义的MyTask，可以获得Runnable里面的 i 的值，
     * 这是一种模板设计模式。
     */
    private static void executeRunnableTask() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10, new MyThreadFactory());
        IntStream.range(1, 10).forEach(i -> executorService.execute(
                new MyTask(i) {

                    @Override
                    protected void doInit() {
                        //do nothing
                    }

                    @Override
                    protected void doExecute() {
                        if (i % 3 == 0) {
                            //noinspection divzero
                            int temp = i / 0;
                        }
                    }

                    @Override
                    protected void done() {
                        System.out.println("The no:" + i + " successfully , update status to DONE");
                    }

                    @Override
                    protected void error(Throwable cause) {
                        System.out.println("The no:" + i + " failed , update status to ERROR");
                    }
                }
        ));
        executorService.shutdown();
        executorService.awaitTermination(9999, TimeUnit.MINUTES);
        System.out.println("ExecutorService is terminated");
    }

    /**
     * 这是一个抽象类，MyTask会按照以下顺序执行：
     * doInit() -> doExecute() -> done()
     * 若果出现异常则执行error(cause)
     */
    private abstract static class MyTask implements Runnable {
        protected final int no;

        private MyTask(int no) {
            this.no = no;
        }

        @Override
        public void run() {
            try {
                this.doInit();
                this.doExecute();
                this.done();
            } catch (Throwable cause) {
                this.error(cause);
            }
        }

        protected abstract void doInit();

        protected abstract void doExecute();

        protected abstract void done();

        protected abstract void error(Throwable cause);
    }

}
