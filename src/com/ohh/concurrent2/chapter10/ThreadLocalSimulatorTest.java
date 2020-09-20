package com.ohh.concurrent2.chapter10;

import java.util.Random;

public class ThreadLocalSimulatorTest {
    private static final ThreadLocalSimulator<String> threadLocalSimulator = new ThreadLocalSimulator<String>() {
        @Override
        public String initialValue() {
            return "NoValue";
        }
    };
    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(() -> {
            threadLocalSimulator.set("Thread-t1");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocalSimulator.get());
        }, "t1");

        Thread t2 = new Thread(() -> {
            threadLocalSimulator.set("Thread-t2");
            try {
                Thread.sleep(random.nextInt(1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " " + threadLocalSimulator.get());
        }, "t2");

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println(Thread.currentThread().getName() + " " + threadLocalSimulator.get());
    }
}
