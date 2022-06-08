package com.wgy.juc.executor.completionService;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * ExecutorService 的缺点
 */
public class CompletionServiceExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        futureDefect1();
        futureDefect2();
    }

    /**
     * 使用 ExecutorService 的缺点：获取结果是阻塞的。
     */
    private static void futureDefect1() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        Future<Integer> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 100;
        });
        System.out.println("task was submitted, and program is waiting for result...");
        System.out.println("value is: " + future.get());
    }

    /**
     * 使用 ExecutorService 的缺点：当有多个任务提交时，可能先阻塞的获取执行时间较长的任务，
     * 导致一些已经完成的任务无法获取到执行结果，从而浪费性能。
     */
    private static void futureDefect2() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        List<Callable<Integer>> callableList = Arrays.asList(
                () -> {
                    TimeUnit.SECONDS.sleep(10);
                    System.out.println("task-1 is finished.");
                    return 1;
                },
                () -> {
                    TimeUnit.SECONDS.sleep(20);
                    System.out.println("task-2 is finished.");
                    return 2;
                }
        );
        List<Future<Integer>> futures = executorService.invokeAll(callableList);
        System.out.println("all of the task is execute finished.");
        System.out.println("task-1 value is: " + futures.get(0).get());
        System.out.println("task-2 value is: " + futures.get(1).get());
    }
}
