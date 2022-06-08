package com.wgy.juc.utils.semaphore;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample3 {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        Thread t1 = new Thread(() -> {
            try {
                semaphore.acquire();
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
            System.out.println("T1 finished.");
        });
        t1.start();

        TimeUnit.MILLISECONDS.sleep(500);
        Thread t2 = new Thread(() -> {
            try {
                semaphore.acquireUninterruptibly();//使用acquireUninterruptibly()方法不会捕获中断
            } finally {
                semaphore.release();
            }
            System.out.println("T2 finished.");
        });
        t2.start();

        TimeUnit.MILLISECONDS.sleep(500);
        t2.interrupt();
    }
}
