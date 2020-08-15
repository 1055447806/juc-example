package com.ohh.juc.utils;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreExample2 {
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(1);
        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+" in");
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+" get the semaphore.");
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    semaphore.release();
                }
                System.out.println(Thread.currentThread().getName()+" out");
            }).start();
        }
        while (true) {
            TimeUnit.SECONDS.sleep(1);
            System.out.println("semaphore.availablePermits() = " + semaphore.availablePermits());
            System.out.println("semaphore.getQueueLength() = " + semaphore.getQueueLength());
        }
    }
}
