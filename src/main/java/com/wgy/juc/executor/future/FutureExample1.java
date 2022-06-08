package com.wgy.juc.executor.future;

import java.util.concurrent.*;

public class FutureExample1 {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//        testGet();
        testGetWithTimeOut();
    }

    /**
     * 使用 submit(Callable) 方法会返回一个 Future，使用这个 Future 可以阻塞的获得返回的结果。
     * 在使用 future.get 方法获取结果的过程中，如果被打断，会抛出 InterruptedException 异常。
     */
    private static void testGet() throws ExecutionException, InterruptedException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            try {
                System.out.println("do something...");
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        System.out.println("not be blocked, keep performing...");
//        interruptCallerThreadAfterTwoSeconds();
        System.out.println("then need the result of future, program will be blocked until get value, and the value is : " + future.get());
    }

    /**
     * 在使用 future.get 方法获取结果时，可以设置 timeout，如果超时，则抛出 TimeoutException。
     * 虽然会抛出 TimeoutException，但是任务仍然会继续执行直到结束。
     */
    private static void testGetWithTimeOut() throws ExecutionException, InterruptedException, TimeoutException {
        ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            try {
                System.out.println("do something...");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("execute successful.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        });
        Integer value = future.get(2, TimeUnit.SECONDS);
        System.out.println("value = " + value);
    }

    /**
     * 在 2 秒之后打断调用线程
     */
    private static void interruptCallerThreadAfterTwoSeconds() {
        Thread callerThread = Thread.currentThread();
        new Thread(() -> {
            System.out.println("I will interrupt thread[" + callerThread.getName() + "] after 2 seconds.");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            callerThread.interrupt();
        }).start();
    }
}
