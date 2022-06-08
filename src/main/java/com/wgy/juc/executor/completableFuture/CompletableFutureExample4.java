package com.wgy.juc.executor.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * CompletableFuture 的一些组合方法。
 * thenAcceptBoth，acceptEither，runAfterBoth，runAfterEither，thenCombine，thenCompose
 */
public class CompletableFutureExample4 {

    public static void main(String[] args) throws InterruptedException {
//        testThenAcceptBoth();
//        testAcceptEither();
//        testRunAfterBoth();
//        testRunAfterEither();
//        testThenCombine();
        testThenCompose();
        Thread.currentThread().join();
    }

    /**
     * thenAcceptBoth 方法可以组合两个 CompletableFuture，并消费两个 CompletableFuture 的结果。
     */
    private static void testThenAcceptBoth() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync s");
            sleepSec(5);
            System.out.println("end the supplyAsync s");
            return "hello";
        });
        CompletableFuture<Integer> other = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync i");
            sleepSec(5);
            System.out.println("end the supplyAsync i");
            return 100;
        });
        future.thenAcceptBoth(other, (s, i) -> System.out.printf("s: %s%ni: %d%n", s, i));
    }

    /**
     * acceptEither 方法会执行两个 CompletableFuture，并消费最快结束的那一个的返回值。
     */
    private static void testAcceptEither() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync one future");
            sleepSec(5);
            System.out.println("end the supplyAsync one future");
            return "one future";
        });
        CompletableFuture<String> other = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync other future");
            sleepSec(3);
            System.out.println("end the supplyAsync other future");
            return "other future";
        });
        future.acceptEither(other, System.out::println);
    }

    /**
     * runAfterBoth 方法会在两个 CompletableFuture 执行结束之后，运行一个 Runnable。
     */
    private static void testRunAfterBoth() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync one future");
            sleepSec(5);
            System.out.println("end the supplyAsync one future");
            return "one future";
        });
        CompletableFuture<String> other = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync other future");
            sleepSec(3);
            System.out.println("end the supplyAsync other future");
            return "other future";
        });
        future.runAfterBoth(other, () -> System.out.println("run after both."));
    }

    /**
     * runAfterEither 方法会执行两个 CompletableFuture，并在最快的一个执行结束之后，运行一个 Runnable。
     */
    private static void testRunAfterEither() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync one future");
            sleepSec(5);
            System.out.println("end the supplyAsync one future");
            return "one future";
        });
        CompletableFuture<String> other = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync other future");
            sleepSec(3);
            System.out.println("end the supplyAsync other future");
            return "other future";
        });
        future.runAfterEither(other, () -> System.out.println("one of task finished."));
    }

    /**
     * thenCombine 方法会执行两个 CompletableFuture，并将两个 CompletableFuture 的结果进行加工，
     * 转换为一个新的 CompletableFuture，用于后续的处理。
     */
    private static void testThenCombine() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync one future");
            sleepSec(5);
            System.out.println("end the supplyAsync one future");
            return "hello";
        });
        CompletableFuture<Integer> other = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync other future");
            sleepSec(3);
            System.out.println("end the supplyAsync other future");
            return 10;
        });

        future
                .thenCombine(other, (s, i) -> s.length() > i)
                .whenComplete((v, t) -> System.out.println(v));
    }

    /**
     * thenCompose 方法可以用于将某个 CompletableFuture 的结果作为另一个 CompletableFuture 的组成部分，
     * 其中先执行第一个 CompletableFuture，再执行 thenCompose 操作。
     */
    private static void testThenCompose() {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            System.out.println("start the supplyAsync one future");
            sleepSec(5);
            System.out.println("end the supplyAsync one future");
            return "hello";
        });
        future.thenCompose(s -> CompletableFuture.supplyAsync(() -> s)).thenAccept(System.out::println);
    }

    private static void sleepSec(long sec) {
        try {
            TimeUnit.SECONDS.sleep(sec);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
