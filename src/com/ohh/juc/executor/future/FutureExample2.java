package com.ohh.juc.executor.future;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class FutureExample2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
//        testCancel1();
//        testCancel2();
//        testCancel3();
//        testCancel4();
//        testCancel5();
        testCancel6();
//        testIsDone();
    }

    /**
     * 在任务执行过程中可以使用 future.cancel 取消任务，并返回 true，
     * 不能取消已经被取消的任务，当任务被取消后，再次执行 future.cancel，将返回 false。
     * cancel 方法接收一个参数，当参数为 true 时，会打断任务的线程，否则允许任务线程执行完毕。
     */
    private static void testCancel1() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("mission is start!");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                System.out.println("mission be interrupted!");
            }
            System.out.println("mission is finished!");
            return 10;
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("can be cancel? " + future.cancel(false));

        TimeUnit.SECONDS.sleep(1);
        System.out.println("can be cancel again? " + future.cancel(true));
    }

    /**
     * 使用 future.cancel 方法可以取消任务，但是不能取消已经完成的任务。
     */
    private static void testCancel2() throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> 10);
        System.out.println("mission is completed and the value is: " + future.get());
        System.out.println("can be cancel? " + future.cancel(true));
    }

    /**
     * 当使用 future.cancel 方法返回 true 之后，future.isCancelled 和 future.isDone 方法也返回 true
     */
    private static void testCancel3() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        AtomicBoolean running = new AtomicBoolean(true);
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("mission is start!");
            while (running.get()) {
                //empty
            }
            System.out.println("mission is finished.");
            return 10;
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("can be cancel? " + future.cancel(true));
        System.out.println("mission is cancelled? " + future.isCancelled());
        System.out.println("mission is done? " + future.isDone());
    }

    /**
     * 使用 cancel 方法并不会使线程停止运行，而是会继续执行，
     * 所以应该用 Thread.interrupted() 方法和 cancel 方法配合使用。
     */
    private static void testCancel4() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("mission is start!");
            while (!Thread.interrupted()) {
                //empty
            }
            System.out.println("mission is finished");
            return 10;
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("can be cancel? " + future.cancel(true));
    }

    /**
     * 如果仅仅使用 Thread.interrupted 方法去判断，当一次任务的执行时间过长的情况下，
     * 必须要等到这次任务执行结束后才能使任务线程停止运行，所以在这种情况下可以使用 ThreadFactory，
     * 为任务线程设置为守护线程，这时当调用 cancel 方法后，可以强制让任务线程停止。
     */
    private static void testCancel5() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool(r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            return t;
        });
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("mission is start!");
            while (!Thread.interrupted()) {
                //也许一次任务运行很长的时间
            }
            TimeUnit.SECONDS.sleep(5);
            System.out.println("mission is finished");
            return 10;
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("can be cancel? " + future.cancel(true));
    }

    /**
     * 虽然使用了 cancel 方法后任务线程仍然可以正常结束并返回 value，
     * 但是使用 future.get 方法会抛出 CancellationException 异常，无法获取返回值。
     */
    private static void testCancel6() throws InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            System.out.println("mission is start!");
            while (!Thread.interrupted()) {
                //empty
            }
            System.out.println("mission is finished and return the value '10'.");
            return 10;
        });

        TimeUnit.SECONDS.sleep(1);
        System.out.println("can be cancel? " + future.cancel(true));

        try {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("can be get the value? " + future.get());
        } catch (ExecutionException | CancellationException e) {
            System.err.println("get the value failed.");
        }
    }

    /**
     * 使用 submit(Callable) 提交任务获得的 future，可以使用 future.isDone 方法判断任务执行状态，
     * 当任务顺利完成、执行中出现异常，或者被取消时，future.isDone 方法返回 true，否则返回 false。
     */
    private static void testIsDone() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<Integer> future = executorService.submit(() -> {
            try {
                //noinspection NumericOverflow,divzero
//                int value = 1 / 0;
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                //empty
            }
            return 10;
        });

        System.out.println("mission is done? " + future.isDone());
        try {
            System.out.println("mission completed and the value is: " + future.get());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("future.get() is failed.");
        }
        System.out.println("mission is done? " + future.isDone());
    }
}
