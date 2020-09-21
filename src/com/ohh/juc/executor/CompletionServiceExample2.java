package com.ohh.juc.executor;

import java.util.concurrent.*;

public class CompletionServiceExample2 {

    /**
     * ExecutorCompletionService 的 take 方法可以阻塞的获取一个执行最快的任务的结果。
     * ExecutorCompletionService 的 poll 方法可以非阻塞的获取一个执行最快的任务的结果，
     * 调用 poll 方法时如果 completionQueue 中没有结果，则返回 null，
     * 可以给 poll 设定 timeout，在指定时间内阻塞，如果 completionQueue 中有结果，则立即返回该 future，否则返回 null。
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        ExecutorCompletionService<Integer> executorCompletionService = new ExecutorCompletionService<>(executorService);

        executorCompletionService.submit(
                () -> {
                    TimeUnit.SECONDS.sleep(5);
                    return 1;
                }
        );

        executorCompletionService.submit(
                () -> {
                    TimeUnit.SECONDS.sleep(10);
                    return 2;
                }
        );

        /*
        //take
        while (true) {
            System.out.println("value is: "+executorCompletionService.take().get());
        }
        */

        //poll
        Future<Integer> poll;
        poll = executorCompletionService.poll();
        System.out.println("poll is: " + poll);
        poll = executorCompletionService.poll(2, TimeUnit.SECONDS);
        System.out.println("poll is: " + poll);
        poll = executorCompletionService.poll(100, TimeUnit.SECONDS);
        System.out.println("poll is: " + poll);
        System.out.println("the value is: " + poll.get());
    }

}
