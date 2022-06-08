package com.wgy.concurrent2.chapter10;

import java.util.Random;

public class ThreadLocalComplexTest {
    private static final ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            threadLocal.set("Thread-t1");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }, "t1");

        Thread t2 = new Thread(() -> {
            threadLocal.set("Thread-t2");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(Thread.currentThread().getName() + " " + threadLocal.get());
    }
}
