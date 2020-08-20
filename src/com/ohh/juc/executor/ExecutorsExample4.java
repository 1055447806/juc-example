package com.ohh.juc.executor;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * ForkJoinPool根据CPU核数创建线程,
 * 线程会自动销毁
 * <p>
 * return new ForkJoinPool
 * (Runtime.getRuntime().availableProcessors(),
 * ForkJoinPool.defaultForkJoinWorkerThreadFactory,
 * null, true);
 */
public class ExecutorsExample4 {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newWorkStealingPool();
        List<Callable<String>> list = IntStream.range(0, 20).mapToObj(i -> (Callable<String>) () -> {
            System.out.println(Thread.currentThread().getName() + " running");
            sleep(5);
            return "Task-" + i;
        }).collect(Collectors.toList());

        executorService.invokeAll(list).stream().map(future -> {
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }).forEach(System.out::println);
    }

    private static void sleep(long l) {
        try {
            TimeUnit.SECONDS.sleep(l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
