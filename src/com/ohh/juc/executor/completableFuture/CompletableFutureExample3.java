package com.ohh.juc.executor.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture 的一些非组合方法。
 * whenComplete，thenApply，handle，thenAccept，thenRun
 */
public class CompletableFutureExample3 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testWhenComplete();
//        testWhenCompleteAsync();
//        testThenApply();
//        testThenApplyAsync();
//        testHandleAsync();
//        testThenAcceptAsync();
        testThenRun();

        Thread.currentThread().join();
    }

    /**
     * whenComplete 方法会同步的执行回调函数，
     */
    private static void testWhenComplete() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello world.");
        future.whenComplete((v, t) -> {
            try {
                System.out.println("whenComplete is running.");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("whenComplete v = " + v);
                System.out.println("whenComplete is finished.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("task is submitted.");
    }

    /**
     * whenCompleteAsync 方法会异步的执行回调函数。
     */
    private static void testWhenCompleteAsync() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> "hello world.");
        future.whenCompleteAsync((v, t) -> {
            try {
                System.out.println("whenCompleteAsync is running.");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("whenCompleteAsync v = " + v);
                System.out.println("whenCompleteAsync is finished.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("task is submitted.");
    }

    /**
     * thenApply 方法会同步的处理一个值，并返回一个结果。
     */
    private static void testThenApply() {
        CompletableFuture<Integer> future =
                CompletableFuture
                        .supplyAsync(() -> "hello world.")
                        .thenApply(v -> {
                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return v.length();
                        })
                        .whenComplete((v, t) -> System.out.println("v.length = " + v));
        System.out.println("task is submitted");

    }

    /**
     * thenApplyAsync 方法会异步的处理一个值，并返回一个结果。
     */
    private static void testThenApplyAsync() {
        CompletableFuture<Integer> future =
                CompletableFuture
                        .supplyAsync(() -> "hello world.")
                        .thenApplyAsync(v -> {
                            try {
                                TimeUnit.SECONDS.sleep(5);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return v.length();
                        })
                        .whenComplete((v, t) -> System.out.println("v.length = " + v));
        System.out.println("task is submitted");
    }

    /**
     * handleAsync 和 handle 方法可以对程序中的错误进行处理。
     */
    private static void testHandleAsync() {
        CompletableFuture
                .<String>supplyAsync(() -> {
                    throw new RuntimeException();
                })
                .handleAsync((v, t) -> (v == null) ? 0 : v.length())
                .whenComplete((v, t) -> System.out.println("value is : " + v));
        System.out.println("task is submitted");
    }

    /**
     * thenAcceptAsync 和 thenAccept 方法消费一个 CompletableFuture 所产生的结果。
     */
    private static void testThenAcceptAsync() {
        CompletableFuture
                .supplyAsync(() -> "hello")
                .thenAcceptAsync(System.out::println);
    }

    /**
     * thenRunAsync 和 thenRun 方法可以运行一个 Runnable。
     */
    private static void testThenRun() {
        CompletableFuture
                .supplyAsync(() -> "hello")
                .thenRun(() -> System.out.println("done."));
    }
}
