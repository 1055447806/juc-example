package com.ohh.juc.utils;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {
    public static void main(String[] args) {
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+": Do something initial working...");
            try {
                Thread.sleep(1_000L);
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName()+": Do other working...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t1").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName()+": asyn prepare for some data...");
            try {
                Thread.sleep(5_000L);
                System.out.println(Thread.currentThread().getName()+": Done...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                countDownLatch.countDown();
            }
        },"t2").start();

        new Thread(() -> {
            try {
                countDownLatch.await();
                System.out.println(Thread.currentThread().getName()+": release...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"t3").start();
    }
}
