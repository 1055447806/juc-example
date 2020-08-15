package com.ohh.juc.utils;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;

public class CyclicBarrierExample2 {
    public static void main(String[] args) throws InterruptedException {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2);
        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "T1").start();

        new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(0);
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }, "T2").start();

        TimeUnit.SECONDS.sleep(3);
        System.out.println("cyclicBarrier.getNumberWaiting() = " + cyclicBarrier.getNumberWaiting());
        System.out.println("cyclicBarrier.getParties() = " + cyclicBarrier.getParties());
        System.out.println("cyclicBarrier.isBroken() = " + cyclicBarrier.isBroken());

        cyclicBarrier.reset();
        System.out.println("cyclicBarrier.getNumberWaiting() = " + cyclicBarrier.getNumberWaiting());
        System.out.println("cyclicBarrier.getParties() = " + cyclicBarrier.getParties());
        System.out.println("cyclicBarrier.isBroken() = " + cyclicBarrier.isBroken());
    }
}
