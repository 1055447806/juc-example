package com.ohh.juc.executor.executorService;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExecutorServiceExample4 {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testInvokeAny();
//        testInvokeAll();
//        testSubmitRunnable();
        testSubmitRunnableWithResult();
    }

    /**
     * invokeAny 方法会接收一个 callable 集合，然后执行这些任务，直到某个任务执行完毕，
     * 这时其余的任务如果未执行完毕，就会被取消，不会继续执行。
     * 如果为其设置了 timeout，那么当超过等待时间还没有任务执行完毕，则抛出 TimeoutException。
     */
    private static void testInvokeAny() throws ExecutionException, InterruptedException, TimeoutException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<Integer>> callableList =
                IntStream
                        .range(0, 5)
                        .mapToObj(i ->
                                (Callable<Integer>) () -> {
                                    System.out.println("mission[" + i + "] is start.");
                                    TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                                    System.out.println("mission[" + i + "] is finished.");
                                    return i;
                                }
                        )
                        .collect(Collectors.toList());
        Integer value = executorService.invokeAny(callableList, 1, TimeUnit.SECONDS);
        System.out.println("InvokeAny() run finished, and value = " + value);
    }

    /**
     * invokeAll 方法接收一个 callable 集合，并等待所有任务执行完毕，
     * 返回一个 future 集合，如果设置了 timeout，那么当有任务超时时，抛出 CancellationException 异常。
     */
    @SuppressWarnings("DuplicatedCode")
    private static void testInvokeAll() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        List<Callable<Integer>> callableList = IntStream
                .range(0, 5)
                .mapToObj(i ->
                        (Callable<Integer>) () -> {
                            System.out.println("mission[" + i + "] is start.");
                            TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(20));
                            System.out.println("mission[" + i + "] is finished.");
                            return i;
                        }
                )
                .collect(Collectors.toList());

        List<Future<Integer>> futures = executorService.invokeAll(callableList, 1, TimeUnit.SECONDS);

        System.out.println("InvokeAll() run finished, and values are: ");

        futures
                .stream()
                .map(future -> {
                    try {
                        return future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .forEach(System.out::println);
    }

    /**
     * 使用 submit(Runnable) 方法提交任务，返回一个future，可以利用 future.get() 等待任务执行结束。
     */
    private static void testSubmitRunnable() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("Result:" + future.get());
    }

    /**
     * 使用 submit(Runnable,Object) 方法提交任务，返回一个future，
     * object是返回的结果，可以利用 future.get() 等待并获取执行结果。
     */
    private static void testSubmitRunnableWithResult() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        String result = "DONE";
        Future<?> future = executorService.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, result);
        System.out.println("Result:" + future.get());
    }
}
