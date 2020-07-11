package com.ohh.jcu.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerTest2 {

    /**
     * 可见性、有序性、原子性
     */
    private static AtomicInteger value = new AtomicInteger();

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    // no synchronized
                    value.incrementAndGet();
                }
                System.out.println("finished!");
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        Thread.sleep(1000L);
        System.out.println("value: " + value);
    }
}
