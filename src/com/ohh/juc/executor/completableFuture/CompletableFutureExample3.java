package com.ohh.juc.executor.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class CompletableFutureExample3 {

    public static void main(String[] args) throws InterruptedException {
//        testWhenComplete();
//        testWhenCompleteAsync();
        testThenApply();
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

    private static void testThenApply() {
        CompletableFuture<Integer> future =
                CompletableFuture
                        .supplyAsync(() -> "hello world.")
                        .thenApply(String::length);
        System.out.println("task is submitted.");
    }

    private static void testThenApplyAsync() {
        CompletableFuture<Integer> future =
                CompletableFuture
                        .supplyAsync(() -> "hello world.")
                        .thenApplyAsync(String::length);
        System.out.println("task is submitted.");
    }
}
