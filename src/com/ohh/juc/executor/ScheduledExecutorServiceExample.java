package com.ohh.juc.executor;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class ScheduledExecutorServiceExample {
    public static void main(String[] args) throws InterruptedException {
//        testSchedule();
//        testCancel();
//        testScheduleAtFixedRate1();
//        testScheduleAtFixedRate2();
//        testScheduleAtFixedRate3();
//        testScheduleWithFixedDelay();
//        testContinueExistingPeriodicTasksAfterShutdownPolicy();
        testExecuteExistingDelayedTasksAfterShutdownPolicy();
    }

    /**
     * 可以通过 schedule 方法 使任务在某个时间后执行
     */
    private static void testSchedule() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println("program is running, and task will run after 2 sec.");
        executor.schedule(() -> System.out.println("task is running."), 2, TimeUnit.SECONDS);
    }

    /**
     * 可以通过 cancel 方法取消任务。
     */
    private static void testCancel() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println("program is running, and task will run after 2 sec.");
        ScheduledFuture<?> future = executor.schedule(() -> System.out.println("task is running."), 2, TimeUnit.SECONDS);
        System.out.println("can be cancel? " + future.cancel(true));
    }

    /**
     * scheduleAtFixedRate 方法可以在 initialDelay 时间后开始循环执行任务，任务的周期为 period。
     */
    private static void testScheduleAtFixedRate1() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println("program is running, and task will run after 5 sec, period is 1 sec.");
        executor.scheduleAtFixedRate(() -> System.out.println("task is running."), 5, 1, TimeUnit.SECONDS);
    }

    /**
     * 使用 scheduleAtFixedRate 方法提交的任务可以使用 cancel 方法取消。
     */
    private static void testScheduleAtFixedRate2() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println("program is running, and task will run after 5 sec, period is 1 sec.");
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> System.out.println("task is running."), 5, 1, TimeUnit.SECONDS);
        //在任务执行几次之后，使用 cancel 方法取消任务。
        TimeUnit.SECONDS.sleep(10);
        System.out.println("can be cancel? " + future.cancel(true));
    }

    /**
     * scheduleAtFixedRate 方法提交的任务，如果任务的执行时间大于 period，
     * 那么只有当本次任务结束后，才会开始下一次任务。
     */
    private static void testScheduleAtFixedRate3() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println("program is running, and task will run after 5 sec, period is 1 sec.");
        ScheduledFuture<?> future = executor.scheduleAtFixedRate(() -> {
            System.out.println("task is running.");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, 5, 1, TimeUnit.SECONDS);
    }

    /**
     * scheduleWithFixedDelay 方法提交的任务，会在每次任务执行结束后，
     * 延时设定的 delay，然后再执行下一次任务。
     */
    private static void testScheduleWithFixedDelay() {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        System.out.println("program is running, and task will run after 5 sec, delay is 2 sec.");
        executor.scheduleWithFixedDelay(() -> {
            System.out.println("task is running.");
            try {
                //随机运行 0 ~ 5 秒
                TimeUnit.SECONDS.sleep(ThreadLocalRandom.current().nextInt(5));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("task is finished.");
        }, 5, 2, TimeUnit.SECONDS);
    }

    /**
     * ScheduledThreadPoolExecutor 的 ContinueExistingPeriodicTasksAfterShutdownPolicy 默认为 false，
     * 在使用 scheduleAtFixedRate 和 scheduleWithFixedDelay 方法提交周期性任务时，shutdown 方法会使周期任务停止运行，
     * 通过设置 ContinueExistingPeriodicTasksAfterShutdownPolicy 策略为 true，
     * 就可以在调用 shutdown 之后让周期任务继续运行。
     */
    private static void testContinueExistingPeriodicTasksAfterShutdownPolicy() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.setContinueExistingPeriodicTasksAfterShutdownPolicy(true);
        System.out.println("Policy ContinueExistingPeriodicTasksAfterShutdownPolicy is : " + executor.getContinueExistingPeriodicTasksAfterShutdownPolicy());
        System.out.println("program is running, and task will run after 5 sec, period is 1 sec.");
        executor.scheduleAtFixedRate(() -> System.out.println("task is running."), 5, 1, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(6);
        System.out.println("> shutdown!");
        executor.shutdown();
    }

    /**
     * ScheduledThreadPoolExecutor 的 ExecuteExistingDelayedTasksAfterShutdownPolicy 默认为 true，
     * 在使用 schedule 方法时，如果在给定的 delay 内调用 shutdown 方法，那么延时任务会正常运行，
     * 当给 ExecuteExistingDelayedTasksAfterShutdownPolicy 设置为 false 时，在给定的 delay 内调用 shutdown 方法，
     * 那么延时任务会被取消。
     */
    private static void testExecuteExistingDelayedTasksAfterShutdownPolicy() throws InterruptedException {
        ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(2);
        executor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
        System.out.println("Policy ExecuteExistingDelayedTasksAfterShutdownPolicy is: " + executor.getExecuteExistingDelayedTasksAfterShutdownPolicy());
        System.out.println("program is running, and task will run after 5 sec.");
        executor.schedule(() -> System.out.println("task is running."), 5, TimeUnit.SECONDS);
        TimeUnit.SECONDS.sleep(2);
        System.out.println("> shutdown!");
        executor.shutdown();
    }
}
