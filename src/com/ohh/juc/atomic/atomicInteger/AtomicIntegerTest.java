package com.ohh.juc.atomic.atomicInteger;

public class AtomicIntegerTest {

    /**
     * 可见性、有序性、非原子性
     */
    private static volatile int value;

    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    /*synchronized (this) {
                        value++;
                    }*/
                    // need synchronized
                    value++;
                }
                System.out.println("finished!");
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }

        Thread.sleep(1000L);
        System.out.println("value: "+value);
    }
}
