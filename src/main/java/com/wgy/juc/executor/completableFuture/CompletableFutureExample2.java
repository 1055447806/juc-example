package com.wgy.juc.executor.completableFuture;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * CompletableFuture 的几种创建方式。
 */
public class CompletableFutureExample2 {

    public static void main(String[] args) throws InterruptedException {
//        testSupplyAsync();
//        testRunAsync();
//        testCompletedFuture();
//        testAnyOf();
        testAllOf();
        Thread.currentThread().join();
    }

    /**
     * 这个方法实现了处理两个异步的任务，并在处理完两个异步任务后，执行一个回调函数的场景。
     * 其中 supplyAsync 会生产一个数据。
     */
    private static void testSupplyAsync() {
        CompletableFuture<Void> vcf1 = getVoidCompletableFutureOfSupplyAsyncAndAcceptAsyncObject();
        CompletableFuture<Void> vcf2 = getVoidCompletableFutureOfSupplyAsyncAndAcceptAsyncObject();
        vcf1.runAfterBoth(vcf2, () -> System.out.println("process action"));
    }

    /**
     * 异步的生产并异步处理一个对象，并返回一个 CompletableFuture。
     */
    private static CompletableFuture<Void> getVoidCompletableFutureOfSupplyAsyncAndAcceptAsyncObject() {
        return CompletableFuture
                .supplyAsync(Object::new)
                .thenAcceptAsync(
                        obj -> {
                            try {
                                System.out.println("accept obj[" + obj + "] start.");
                                TimeUnit.SECONDS.sleep(5);
                                System.out.println("accept obj[" + obj + "] end.");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                );
    }

    /**
     * 异步的执行一个 runnable 任务。
     */
    private static void testRunAsync() {
        CompletableFuture.runAsync(() -> {
            try {
                System.out.println("task start.");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("task finish.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).whenComplete((v, t) -> System.out.println("task is success."));
    }

    /**
     * completedFuture 方法接收一个参数 value，并返回一个 CompletableFuture，
     * 且该 CompletableFuture 的 result 为 value。
     */
    private static void testCompletedFuture() {
        CompletableFuture.completedFuture("hello world.").thenAccept(v -> System.out.println("get data is: " + v));
    }

    /**
     * anyOf 方法会执行所有的 CompletableFuture，当有任务结束时，将最快结束的任务的返回结果作为返回值。
     */
    private static void testAnyOf() {
        List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();
        IntStream.range(0, 2).forEach(i ->
                completableFutures.add(
                        CompletableFuture.supplyAsync(
                                () -> {
                                    try {
                                        System.out.printf("task[%d] is start.%n", i);
                                        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                                        System.out.printf("task[%d] is finished.%n", i);
                                        return i;
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        )
                )
        );

        // 提交两个任务并对最快执行完的任务的结果进行打印。
        CompletableFuture
                .anyOf(completableFutures.toArray(new CompletableFuture[0]))
                .whenComplete((v, t) -> System.out.println("the value is:" + v));
    }

    /**
     * allOf 方法会执行所有的 CompletableFuture，当所有任务结束时，并返回一个 CompletableFuture<Void>。
     */
    private static void testAllOf() {
        List<CompletableFuture<Integer>> completableFutures = new ArrayList<>();
        IntStream.range(0, 2).forEach(i ->
                completableFutures.add(
                        CompletableFuture.supplyAsync(
                                () -> {
                                    try {
                                        System.out.printf("task[%d] is start.%n", i);
                                        TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(10));
                                        System.out.printf("task[%d] is finished.%n", i);
                                        return i;
                                    } catch (InterruptedException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                        )
                )
        );
        CompletableFuture
                .allOf(completableFutures.toArray(new CompletableFuture[0]))
                .whenComplete((v, t) -> System.out.println("all task is finished."));
    }
}
