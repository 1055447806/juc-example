package com.ohh.juc.executor.completableFuture;

import java.util.concurrent.*;
import java.util.stream.IntStream;

/**
 * 简单的认识 CompletableFuture
 */
public class CompletableFutureExample1 {

    public static void main(String[] args) throws InterruptedException {
//        testCompletableFuture1();
        testCompletableFuture2();
    }

    /**
     * Future的缺点是如果想得到任务执行结果就要使用 get 方法去获取，而 get 方法是阻塞的，
     * 通过 CompletableFuture 就不需要使用 get 方法去主动的获取结果了，而是在任务结束后通知主线程。
     * 需要注意的是，CompletableFuture 的内置线程池是守护线程，所以在这个例子中使用了 join 方法来观察程序运行。
     */
    public static void testCompletableFuture1() throws InterruptedException {
        CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println("done"));
        System.out.println("task is submitted, and Thread[main] is not blocked.");
        Thread.currentThread().join();
    }

    /**
     * 当程序的任务分为多个阶段时，第二个阶段需要第一个阶段返回的值，使用 future.get 方法去获取值，
     * 如果某个任务执行的时间过长，可能影响程序的运行效率，此时就可以使用 CompletableFuture，
     * 比如第一个阶段获取一些数据，第二个阶段对这些数据进行一些处理，CompletableFuture 可以使某个任务的
     * 第一阶段和第二阶段连续进行，从而避免了被等待其他任务的返回结果所影响。
     */
    private static void testCompletableFuture2() throws InterruptedException {
        IntStream.range(0, 10).forEach(i ->
                CompletableFuture
                        .supplyAsync(CompletableFutureExample1::getData)
                        .thenAccept(CompletableFutureExample1::useData)
                        .whenComplete((v, t) -> System.out.printf("task[%d] is finished.%n", i))
        );
        Thread.currentThread().join();
    }

    /**
     * 任务1：获取数据
     */
    private static int getData() {
        int index = ThreadLocalRandom.current().nextInt(10);
        try {
            System.out.printf("try to get data[%d]%n", index);
            TimeUnit.SECONDS.sleep(index);
            System.out.printf("get data[%d] succeed.%n", index);
            return index;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 任务2：对数据进行处理
     */
    private static void useData(int data) {
        System.out.printf("use the data[%d]%n", data);
    }
}
