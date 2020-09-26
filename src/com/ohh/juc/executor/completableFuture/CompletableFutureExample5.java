package com.ohh.juc.executor.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture 处理结果的一些方法。
 */
public class CompletableFutureExample5 {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testGetNow();
//        testComplete1();
//        testComplete2();
//        testJoin();
//        testCompleteExceptionally();
//        testObtrudeException();
//        testObtrudeValue();
        testExceptionally();

        Thread.currentThread().join();
    }

    /**
     * getNow 方法当 CompletableFuture 有 result 的时候，返回 result，否则返回给定的值。
     */
    private static void testGetNow() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        future.thenAcceptAsync(v -> System.out.println("accept value: " + v));
        System.out.println(future.getNow("world"));
        sleepSec(8);
        System.out.println(future.getNow("world"));
    }

    /**
     * complete 方法如果 CompletableFuture 没有执行完成，返回 true，并设置它的结果为给定值。
     */
    private static void testComplete1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        System.out.println(future.complete("world"));
        System.out.println(future.get());
    }

    /**
     * complete 方法如果 CompletableFuture 执行完成，返回 false，它的结果仍为原值。
     */
    private static void testComplete2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        sleepSec(8);
        System.out.println(future.complete("world"));
        System.out.println(future.get());
    }

    /**
     * join 方法和 get 方法很类似，但是不需要处理异常。
     */
    private static void testJoin() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        String result = future.join();
        System.out.println(result);
    }

    /**
     * 当 CompletableFuture 没有执行完时，调用 completeExceptionally 方法，
     * 可以使 CompletableFuture 在获取结果时抛出指定的异常。
     */
    private static void testCompleteExceptionally() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        future.completeExceptionally(new RuntimeException("task is not yet get the result."));
        sleepSec(8);
        System.out.println("future.get() = " + future.get());
    }

    /**
     * obtrudeException 方法可以强制使 CompletableFuture 在 get 时抛出指定异常，
     * 不论 CompletableFuture 是否执行完成。
     */
    private static void testObtrudeException() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        sleepSec(8);
        future.obtrudeException(new RuntimeException("error."));
        System.out.println("future.get() = " + future.get());
    }

    /**
     * obtrudeValue 方法可以强制使 CompletableFuture 在获取结果时获取指定的值，
     * 不论 CompletableFuture 是否执行完成。
     */
    private static void testObtrudeValue() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        sleepSec(8);
        future.obtrudeValue("world");
        System.out.println("future.get() = " + future.get());
    }

    /**
     * exceptionally 方法可以在任务运行出现异常时，将异常进行处理，
     * 转换成 CompletableFuture 的结果，如果没有异常，就正常执行。
     */
    private static void testExceptionally() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleepSec(5);
            return "hello";
        });
        future.whenComplete((v, t) -> System.out.println("value is: " + v));
        sleepSec(8);
        future.thenApply(v -> {
            Integer.parseInt(v);
            return v + "world";
        }).exceptionally(Throwable::getMessage).thenAccept(v -> System.out.println("accept: " + v));
    }

    private static void sleepSec(long sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
