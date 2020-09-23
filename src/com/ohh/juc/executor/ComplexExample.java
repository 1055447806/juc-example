package com.ohh.juc.executor;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * 对于 ExecutorCompletionService 的一些复杂情况的解决方案。
 */
public class ComplexExample {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
//        testExecutorCompletionService();
//        testExecutorCompletionService2();
        testExecutorCompletionService3();
    }

    /**
     * ExecutorCompletionService 解决了多个任务情况下，获取结果的等待问题，它的关注点是任务的完成，
     * 通过 poll 或者 take 方法获取最先完成的任务的返回值，
     */
    private static void testExecutorCompletionService() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        ExecutorCompletionService<Object> executorCompletionService = new ExecutorCompletionService<>(executorService);
        List<Runnable> runnableList = IntStream.range(0, 5).mapToObj(ComplexExample::toTask).collect(Collectors.toList());
        runnableList.forEach(r -> executorCompletionService.submit(Executors.callable(r)));

        while (true) {
            System.out.println("value is: " + executorCompletionService.take().get());
        }
    }

    /**
     * ExecutorCompletionService 虽然解决了一些问题，但是同时也存在着一些问题，
     * 比如当使用 shutdown 方法后，希望获取队列中被排干的任务，会发现这些任务是
     * ExecutorCompletionService 的一个私有内部类 java.util.concurrent.ExecutorCompletionService$QueueingFuture，
     * 这样就不能获取这些任务的一些细节，并且，如果有任务在执行过程中，shutdownNow 方法会中断这些任务，但是返回的集合中
     * 却不包含这些任务，这样就会使这些没有运行完成的任务被丢失。
     */
    private static void testExecutorCompletionService2() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<Object> executorCompletionService = new ExecutorCompletionService<>(executorService);
        List<Runnable> runnableList = IntStream.range(0, 5).mapToObj(ComplexExample::toTask).collect(Collectors.toList());
        runnableList.forEach(r -> executorCompletionService.submit(Executors.callable(r)));
        TimeUnit.SECONDS.sleep(12);
        // 使用 Callable
        List<Runnable> res = executorService.shutdownNow();
        System.out.println(res);
    }

    /**
     * 如果想要获取到那些被中断的任务，可以使用一个自定义的 Callable 类。
     * 通过这种方式就可以获取队列中被排干的任务和被中断的任务的集合。
     */
    private static void testExecutorCompletionService3() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);
        List<Callable<Integer>> callableList = IntStream.range(0, 5).mapToObj(MyTask::new).collect(Collectors.toList());
        callableList.forEach(executorCompletionService::submit);
        TimeUnit.SECONDS.sleep(12);
        //使用 MyTask
        executorService.shutdownNow();
        callableList.stream().filter(c -> !((MyTask) c).isSuccess()).forEach(System.out::println);
    }

    /**
     * 一个自定义的 Callable 类，如果任务执行成功，就把 success 设置为 true。
     */
    private static class MyTask implements Callable<Integer> {

        private final int value;

        private boolean success = false;

        private MyTask(int value) {
            this.value = value;
        }

        @Override
        public Integer call() throws Exception {
            System.out.printf("Task[%d] is running.%n", value);
            TimeUnit.SECONDS.sleep(value * 5 + 10);
            System.out.printf("Task[%d] is finished.%n", value);
            success = true;
            return value;
        }

        public boolean isSuccess() {
            return success;
        }
    }

    private static Runnable toTask(int i) {
        return () -> {
            try {
                System.out.printf("Task[%d] is running.%n", i);
                TimeUnit.SECONDS.sleep(i * 5 + 10);
                System.out.printf("Task[%d] is finished.%n", i);
            } catch (InterruptedException e) {
                System.err.printf("Task[%d] is interrupted.%n", i);
            }
        };
    }
}
