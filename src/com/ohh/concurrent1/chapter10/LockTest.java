package com.ohh.concurrent1.chapter10;

import java.util.Optional;
import java.util.stream.Stream;

public class LockTest {

    public static void main(String[] args) {

        final BooleanLock booleanLock = new BooleanLock();

        //分别启动四个线程
        Stream.of("T1", "T2", "T3", "T4").forEach(name ->
                new Thread(() -> {
                    try {
                        booleanLock.lock(3000);
                        work();
                    } catch (Lock.getLockTimeOutException e) {
                        System.out.println(Thread.currentThread().getName() + " time out");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        booleanLock.unlock();
                    }
                }, name).start()
        );

        //输出等待线程队列
        try {
            Thread.sleep(1000);
            while (booleanLock.getBlockedSize() > 0) {
                try {
                    Thread.sleep(2000);
                    System.out.println("waiting queue: " + booleanLock.getBlockedThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void work() throws InterruptedException {
        Optional.of(Thread.currentThread().getName() + " is working...").ifPresent(System.out::println);
        Thread.sleep(10_000);
    }
}